package com.domed.api.service.transportService.vehicleWorkflow;

import com.domed.domain.transportDomain.HyClb;
import com.domed.domain.transportDomain.transportTemp.HyClbTemp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.*;
import java.util.*;
import static com.domed.api.service.transportService.vehicleWorkflow.VehicleWorkflowException.require;

@Service
@Transactional(rollbackFor = Exception.class)
public class VehicleWorkflowService implements IVehicleWorkflowService {
    @Autowired private VehicleStore store;
    // The project owner approved reviewed manual inputs on 2026-11-03.
    @Value("${vehicle.workflow.manual-formal-accept-enabled:true}") private boolean manualFormalAccept = true;
    private static final Set<String> FIELDS = new HashSet<>(Arrays.asList("enterpriseId", "plateNumber", "vin",
            "plateColor", "vehicleType", "engineNumber", "manufacturer", "model", "businessType", "registrationDate", "remarks"));
    private static final Set<String> IDENTITY = new HashSet<>(Arrays.asList("id", "vehicleId", "letterGid", "revision"));
    private final ObjectMapper json = new ObjectMapper();

    @Override @Transactional(readOnly = true)
    public Map<String, Object> options(VehicleActor actor) {
        require(actor.applicant, 403, "无单车申报权限");
        return store.options(actor.id);
    }

    @Override @Transactional(readOnly = true)
    public Map<String, Object> listDrafts(VehicleActor actor, Map<String, Object> input) {
        require(actor.applicant, 403, "无单车申报权限");
        keys(input, "page", "size");
        int page = integer(input, "page", 1, 1000000, 1), size = integer(input, "size", 1, 100, 10);
        store.schema(false);
        List<Map<String, Object>> rows = new ArrayList<>();
        for (HyClbTemp row : store.drafts(actor.id, page, size)) rows.add(view(row, actor));
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("rows", rows); out.put("total", store.draftCount(actor.id)); out.put("page", page); out.put("size", size);
        return out;
    }

    @Override @Transactional(readOnly = true)
    public Map<String, Object> getAcceptCarByLetterGid(VehicleActor actor, Map<String, Object> input) {
        keys(input);
        store.schema(false);
        HyClbTemp row = load(input, false);
        readAllowed(row, actor);
        return view(row, actor);
    }

    @Override
    public Map<String, Object> holdRoadFirstIssueTemp(VehicleActor actor, Map<String, Object> input) {
        keys(input, FIELDS.toArray(new String[0]));
        require(actor.applicant, 403, "无单车申报权限");
        store.schema(false);
        HyClbTemp row;
        boolean create = text(input, "id", 20, false) == null;
        Integer from = null;
        if (create) {
            require(text(input, "vehicleId", 36, false) == null && text(input, "letterGid", 36, false) == null
                    && text(input, "revision", 64, false) == null, 422, "新草稿不得指定已有标识");
            row = new HyClbTemp();
            row.setVehicleId(UUID.randomUUID().toString());
            row.setLetterGid(UUID.randomUUID().toString());
            row.setOwnerId(actor.id); row.setRegion(actor.region); row.setDepartment(actor.department);
            row.setCreatedAt(new Date()); row.setIsDaily("0"); row.setDoThingStatus("0");
        } else {
            row = load(input, true);
            owner(row, actor); managed(row); fresh(row, input);
            require(row.getStatus() != null && (row.getStatus() == -1 || row.getStatus() == -3), 409, "当前状态不能修改草稿");
            from = row.getStatus();
            require(Objects.equals(row.getEnterpriseId(), text(input, "enterpriseId", 36, true)), 409, "已保存草稿不能更换业户");
        }
        row.setEnterpriseId(text(input, "enterpriseId", 36, true));
        row.setLicenseNumber(store.enterpriseLicense(row.getEnterpriseId(), actor.id));
        row.setBusinessType(text(input, "businessType", 100, true));
        require(!special(row), 422, "危险品或放射性业务需原审批配套源码");
        row.setBusinessTitle(store.businessTitle(row.getBusinessType()));
        row.setPlateNumber(upper(text(input, "plateNumber", 50, false)));
        row.setVin(upper(text(input, "vin", 50, false)));
        row.setPlateColor(text(input, "plateColor", 50, false));
        row.setVehicleType(text(input, "vehicleType", 50, false));
        row.setEngineNumber(text(input, "engineNumber", 50, false));
        row.setManufacturer(text(input, "manufacturer", 40, false));
        row.setModel(text(input, "model", 40, false));
        row.setRegistrationDate(date(text(input, "registrationDate", 10, false)));
        row.setRemarks(text(input, "remarks", 4000, false));
        validate(row, false);
        row.setStatus(-1); row.setQueueType("dbjlx_ysl_dyslj"); touch(row, actor);
        store.validateWidths(row);
        if (create) { store.persist(row); store.flush(); }
        store.event(row, actor, create ? "CREATE" : "SAVE_DRAFT", from, null);
        store.flush();
        return view(row, actor);
    }

    @Override
    public Map<String, Object> submit(VehicleActor actor, Map<String, Object> input) {
        keys(input); store.schema(false);
        HyClbTemp row = load(input, true);
        owner(row, actor); managed(row); fresh(row, input);
        require(Objects.equals(row.getStatus(), -1), 409, "只能提交已保存的草稿");
        row.setLicenseNumber(store.enterpriseLicense(row.getEnterpriseId(), actor.id));
        validate(row, true);
        store.businessTitle(row.getBusinessType()); require(!special(row), 422, "此业务需要配套审批服务");
        // HY_CLB is read only here, never auto-created or altered.
        store.assertNoDuplicate(row);
        transition(row, actor, 3, "dbjlx_ysl_dyslj", "SUBMIT", null);
        return view(row, actor);
    }

    @Override
    public Map<String, Object> updateCarForecastAccept(VehicleActor actor, Map<String, Object> input) {
        keys(input); store.schema(false);
        HyClbTemp row = load(input, true);
        reviewer(row, actor, true); managed(row); fresh(row, input);
        require(Objects.equals(row.getStatus(), 3), 409, "只允许预受理已提交的单车申请");
        validate(row, true);
        require(!special(row), 422, "此业务需要配套审批服务");
        transition(row, actor, 0, "dbjlx_sl_dsl", "PRE_ACCEPT", null);
        return view(row, actor);
    }

    @Override
    public Map<String, Object> updateCarAccept(VehicleActor actor, Map<String, Object> input) {
        keys(input, "acceptStatus", "opinion", "permitNumber", "inspectionExpiry", "inspectionEvidence", "manualVerified");
        store.schema(false);
        HyClbTemp row = load(input, true);
        reviewer(row, actor, false); managed(row); fresh(row, input);
        require(Objects.equals(row.getStatus(), 0), 409, "只允许受理状态为 0 的申请");
        String status = text(input, "acceptStatus", 20, true);
        String opinion = text(input, "opinion", 50, true);
        require("slhg".equals(status) || "slth".equals(status), 422, "仅支持受理合格或退回；补正通知分支缺少配套源码");
        row.setAcceptOpinion(opinion); row.setAcceptOfficer(actor.name); row.setAcceptedAt(new Date());
        if ("slth".equals(status)) {
            transition(row, actor, -3, "dbjlx_ysl_ylthj", "RETURN", opinion);
            return view(row, actor);
        }
        require(!special(row), 422, "危险品或放射性业务的审批/待核查分支尚未接入");
        require(manualFormalAccept, 503, "当前部署未启用人工核验受理；自动检验及证号服务尚未接入");
        require(Boolean.TRUE.equals(input.get("manualVerified")), 422, "请确认已人工核验证号及检验记录");
        store.schema(true); validate(row, true); store.assertNoDuplicate(row);
        require(row.getFormalId() == null, 409, "此申请已关联正式车辆");
        row.setPermitNumber(text(input, "permitNumber", 20, true));
        Date expiry = date(text(input, "inspectionExpiry", 10, true));
        require(expiry != null && !expiry.before(today()), 422, "检验有效期已过期");
        String evidence = text(input, "inspectionEvidence", 800, true);
        row.setInspectionExpiry(expiry);
        HyClb formal = formal(row, actor);
        store.validateWidths(row); store.validateWidths(formal);
        store.persist(formal); store.flush();
        row.setFormalId(formal.getId());
        transition(row, actor, 2, "dbjlx_sh_spture", "ACCEPT_MANUAL", opinion + "；证号：" + row.getPermitNumber()
                + "；检验有效期：" + format(expiry) + "；核验证据：" + evidence);
        return view(row, actor);
    }

    private HyClb formal(HyClbTemp row, VehicleActor actor) {
        HyClb f = new HyClb();
        f.setUClbm(row.getVehicleId()); f.setLetterGid(row.getLetterGid()); f.setUYhbm(row.getEnterpriseId());
        f.setCCh(row.getPlateNumber()); f.setCCjh(row.getVin()); f.setCCpys(row.getPlateColor());
        f.setCCllx(row.getVehicleType()); f.setCFdjh(row.getEngineNumber()); f.setNCp(row.getManufacturer());
        f.setNXh(row.getModel()); f.setNYwlx(row.getBusinessType()); f.setNSxmc(row.getBusinessTitle());
        f.setCXkzh(row.getLicenseNumber()); f.setCYyzh(row.getPermitNumber()); f.setXzqh(row.getRegion());
        f.setCLrr(row.getOwnerId()); f.setCLrbm(row.getDepartment()); f.setDLrsj(row.getCreatedAt());
        f.setDXgsj(new Date()); f.setCXgr(actor.id); f.setDCcrq(row.getRegistrationDate());
        f.setDMsqr(row.getInspectionExpiry()); f.setNBz(row.getRemarks()); f.setTZt(BigInteger.valueOf(2));
        f.setDbjlx("dbjlx_sh_spture"); f.setDoThingStatus("0"); f.setIsdaily("0");
        f.setApplicationformtype("0");
        f.setSlrxm(actor.name); f.setSlsj(row.getAcceptedAt()); f.setSlyj(row.getAcceptOpinion());
        return f;
    }
    private void transition(HyClbTemp row, VehicleActor actor, int state, String queue, String action, String detail) {
        Integer from = row.getStatus(); row.setStatus(state); row.setQueueType(queue); touch(row, actor);
        store.validateWidths(row); store.event(row, actor, action, from, detail); store.flush();
    }
    private HyClbTemp load(Map<String, Object> input, boolean lock) {
        String letter = text(input, "letterGid", 36, false), rawId = text(input, "id", 20, false);
        require(letter != null || rawId != null, 422, "需要申请标识或数值 ID");
        BigInteger id = null;
        if (rawId != null) {
            require(rawId.matches("[1-9][0-9]{0,19}"), 422, "ID 必须是十进制主键字符串");
            id = new BigInteger(rawId);
        }
        HyClbTemp row = store.single(letter, id, lock);
        require(id == null || id.equals(row.getId()), 409, "ID 与 LETTER_GID 不匹配");
        String vehicle = text(input, "vehicleId", 36, false);
        require(vehicle == null || vehicle.equals(row.getVehicleId()), 409, "U_CLBM 不匹配");
        return row;
    }
    private void readAllowed(HyClbTemp row, VehicleActor a) {
        require((a.applicant && a.id.equals(row.getOwnerId())) ||
                ((a.preAccept || a.accept) && a.region.equals(row.getRegion())), 403, "无权查看此申请");
    }
    private void owner(HyClbTemp row, VehicleActor a) {
        require(a.applicant && a.id.equals(row.getOwnerId()) && a.region.equals(row.getRegion()), 403, "无权修改他人的申请");
    }
    private void reviewer(HyClbTemp row, VehicleActor a, boolean pre) {
        require((pre ? a.preAccept : a.accept) && a.region.equals(row.getRegion()), 403, "无此区划的办理权限");
        require(!a.id.equals(row.getOwnerId()), 403, "不得审核本人申请");
    }
    private void managed(HyClbTemp row) {
        require(store.managed(row), 409, "此记录来自原业务，配套材料/历史不完整；仅允许查看，不能转换为本单车流程");
    }
    private void fresh(HyClbTemp row, Map<String, Object> input) {
        require(revision(row).equals(text(input, "revision", 64, true)), 409, "数据已更新，请重新加载后操作");
    }
    private static void touch(HyClbTemp row, VehicleActor a) { row.setUpdatedAt(new Date()); row.setUpdatedBy(a.id); }
    private static boolean special(HyClbTemp row) {
        String type = row.getBusinessType() == null ? "" : row.getBusinessType().toLowerCase(Locale.ROOT);
        return type.contains("danger") || type.contains("radiate");
    }
    private static void validate(HyClbTemp row, boolean complete) {
        if (row.getVin() != null) require(row.getVin().matches("[A-HJ-NPR-Z0-9]{17}"), 422, "VIN 必须为不含 I/O/Q 的 17 位字符");
        if (row.getPlateNumber() != null) require(row.getPlateNumber().matches("[\\p{L}0-9·\\-]{2,20}"), 422, "车牌格式不正确");
        require(row.getRegistrationDate() == null || !row.getRegistrationDate().after(today()), 422, "注册日期不能晚于今天");
        if (complete) require(row.getLicenseNumber() != null && !row.getLicenseNumber().trim().isEmpty()
                && row.getVin() != null && row.getPlateNumber() != null && row.getVehicleType() != null
                && row.getPlateColor() != null && row.getEngineNumber() != null && row.getManufacturer() != null
                && row.getModel() != null && row.getRegistrationDate() != null, 422, "请补全车牌、VIN、车辆类型、号牌颜色、发动机号、厂牌、型号和注册日期");
    }
    private Map<String, Object> snapshot(HyClbTemp r) {
        Map<String, Object> out = new TreeMap<>();
        out.put("id", r.getId() == null ? null : r.getId().toString());
        out.put("vehicleId", r.getVehicleId()); out.put("letterGid", r.getLetterGid());
        out.put("enterpriseId", r.getEnterpriseId()); out.put("plateNumber", r.getPlateNumber()); out.put("vin", r.getVin());
        out.put("plateColor", r.getPlateColor()); out.put("vehicleType", r.getVehicleType()); out.put("engineNumber", r.getEngineNumber());
        out.put("manufacturer", r.getManufacturer()); out.put("model", r.getModel()); out.put("businessType", r.getBusinessType());
        out.put("businessTitle", r.getBusinessTitle()); out.put("registrationDate", format(r.getRegistrationDate()));
        out.put("remarks", r.getRemarks()); out.put("status", r.getStatus()); out.put("region", r.getRegion());
        out.put("formalId", r.getFormalId() == null ? null : r.getFormalId().toString()); out.put("permitNumber", r.getPermitNumber());
        out.put("inspectionExpiry", format(r.getInspectionExpiry())); out.put("opinion", r.getAcceptOpinion());
        // Oracle DATE stores seconds; exclude transient Java milliseconds from the revision fingerprint.
        out.put("updatedAt", r.getUpdatedAt() == null ? null : r.getUpdatedAt().getTime() / 1000 * 1000); out.put("ownerId", r.getOwnerId());
        out.put("queueType", r.getQueueType()); out.put("doThingStatus", r.getDoThingStatus()); out.put("isDaily", r.getIsDaily());
        return out;
    }
    private String revision(HyClbTemp row) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(json.writeValueAsBytes(snapshot(row)));
            StringBuilder hex = new StringBuilder();
            for (byte value : digest) hex.append(String.format("%02x", value & 255));
            return hex.toString();
        } catch (Exception e) { throw new IllegalStateException(e); }
    }
    private Map<String, Object> view(HyClbTemp row, VehicleActor a) {
        Map<String, Object> out = snapshot(row);
        boolean managed = store.managed(row), ordinary = !special(row);
        boolean owned = a.applicant && a.id.equals(row.getOwnerId()) && a.region.equals(row.getRegion());
        boolean review = !a.id.equals(row.getOwnerId()) && a.region.equals(row.getRegion());
        out.put("revision", revision(row)); out.put("managed", managed);
        out.put("canEdit", managed && owned && (Objects.equals(row.getStatus(), -1) || Objects.equals(row.getStatus(), -3)));
        out.put("canSubmit", managed && owned && Objects.equals(row.getStatus(), -1));
        out.put("canPreAccept", managed && ordinary && review && a.preAccept && Objects.equals(row.getStatus(), 3));
        out.put("canReturn", managed && review && a.accept && Objects.equals(row.getStatus(), 0));
        out.put("canAccept", managed && ordinary && manualFormalAccept && review && a.accept && Objects.equals(row.getStatus(), 0));
        out.put("blockedReason", !managed ? "原业务记录仅供查看；缺少配套材料/审批源码" : !ordinary ?
                "危险品/放射性审批分支缺少配套源码" : !manualFormalAccept && Objects.equals(row.getStatus(), 0) ?
                "当前部署未启用人工核验受理；自动检验及证号服务尚未接入" : "");
        return out;
    }
    private static void keys(Map<String, Object> input, String... extra) {
        require(input != null, 422, "请求不能为空");
        Set<String> allowed = new HashSet<>(IDENTITY); allowed.addAll(Arrays.asList(extra));
        for (String key : input.keySet()) require(allowed.contains(key), 422, "不支持的输入字段: " + key);
    }
    private static String text(Map<String, Object> input, String key, int max, boolean required) {
        Object value = input.get(key);
        require(value == null || value instanceof String, 422, key + " 必须为字符串");
        String s = value == null ? null : ((String) value).trim();
        if (s != null && s.isEmpty()) s = null;
        require(!required || s != null, 422, key + " 不能为空");
        require(s == null || (s.length() <= max && !s.matches("(?s).*\\p{Cntrl}.*")), 422, key + " 格式或长度不正确");
        return s;
    }
    private static int integer(Map<String, Object> input, String key, int min, int max, int fallback) {
        Object value = input.get(key); if (value == null) return fallback;
        String s = value.toString(); require(s.matches("[0-9]{1,7}"), 422, "分页参数不正确");
        int n = Integer.parseInt(s); require(n >= min && n <= max, 422, "分页参数超出范围"); return n;
    }
    private static String upper(String s) { return s == null ? null : s.toUpperCase(Locale.ROOT); }
    private static Date date(String s) {
        if (s == null) return null;
        require(s.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}"), 422, "日期须为 yyyy-MM-dd");
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd"); f.setLenient(false);
        try { return f.parse(s); } catch (ParseException e) { throw new VehicleWorkflowException(422, "日期无效"); }
    }
    private static String format(Date d) { return d == null ? null : new SimpleDateFormat("yyyy-MM-dd").format(d); }
    private static Date today() { return date(format(new Date())); }
}
