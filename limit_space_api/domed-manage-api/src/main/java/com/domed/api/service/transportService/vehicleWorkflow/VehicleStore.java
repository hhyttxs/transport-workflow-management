package com.domed.api.service.transportService.vehicleWorkflow;

import com.domed.domain.transportDomain.HyClb;
import com.domed.domain.transportDomain.VehicleWorkflowAudit;
import com.domed.domain.transportDomain.transportTemp.HyClbTemp;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import javax.persistence.*;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.*;
import java.util.*;
import static com.domed.api.service.transportService.vehicleWorkflow.VehicleWorkflowException.require;

/** Uses the project's JPA EntityManager for bound queries and pessimistic locking. */
@Repository
public class VehicleStore {
    @PersistenceContext private EntityManager em;

    public HyClbTemp single(String letterGid, BigInteger id, boolean lock) {
        String jpql = letterGid != null ? "SELECT c FROM HyClbTemp c WHERE c.letterGid = :value" :
                "SELECT c FROM HyClbTemp c WHERE c.id = :value";
        TypedQuery<HyClbTemp> q = em.createQuery(jpql, HyClbTemp.class)
                .setParameter("value", letterGid != null ? letterGid : id).setMaxResults(2);
        if (lock) q.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        List<HyClbTemp> rows = q.getResultList();
        require(rows.size() != 0, 404, "未找到申请");
        require(rows.size() == 1, 409, "此申请包含多辆车，须使用原多车业务处理");
        HyClbTemp row = rows.get(0);
        // Even when opened by numeric ID, never silently process one member of a batch.
        Long count = em.createQuery("SELECT COUNT(c) FROM HyClbTemp c WHERE c.letterGid = :letter", Long.class)
                .setParameter("letter", row.getLetterGid()).getSingleResult();
        require(count == 1, 409, "此申请不是单车申请");
        return row;
    }
    public List<HyClbTemp> drafts(String owner, int page, int size) {
        return em.createQuery("SELECT c FROM HyClbTemp c WHERE c.ownerId = :owner AND c.status IN (-1,-3) " +
                "ORDER BY c.updatedAt DESC, c.id DESC", HyClbTemp.class).setParameter("owner", owner)
                .setFirstResult((page - 1) * size).setMaxResults(size).getResultList();
    }
    public long draftCount(String owner) {
        return em.createQuery("SELECT COUNT(c) FROM HyClbTemp c WHERE c.ownerId = :owner AND c.status IN (-1,-3)", Long.class)
                .setParameter("owner", owner).getSingleResult();
    }
    public boolean managed(HyClbTemp row) {
        return em.createQuery("SELECT COUNT(a) FROM VehicleWorkflowAudit a WHERE a.tempId = :id AND a.action = :action",
                Long.class).setParameter("id", row.getId()).setParameter("action", "CREATE").getSingleResult() == 1;
    }
    public String enterpriseLicense(String enterprise, String owner) {
        List<?> rows = em.createNativeQuery("SELECT XKZH FROM HY_YHXX WHERE ID = :id AND CREATOR = :owner")
                .setParameter("id", enterprise).setParameter("owner", owner).setMaxResults(2).getResultList();
        require(rows.size() == 1, 403, "业户不存在或不属于当前账号");
        return rows.get(0) == null ? null : rows.get(0).toString();
    }
    public String businessTitle(String key) {
        List<?> rows = em.createNativeQuery("SELECT VALUE FROM HY_DICTIONARY WHERE KEY = :key")
                .setParameter("key", key).setMaxResults(2).getResultList();
        require(rows.size() == 1 && rows.get(0) != null, 422, "请选择数据库中有效的业务类型编码");
        String title = rows.get(0).toString();
        require(title.contains("运输证") && title.contains("核发"), 422, "业务类型不是道路运输证核发");
        return title;
    }
    public void assertNoDuplicate(HyClbTemp row) {
        List<?> formal = em.createNativeQuery("SELECT ID FROM HY_CLB WHERE C_CJH = :vin")
                .setParameter("vin", row.getVin()).setMaxResults(1).getResultList();
        require(formal.isEmpty(), 409, "此 VIN 已存在正式车辆记录");
        Long pending = em.createQuery("SELECT COUNT(c) FROM HyClbTemp c WHERE c.vin = :vin " +
                "AND c.id <> :id AND c.status NOT IN (-3,-1)", Long.class)
                .setParameter("vin", row.getVin()).setParameter("id", row.getId()).getSingleResult();
        require(pending == 0, 409, "此 VIN 已有在办申请");
    }
    public void persist(Object row) { em.persist(row); }
    public Map<String, Object> options(String owner) {
        Map<String, Object> out = new LinkedHashMap<>();
        List<Map<String, String>> enterprises = new ArrayList<>(), types = new ArrayList<>();
        for (Object item : em.createNativeQuery("SELECT ID, SQRMC FROM HY_YHXX WHERE CREATOR = :owner ORDER BY SQRMC")
                .setParameter("owner", owner).setMaxResults(500).getResultList()) {
            Object[] row = (Object[]) item;
            Map<String, String> option = new LinkedHashMap<>();
            option.put("value", String.valueOf(row[0])); option.put("label", String.valueOf(row[1])); enterprises.add(option);
        }
        for (Object item : em.createNativeQuery("SELECT KEY, VALUE FROM HY_DICTIONARY WHERE VALUE LIKE :title ORDER BY KEY")
                .setParameter("title", "%运输证%核发%").setMaxResults(200).getResultList()) {
            Object[] row = (Object[]) item;
            String key = String.valueOf(row[0]).toLowerCase(Locale.ROOT);
            if (key.contains("danger") || key.contains("radiate")) continue;
            Map<String, String> option = new LinkedHashMap<>();
            option.put("value", String.valueOf(row[0])); option.put("label", String.valueOf(row[1])); types.add(option);
        }
        out.put("enterprises", enterprises); out.put("businessTypes", types); return out;
    }
    public void flush() { em.flush(); }

    public void event(HyClbTemp row, VehicleActor actor, String action, Integer from, String detail) {
        VehicleWorkflowAudit audit = new VehicleWorkflowAudit();
        audit.setEventId(UUID.randomUUID().toString());
        audit.setTempId(row.getId()); audit.setVehicleId(row.getVehicleId()); audit.setLetterGid(row.getLetterGid());
        audit.setActorId(actor.id); audit.setAction(action); audit.setFromState(from); audit.setToState(row.getStatus());
        audit.setEventTime(new java.util.Date()); audit.setDetail(detail);
        if ("ACCEPT_MANUAL".equals(action)) audit.setAcceptedVin(row.getVin());
        persist(audit);
    }

    /** No DDL: verify the live current-schema contract before touching partially known tables. */
    public void schema(boolean formal) {
        em.unwrap(Session.class).doWork(connection -> {
            checkMapping(connection, HyClbTemp.class);
            checkMapping(connection, VehicleWorkflowAudit.class);
            Map<String, Set<String>> uniqueIndexes = new HashMap<>();
            try (ResultSet indexes = connection.getMetaData().getIndexInfo(null, connection.getSchema(),
                    "VEHICLE_WORKFLOW_AUDIT", true, false)) {
                while (indexes.next()) {
                    String name = indexes.getString("INDEX_NAME"), column = indexes.getString("COLUMN_NAME");
                    if (name != null && column != null && !indexes.getBoolean("NON_UNIQUE"))
                        uniqueIndexes.computeIfAbsent(name, key -> new HashSet<>()).add(column.toUpperCase(Locale.ROOT));
                }
            }
            require(uniqueIndexes.values().stream().anyMatch(columns -> columns.equals(Collections.singleton("ACCEPTED_VIN"))),
                    503, "缺少单车核发 VIN 唯一约束，请核对审核后的数据库脚本");
            if (formal) checkMapping(connection, HyClb.class);
            if (connection.getMetaData().getDatabaseProductName().toLowerCase(Locale.ROOT).contains("oracle")) {
                for (String seq : formal ? new String[]{"HY_CLB_TEMP_SEQ", "HY_CLB_SEQ"} : new String[]{"HY_CLB_TEMP_SEQ"}) {
                    try (PreparedStatement ps = connection.prepareStatement(
                            "SELECT COUNT(*) FROM ALL_SEQUENCES WHERE SEQUENCE_OWNER = SYS_CONTEXT('USERENV','CURRENT_SCHEMA') AND SEQUENCE_NAME = ?")) {
                        ps.setString(1, seq);
                        try (ResultSet rs = ps.executeQuery()) {
                            rs.next(); require(rs.getInt(1) == 1, 503, "缺少经确认的数据库序列: " + seq);
                        }
                    }
                }
            }
        });
    }
    private static void checkMapping(Connection c, Class<?> type) throws SQLException {
        String table = type.getAnnotation(Table.class).name();
        Map<String, Field> expected = new HashMap<>();
        for (Field f : type.getDeclaredFields()) {
            Column col = f.getAnnotation(Column.class);
            if (col != null) expected.put(col.name(), f);
        }
        Map<String, Integer> actual = new HashMap<>();
        String schema = c.getSchema();
        try (ResultSet rs = c.getMetaData().getColumns(null, schema, table, null)) {
            while (rs.next()) {
                String col = rs.getString("COLUMN_NAME").toUpperCase(Locale.ROOT);
                int jdbcType = rs.getInt("DATA_TYPE");
                actual.put(col, jdbcType);
                if (!expected.containsKey(col) && rs.getInt("NULLABLE") == DatabaseMetaData.columnNoNulls
                        && rs.getString("COLUMN_DEF") == null && !"YES".equals(rs.getString("IS_AUTOINCREMENT")))
                    throw new VehicleWorkflowException(503, table + "." + col + " 是未知必填列，需完整数据库定义");
            }
        }
        for (Map.Entry<String, Field> entry : expected.entrySet()) {
            Integer jdbc = actual.get(entry.getKey());
            require(jdbc != null, 503, "数据库缺少已验证映射列: " + table + "." + entry.getKey());
            Class<?> fieldType = entry.getValue().getType();
            boolean matches = fieldType == String.class
                    ? Arrays.asList(Types.VARCHAR, Types.NVARCHAR, Types.CHAR, Types.NCHAR).contains(jdbc)
                    : java.util.Date.class.isAssignableFrom(fieldType)
                    ? Arrays.asList(Types.DATE, Types.TIMESTAMP, Types.TIMESTAMP_WITH_TIMEZONE).contains(jdbc)
                    : Arrays.asList(Types.NUMERIC, Types.DECIMAL, Types.INTEGER, Types.BIGINT, Types.SMALLINT).contains(jdbc);
            require(matches, 503, "数据库列类型不兼容: " + table + "." + entry.getKey());
        }
    }
    public void validateWidths(Object entity) {
        em.unwrap(Session.class).doWork(c -> {
            boolean oracle = c.getMetaData().getDatabaseProductName().toLowerCase(Locale.ROOT).contains("oracle");
            String table = entity.getClass().getAnnotation(Table.class).name();
            for (Field f : entity.getClass().getDeclaredFields()) {
                Column col = f.getAnnotation(Column.class);
                if (col == null || f.getType() != String.class) continue;
                try {
                    f.setAccessible(true);
                    String value = (String) f.get(entity);
                    if (value == null) continue;
                    if (oracle) {
                        try (PreparedStatement ps = c.prepareStatement("SELECT DATA_LENGTH, CHAR_LENGTH, CHAR_USED " +
                                "FROM ALL_TAB_COLUMNS WHERE OWNER = SYS_CONTEXT('USERENV','CURRENT_SCHEMA') AND TABLE_NAME = ? AND COLUMN_NAME = ?")) {
                            ps.setString(1, table); ps.setString(2, col.name());
                            try (ResultSet rs = ps.executeQuery()) {
                                require(rs.next(), 503, "无法验证列长度");
                                boolean chars = "C".equals(rs.getString(3));
                                int max = rs.getInt(chars ? 2 : 1);
                                int length = value.codePointCount(0, value.length());
                                if (!chars) try (PreparedStatement bytes = c.prepareStatement("SELECT LENGTHB(?) FROM DUAL")) {
                                    bytes.setString(1, value);
                                    try (ResultSet b = bytes.executeQuery()) { b.next(); length = b.getInt(1); }
                                }
                                require(length <= max, 422, col.name() + " 超出数据库长度");
                            }
                        }
                    } else {
                        try (ResultSet rs = c.getMetaData().getColumns(null, c.getSchema(), table, col.name())) {
                            require(rs.next() && value.length() <= rs.getInt("COLUMN_SIZE"), 422, col.name() + " 超出数据库长度");
                        }
                    }
                } catch (IllegalAccessException e) { throw new IllegalStateException(e); }
            }
        });
    }
}
