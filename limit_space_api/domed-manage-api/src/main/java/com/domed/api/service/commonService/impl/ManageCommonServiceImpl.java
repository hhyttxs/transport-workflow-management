package com.domed.api.service.commonService.impl;

import cn.hutool.core.io.FileUtil;
import com.domed.api.service.commonService.IManageCommonService;
import com.domed.common.result.Result;
import com.domed.constant.Constant;
import com.domed.core.dao.IGenericDAO;
import com.domed.core.exception.ApplicationException;
import com.domed.core.query.support.IPageList;
import com.domed.core.service.ICommonService;
import com.domed.domain.cascaderCommon.CommonTree;
import com.domed.service.IDelegator;
import com.domed.util.CascaderUtil;
import com.domed.util.OSSUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.domed.util.UtilMisc.*;


@Service
@Transactional
public class ManageCommonServiceImpl implements IManageCommonService {

    @Autowired
    private IDelegator delegator;

    @Autowired
    private OSSUtil oSSUtil;

    @Autowired
    private ICommonService commonService;


    @Override
    public Result queryBaseData(Map infoMap) {
        String dbName = this.commonService.getParam(infoMap, "dbName");  //数据库分类名称
        String fdName = this.commonService.getParam(infoMap, "fdName");  //常量分类名称npm
        Map retMap = new HashMap();
        if (!"".equals(dbName)) {
            List<String> dbNameList = this.commonService.getParamList(infoMap, "dbName");
            if (dbNameList.size() > 0) {
                List<Map> baseDataList = delegator.findByView(
                        toMap("t_dictionary d",
                                toList(
                                        toMap("t_dictionaryClass dc ",
                                                toList(
                                                        equalsExp("dc.deleteStatus", 0),
                                                        equalsExp("dc.id", "d.dictionaryClass_id")
                                                )
                                        )
                                )
                        ),
                        toList("d.id",
                                "dc.dictionaryClassName",
                                "d.dictionaryName",     //字典名称
                                "d.dictionaryDescription",      //字典描述
                                "d.dictionaryTitle",            //字典标题
                                "d.dictionaryValue"),           //代表值
                        toList(
                                equalsExp("d.deleteStatus", 0),
                                inStrExp("dc.dictionaryClassName", dbNameList)
                        ),
                        toList("d.seq ASC")//排序
                );
                retMap = this.commonService.Linkedlist2MapGroupBy(baseDataList, "dictionaryClassName");
            }
        }
        if (!"".equals(fdName)) {
            List<String> fdNameList = this.commonService.getParamList(infoMap, "fdName");
            if (fdNameList != null && fdNameList.size() > 0) {
                for (int i = 0; i < fdNameList.size(); i++) {
                    String name = fdNameList.get(i);
                    List list = Constant.getValues(name);
                    retMap.put(name, list);
                }
            }
        }
        //zhushixin
        return Result.success(retMap);
    }

    @Override
    public String queryBaseDataValue(String id) {
        String retName = "";
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append("   d.dictionaryTitle ");
        sql.append(" FROM ");
        sql.append("   t_dictionary AS d ");
        sql.append(" WHERE ");
        sql.append("   d.deleteStatus = '0' ");
        sql.append("   AND d.id = :id ");
        Map params = new HashMap();
        params.put("id", id);
        List<Map> list = this.commonService.nativeQuery(sql.toString(), params);
        if (list != null && list.size() > 0) {
            retName = this.commonService.getParam(list.get(0), "dictionaryTitle");
        }
        return retName;
    }

    @Override
    public Result<List<Map>> queryStaffList(Map infoMap) {
        String sectionDepartment_id = this.commonService.getParam(infoMap, "department_id");
        String id = this.commonService.getParam(infoMap, "id");
        String searchName = this.commonService.getParam(infoMap, "searchName");
        String departmentDown_id = this.commonService.getParam(infoMap, "departmentDown_id");

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append("   s.id, ");
        sql.append("   s.staffName, ");
        sql.append("   s.mobile, ");
        sql.append("   s.department_id, ");
        sql.append("   s.id as value, ");
        sql.append("   s.staffName as title, ");
        sql.append("   ss.staffName AS assStaffName, ");  //创建人员工姓名
        sql.append("   d.departmentName ");  //员工部门
        sql.append(" FROM ");
        sql.append("   t_staff AS s ");
        sql.append("   LEFT JOIN t_department AS d ON d.id = s.department_id ");
        sql.append("   LEFT JOIN t_staff AS ss ON ss.id = s.addStaff_id "); // 创建人员id
        sql.append(" WHERE ");
        sql.append("   s.deleteStatus = '0' ");
        sql.append("   and s.staffStatus != " + Constant.getValueLast("STAFFSTATUSMAP", "leave"));
        if (!"".equals(sectionDepartment_id)) {
            sql.append("   and s.department_id = " + sectionDepartment_id);
        }
        if (!"".equals(searchName)) {
            sql.append("  and (s.staffName LIKE '%" + searchName + "%'");
            sql.append("  or s.mobile LIKE '%" + searchName + "%')");
        }
        if (!"".equals(id)) {
            sql.append("   and s.id = " + id);
        }
        if (!"".equals(departmentDown_id)) {
            sql.append("   and (d.levelTop like '" + departmentDown_id + ",%' or s.department_id = " + departmentDown_id + ") ");
        }
        sql.append("   order by s.addTime desc ");
        List<Map> staffList = this.commonService.nativeQuery(sql.toString());
        return Result.success(staffList);
    }

    @Override
    public Result saveFileToOss(MultipartFile multipartFile, String saveFilePathName) throws IOException {
        MultipartFile file = multipartFile;
        Map map = new HashMap();
        String saveFileName = "";
        if ((file != null) && (!file.isEmpty())) {
            if ((saveFileName == null) || (saveFileName.trim().equals(""))) {
                saveFileName = UUID.randomUUID().toString();
            }
            float fileSize = Float.valueOf((float) file.getSize()).floatValue();
            InputStream is = file.getInputStream();
            InputStream isTemp = file.getInputStream();
            BufferedImage image = ImageIO.read(isTemp);
            if (image != null) {
                String path = oSSUtil.upload(is, saveFilePathName + saveFileName, (int) fileSize);
                int w = image.getWidth();
                int h = image.getHeight();
//                map.put("width", Integer.valueOf(w));
//                map.put("height", Integer.valueOf(h));
//                map.put("mime", "");
//                map.put("fileName", saveFileName);
//                map.put("fileSize", Float.valueOf(fileSize));
                map.put("dbPath", saveFilePathName + saveFileName);
                map.put("path", path);
//                map.put("error", "");
            } else {
//                map.put("width", Integer.valueOf(0));
//                map.put("height", Integer.valueOf(0));
//                map.put("mime", "");
//                map.put("fileName", "");
//                map.put("fileSize", Float.valueOf(0.0F));
//                map.put("oldName", "");
                map.put("dbPath", "");
                map.put("path", "");
                map.put("error", "图片文件已损坏");
            }
        } else {
//            map.put("width", Integer.valueOf(0));
//            map.put("height", Integer.valueOf(0));
//            map.put("mime", "");
//            map.put("fileName", "");
//            map.put("fileSize", Float.valueOf(0.0F));
//            map.put("oldName", "");
            map.put("dbPath", "");
            map.put("path", "");
            map.put("error", "图片文件不存在");
        }
        return Result.success(map);
    }

    @Override
    public Result saveStringToOss(String base64, String saveFilePathName) {
        Map rtnMap = null;
        if ((base64 != null) && (!base64.equals(""))) {

            String ext = "";
            if (!(base64 == null || base64.equals(""))) {
                ext = base64.substring(base64.indexOf("/") + 1, base64.indexOf(";"));
                boolean flag = "gif,jpg,jpeg,bmp,png".contains(ext);
                if (!flag) {
                    ext = "png";
                }
                base64 = base64.substring(base64.indexOf("base64,") + 7);
            }
            String saveFileName = UUID.randomUUID().toString() + "." + ext;//图片名
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                // Base64解码
                byte[] bytes = decoder.decodeBuffer(base64);
                int fileSize = bytes.length;
                InputStream input = new ByteArrayInputStream(bytes);
                InputStream isTemp = new ByteArrayInputStream(bytes);
                BufferedImage image = ImageIO.read(isTemp);
                rtnMap = new HashMap();
                if (image != null) {
                    String rtnPath = oSSUtil.upload(input, saveFilePathName + saveFileName, fileSize);
                    int w = image.getWidth();
                    int h = image.getHeight();

//                    rtnMap.put("width", Integer.valueOf(w));
//                    rtnMap.put("height", Integer.valueOf(h));
//                    rtnMap.put("mime", ext);
//                    rtnMap.put("fileName", saveFileName);
//                    rtnMap.put("fileSize", Float.valueOf(fileSize));
                    rtnMap.put("dbPath", saveFilePathName + saveFileName);
                    rtnMap.put("path", rtnPath);
//                    rtnMap.put("error", "");
                } else {
//                    rtnMap.put("width", Integer.valueOf(0));
//                    rtnMap.put("height", Integer.valueOf(0));
//                    rtnMap.put("mime", "");
//                    rtnMap.put("fileName", "");
//                    rtnMap.put("fileSize", Float.valueOf(0.0F));
//                    rtnMap.put("oldName", "");
                    rtnMap.put("dbPath", "");
                    rtnMap.put("path", "");
                    rtnMap.put("error", "图片文件已损坏");
                }
                return Result.success(rtnMap);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.success(rtnMap);
            }

        }
        return Result.success(rtnMap);

    }

    /**
     * @param
     * @throws
     * @Title: queryEditHistory
     * @Description: 查询修改历史
     * @return:
     * @author: shanyuwei
     * Create Date:  2019/12/28
     */
    @Override
    public Result queryEditHistory(Map infoMap) {
        Map retMap = new HashMap();
        String editType = this.commonService.notNull(infoMap, "editType", "修改类型");    //修改类型(0 合同 1 员工 2账单)
        String item_id = this.commonService.notNull(infoMap, "item_id", "项目id");    //项目id
        String currentPage = this.commonService.notNull(infoMap, "currentPage", "当前页"); //当前页
        String numberPage = this.commonService.notNull(infoMap, "numberPage", "每页条数"); //每页条数

        String name = this.commonService.getParam(infoMap, "name"); //操作人
        String description = this.commonService.getParam(infoMap, "description"); //变更内容

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append("   DATE_FORMAT(e.addTime, '%Y-%m-%d' ) as addTime, ");
        sql.append("   s.staffName, ");
        sql.append("   e.editFieldDescription, ");
        sql.append("   e.originalDescription, ");
        sql.append("   e.newDescription ");
        sql.append(" FROM ");
        sql.append("   t_edithistory as e ");
        sql.append("   left join t_staff as s on s.id = e.addStaff_id ");
        sql.append(" WHERE ");
        sql.append("   e.deleteStatus = '0' ");
        sql.append("   AND e.editType in (" + editType + ") ");
        sql.append("   AND e.item_id = :item_id ");
        Map params = new HashMap();
//        params.put("editType",editType);
        params.put("item_id", item_id);
        if (!"".equals(name)) {
            sql.append("   AND s.staffName like '%" + name + "%' ");
        }
        if (!"".equals(description)) {
            sql.append("   AND e.editFieldDescription like '%" + description + "%' ");
        }
        sql.append("  ORDER BY e.addTime DESC ");
        IPageList iPageList = commonService.pageQuery(sql.toString(), currentPage, numberPage, params);
        retMap.put("data", iPageList.getResult());
        retMap.put("pageCount", iPageList.getRowCount());
        retMap.put("totalPage", iPageList.getPages());
        return Result.success(retMap);
    }

    /**
     * 获取员工职务类型
     * @param staff_id
     * @return
     */
    @Override
    public String getStaffDutyType(String staff_id) throws Exception {
        String[] dutyTypesPolicies = {"butlerDuty", "businessManagerDuty", "directorDuty"};
        Result<Map> policyDecisionResult = this.queryPolicyDecision(dutyTypesPolicies);
        Map policyDecision = policyDecisionResult.getData();
        List butlerDutyList = this.commonService.listMap2PropList(this.commonService.getParamList(policyDecision, "butlerDuty"), "policyDecisionDetailValue");
        List businessManagerDutyList = this.commonService.listMap2PropList(this.commonService.getParamList(policyDecision, "businessManagerDuty"), "policyDecisionDetailValue");
        List directorDutyList = this.commonService.listMap2PropList(this.commonService.getParamList(policyDecision, "directorDuty"), "policyDecisionDetailValue");

        String duty_id = this.queryDutyIdFromStaff(staff_id);

        if (butlerDutyList.contains(duty_id)) return Constant.getValueLast("FOLLOWUPSUBTYPEMAP", "butler");
        if (businessManagerDutyList.contains(duty_id))
            return Constant.getValueLast("FOLLOWUPSUBTYPEMAP", "businessManager");
        if (directorDutyList.contains(duty_id)) return Constant.getValueLast("FOLLOWUPSUBTYPEMAP", "director");

        return Constant.getValueLast("FOLLOWUPSUBTYPEMAP", "another");
    }

    private String queryDutyIdFromStaff(String staff_id) {
        if (staff_id == null || "".equals(staff_id)) return "";

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append(" s.duty_id ");
        sb.append(" FROM t_staff s ");
        sb.append(" WHERE 1 = 1 ");
        sb.append(" AND s.deleteStatus = 0 ");
        sb.append(" AND s.id = :id ");
        Map params = new HashMap();
        params.put("id", staff_id);

        List<Map> list = this.commonService.nativeQuery(sb.toString(), params);
        if (list == null || list.size() == 0) {
            return "";
        }

        Map staffInfo = list.get(0);
        return commonService.getParam(staffInfo, "duty_id");
    }

    @Override
    public Result<Map> queryPolicyDecision(String... dutyTypesPolicies) throws Exception {
        Map result = new HashMap();

        if (dutyTypesPolicies.length == 0) dutyTypesPolicies = new String[]{"-1"};

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append(" pd.id, ");
        sb.append(" pd.policyDecisionType, ");
        sb.append(" pd.policyDecisionValue, ");
        sb.append(" pd.policyDecisionName, ");
        sb.append(" pdd.policyDecisionDetailValue, ");
        sb.append(" pdd.policyDecisionDetailDescription ");
        sb.append(" FROM t_policydecision pd  ");
        sb.append(" LEFT JOIN t_policydecisiondetail pdd ON pd.id = pdd.policyDecision_id ");
        sb.append(" WHERE 1 = 1 ");
        sb.append(" AND pd.deleteStatus = 0 ");
        sb.append(" AND ( ");
        sb.append("         pd.policyDecisionType = " + Constant.getValueLast("POLICYDECISIONTYPEMAP", "value") + " ");
        sb.append("         OR ( ");
        sb.append("                  pd.policyDecisionType = " + Constant.getValueLast("POLICYDECISIONTYPEMAP", "list") + " ");
        sb.append("                   AND pdd.deleteStatus = 0");
        sb.append("          ) ");
        sb.append("         OR ( ");
        sb.append("                  pd.policyDecisionType = " + Constant.getValueLast("POLICYDECISIONTYPEMAP", "map") + " ");
        sb.append("                   AND pdd.deleteStatus = 0");
        sb.append("          ) ");
        sb.append(" ) ");
        sb.append(" AND pd.policyDecisionName in (:names) ");

        Map params = new HashMap();
        params.put("names", Arrays.asList(dutyTypesPolicies));

        List<Map> list = this.commonService.nativeQuery(sb.toString(), params);

        Map policyMap = this.commonService.Linkedlist2MapGroupBy(list, "id");
        for (Object obj : policyMap.keySet()) {
            String key = obj.toString();
            List subList = this.commonService.getParamList(policyMap, key);
            Map firstItem = (Map) subList.get(0);
            String policyDecisionType = this.commonService.getParam(firstItem, "policyDecisionType");
            String policyDecisionName = this.commonService.getParam(firstItem, "policyDecisionName");
            String value = this.commonService.getParam(firstItem, "policyDecisionValue");

            if (Constant.getValueLast("POLICYDECISIONTYPEMAP", "value").equals(policyDecisionType))
                result.put(policyDecisionName, value);
            else result.put(policyDecisionName, subList);
        }


        return Result.success(result);
    }

    @Override
    public Date getDate(String time) throws ParseException {
        Date d = new Date();
        if (time != null && !"".equals(time)) {
            time = time.replace("Z", " UTC");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            d = sdf1.parse(time);
        }
        return d;
    }

    @Override
    public Date stringToDate(String time) throws ParseException {
        Date d = new Date();
        if (time != null && !"".equals(time)) {
            time = time.replace("Z", " UTC");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            d = sdf1.parse(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            time = sdf.format(d);
            d = sdf.parse(time);
        }
        return d;
    }

    @Override
    public String getFormatDate(String time, String format) throws ParseException {
        if (time != null && !"".equals(time)) {
            Date d = getDate(time);
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            time = sdf.format(d);
        }
        return time;
    }

    /**
     * @param request
     * @throws
     * @Title: getRemoteHost
     * @Description: 获取请求ip
     * @return: String
     * @author: syw
     * Create Date: 2017-8-25 下午3:39:40
     */
    @Override
    public String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    /**
     * @Title: 保存文件到本地
     * @Description:
     * @author 高杨
     * @date 2022/4/25 18:29
     * @return:
     */
    @Override
    public Result saveFileToLocal(HttpServletRequest request) throws IOException, ServletException {
        Map result = new HashMap();
        InputStream in = null;
        DataOutputStream out = null;
        MultipartFile partFile = null;
        int fileSize = 0;
        String fontShowName = "";
        String fileName = "";//request.getParameter("fileName");
        String relativePath = request.getParameter("relativePath");
        if(relativePath == null || relativePath.equals("")){
            throw new ApplicationException("相对路径不能为空。");
        }

        String localFilePath = oSSUtil.getLOCAL_FILE_PATH();
        String ACCESS_URL = oSSUtil.getACCESS_URL();

        for(Part part : request.getParts()){
            if(part.getName().equals("file")){
                in = part.getInputStream();
                fileSize = (int) part.getSize();
                fileName = part.getSubmittedFileName();
                fontShowName = fileName;
            }
        }
        if (in != null) {
            try {
                int extIndex = fileName.lastIndexOf(".");
                String extend = "";
                // 文件类型判断 边鹏瑞
                String fileType = "";
                if (extIndex >= 0) {
                    extend = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    fileName = UUID.randomUUID().toString() + "." + extend;

                    boolean existsImg =  "bmp,jpg,png,gif,jpeg".contains(extend);
                    if(existsImg){
                        fileType = Constant.getValueLast("FILETYPEMAP","img");
                    }else if("pdf".contains(extend)) {
                        fileType = Constant.getValueLast("FILETYPEMAP","pdf");
                    }else {
                        fileType = Constant.getValueLast("FILETYPEMAP","file");
                    }
                } else {
                    fileName = UUID.randomUUID().toString();
                }
                localFilePath = localFilePath  + File.separator + relativePath; //相对文件名的路径，例如：d:/XXX/file/,其中file就是业务名称
                String saveFilePathName = localFilePath + File.separator + fileName;
                File path = new File(localFilePath);
                if (!path.exists()) {
                    path.mkdirs();
                }
//                out = new DataOutputStream(new FileOutputStream(saveFilePathName));
//                byte[] buffer = new byte[fileSize];
//                while (in.read(buffer) > 0) {
//                    out.write(buffer);
//                }
                FileUtil.writeFromStream(in,saveFilePathName);
                result.put("fileType",fileType);
                result.put("ext", extend);
                result.put("size", fileSize + "");
                result.put("fileName", fontShowName);
                result.put("relativePath", "/" + relativePath + "/" + fileName);
                result.put("path", "/" + ACCESS_URL + "/" + fileName);
            } finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }
        }
        return Result.success(result);
    }
    /**
     * @Description: 文件下载
    　* @param
    　* @return
    　* @throws
    　* @author 姜雪鹰
    　* @date 2022/4/30 1:49
    　*/
    @Override
    public void fileDownload(HttpServletRequest request, HttpServletResponse response)  {
        String path = request.getParameter("path");
        String fileName = request.getParameter("fileName");
        String ext = request.getParameter("ext");
        String filePath =  oSSUtil.getLOCAL_FILE_PATH() + path;
        InputStream in = null;
        ServletOutputStream out = null;
        try {
            in = new FileInputStream(filePath);// 文件的存放路径
            response.setContentType("text/plain;charset=utf-8");
            response.setHeader("Content-Type","application/force-download");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName + "." + ext, "UTF-8"));
            out = response.getOutputStream();
            byte[] b = new byte[1024];
            int len;
            //从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
            while ((len = in.read(b)) > 0) {
                out.write(b, 0, len);
            }
            out.flush();
        }catch(IOException e){
            throw new ApplicationException("文件下载失败");
        }finally{
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                throw new ApplicationException("文件下载失败");
            }
        }

    }


    /**
     * @Description: 文件预览(PDF)
    　* @param
    　* @return
    　* @throws
    　* @author 姜雪鹰
    　* @date 2022/4/30 1:49
    　*/
    @Override
    public void fileCheck(HttpServletRequest request, HttpServletResponse response)  {
        String path = request.getParameter("path");
        String fileName = request.getParameter("fileName");
        String ext = request.getParameter("ext");
        String filePath =  oSSUtil.getLOCAL_FILE_PATH() + path;
        InputStream in = null;
        ServletOutputStream out = null;
        try {
            in = new FileInputStream(filePath);// 文件的存放路径
            response.setContentType("text/html; charset=UTF-8");
//            response.setHeader("Content-Type","application/pdf");
//            response.setHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(fileName + "." + ext, "UTF-8"));
            if("pdf".equalsIgnoreCase(ext)){
                response.setContentType("application/pdf");
            }else{
                response.setContentType("image/"+ext);
            }
            out = response.getOutputStream();
            byte[] b = new byte[1024];
            int len;
            //从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
            while ((len = in.read(b)) > 0) {
                out.write(b, 0, len);
            }
            out.flush();
        }catch(IOException e){
            throw new ApplicationException("文件预览失败");
        }finally{
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                throw new ApplicationException("文件预览失败");
            }
        }

    }

    @Override
    public void fileCheckKq(HttpServletRequest request, HttpServletResponse response)  {
        String path = request.getParameter("path");
        String fileName = request.getParameter("fileName");
        String ext = request.getParameter("ext");
        String filePath =  "D:\\ideaplace\\hkws\\" + path;
        InputStream in = null;
        ServletOutputStream out = null;
        try {
            in = new FileInputStream(filePath);// 文件的存放路径
            response.setContentType("text/html; charset=UTF-8");
//            response.setHeader("Content-Type","application/pdf");
//            response.setHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(fileName + "." + ext, "UTF-8"));
            if("pdf".equalsIgnoreCase(ext)){
                response.setContentType("application/pdf");
            }else{
                response.setContentType("image/"+ext);
            }
            out = response.getOutputStream();
            byte[] b = new byte[1024];
            int len;
            //从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
            while ((len = in.read(b)) > 0) {
                out.write(b, 0, len);
            }
            out.flush();
        }catch(IOException e){
            throw new ApplicationException("文件预览失败");
        }finally{
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                throw new ApplicationException("文件预览失败");
            }
        }

    }



    @Override
    public Result<Map> getXzjgTree() {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT CODE \"code\",PARENT_CODE \"parentCode\",NAME \"name\" from XZJG WHERE STATUS = 1");
        List<Map<String,String>> list = commonService.nativeQuery(sql.toString());
        List<CommonTree> commonTrees = new ArrayList<CommonTree>();
        for(Map<String,String> m : list){
            CommonTree commonTree = new CommonTree();
            commonTree.setValue(m.get("code"));
            commonTree.setParentCode(m.get("parentCode"));
            commonTree.setLabel(m.get("name"));
            commonTrees.add(commonTree);
        }
        List<CommonTree> commonTrees1 = CascaderUtil.genTree(commonTrees);
        Map result = new HashMap();
        result.put("result",commonTrees1);
        return Result.success(result);
    }
    @Override
    public Result<Map> getXzjgTreePart2() {
        StringBuffer sql = new StringBuffer();
        StringBuffer userSql = new StringBuffer();
        HashMap resultMap = new HashMap();
        List resultTree = new ArrayList();
        HashMap resultR1 = new HashMap();
        HashMap resultR2 = new HashMap();
        resultR1.put("code","parent");
        resultR1.put("parentCode","-1");
        resultR1.put("title","运管处");
        resultR2.put("code","parentOther");
        resultR2.put("parentCode","-1");
        resultR2.put("title","其他");
        sql.append(" SELECT CODE \"code\",NAME \"title\",CODE \"children-key\" from XZJG WHERE STATUS = 1");
        List<Map> listJg = commonService.nativeQuery(sql.toString());
        userSql.append(" SELECT yhwh.CODE \"titleCode\",yhwh.CODE \"children-key\",yhwh.NAME \"title\",qx.XZJG_K_CODE \"kCode\",qx.XZJG_S_CODE \"sCode\",qx.XZJG_Z_CODE \"zCode\" FROM TAB_YHWH yhwh LEFT JOIN YHQX  qx ON qx.GID = yhwh.YHQX_GID ");
        List<Map> listUser = commonService.nativeQuery(userSql.toString());
        resultR2.put("children",listUser.stream().filter(r -> StringUtils.isEmpty((String)r.get("kCode"))&&StringUtils.isEmpty((String)r.get("sCode"))&&StringUtils.isEmpty((String)r.get("zCode")))
                .collect(Collectors.toList()));
        for(Map m : listJg){
            List resUse = null;
            resUse = listUser.stream().filter(r -> m.get("code").equals(r.get("kCode"))||m.get("code").equals(r.get("sCode"))||m.get("code").equals(r.get("zCode")))
                    .collect(Collectors.toList());
            m.put("children",resUse);
        }
        resultR1.put("children",listJg);
        resultTree.add(resultR1);
        resultTree.add(resultR2);
        resultMap.put("data",resultTree);
        return Result.success(resultMap);
    }
    @Override
    public Result<Map> getXzqhList() {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT CODE \"code\",PARENT_CODE \"parentCode\",NAME \"name\" from XZJG WHERE STATUS = 1");
        List<Map<String,String>> list = commonService.nativeQuery(sql.toString());
        List<CommonTree> commonTrees = new ArrayList<CommonTree>();
        for(Map<String,String> m : list){
            CommonTree commonTree = new CommonTree();
            commonTree.setValue(m.get("code"));
            commonTree.setParentCode(m.get("parentCode"));
            commonTree.setLabel(m.get("name"));
            commonTrees.add(commonTree);
        }
        List<CommonTree> commonTrees1 = CascaderUtil.genTree(commonTrees);
        Map result = new HashMap();
        result.put("result",commonTrees1);
        return Result.success(result);
    }

    @Override
    public Result<Map> getSignLog(Map infoMap) {
        String ip = commonService.getParam(infoMap, "ip");
        String code = commonService.getParam(infoMap,"code");
        String start = commonService.getParam(infoMap, "start");
        String end = commonService.getParam(infoMap, "end");
        String currentPage = commonService.getParam(infoMap,"currentPage");
        String numberPage = commonService.getParam(infoMap,"numberPage");
        StringBuffer sbu = new StringBuffer();
        sbu.append(" SELECT * FROM TAB_SYSTEM_LOGIN_LOG WHERE 1=1 ");
        if(StringUtils.isNotEmpty(ip)){
            sbu.append(" AND LOGINIP like '%"+ip+"%'");
        }
        if(StringUtils.isNotEmpty(code)){
            sbu.append(" AND LOGINIP like '%"+code+"%'");
        }
        if(StringUtils.isNotEmpty(start)){
            sbu.append(" AND ADDTIME >= TO_DATE('" + start + "','yyyy-MM-dd hh24:mi:ss')");
        }
        if(StringUtils.isNotEmpty(end)){
            sbu.append(" AND ADDTIME <= TO_DATE('" + end + "','yyyy-MM-dd hh24:mi:ss')");
        }
        IPageList iPageList = commonService.pageQuery(sbu.toString(), currentPage, numberPage, null);

        HashMap result = new HashMap();

        result.put("data",iPageList.getResult());

        return Result.success(result);
    }

}
