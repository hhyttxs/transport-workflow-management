package com.domed.constant;

import java.text.SimpleDateFormat;
import java.util.*;

public class Constant {
    public static final String MOBILE_CODE_PREFIX = "MOBILE_CODE_PREFIX";

    public static Map<String,Map<Object,Object>> C = new HashMap<>();

    public static List vinCodeList = new ArrayList();

    private static final String VALUES="values";
    // 用户角色
    public static class ROLEMAP{
        public static final String ADMIN = "0";
        public static final String SALEMANAGER = "1";
        public static final String SALER = "2";
        public static final String CUSTOMERSERVICE = "3";
    }

    /**
     * Get the constant label by value, macro.
     *
     * @param name constant name
     * @param value constant value
     */
    public static String getLabel(String name, String value){
        Map<Object,Object> values1 = getValue(name, value);
        String label = null;
        if(values1 != null){
            label = (String) values1.get("dictionaryTitle");
        }

        if(label == null){
            return "";
        }
        else{
            return label;
        }
    }

    public static String getName(String name, String value){
        Map<Object,Object> value1 = getValue(name, value);
        String label = null;
        if(value1 != null){
            label = (String) value1.get("dictionaryName");
        }

        if(label == null){
            return "";
        }
        else{
            return label;
        }
    }

    public static String getValueLast(String name, String value){
        Map<Object,Object> valueMap = getValue(name, value);
        String value1 = null;
        if(valueMap != null){
            value1 = (String) valueMap.get("dictionaryValue");
        }

        if(value1 == null){
            return "";
        }
        else{
            return value1;
        }
    }

    /**
     * Get the constant value map.
     *
     * @param name constant name
     * @param value constant value
     */
    public static Map<Object,Object> getValue(String name, String value){
        Map<Object,Object> constant =  C.get(name);

        if(constant != null){
            return (Map) constant.get(value);
        }

        return constant;
    }

    /**
     * Return the constant value list by given name.
     *
     * @param name constant name
     * @return list of the constant values
     */
    public static List getValues(String name){
        Map<Object,Object>  constant =  C.get(name);

        if(constant != null){
            return (List) constant.get(VALUES);
        }

        return null;
    }

    private static Map<Object,Object> addValue(Map<Object,Object> map, String value, String macro, String label){
        Map<Object,Object> constant = new HashMap<>();
        constant.put("dictionaryValue", value);
        constant.put("dictionaryName", macro);
        constant.put("dictionaryTitle", label);
        map.put(value, constant);
        map.put(macro, constant);
        map.put(label, constant);

        return constant;
    }
    static String aaa = "1";

    static{
        ArrayList<Object> list = null;
        //部门表 部门层级类型(0 公司 1 部门)
        Map DEPARTENTLEVELTYPEMAP = new HashMap();
        DEPARTENTLEVELTYPEMAP.put("description", "部门层级类型");
        DEPARTENTLEVELTYPEMAP.put("name", "DEPARTENTLEVELTYPEMAP");

        list = new ArrayList();
        list.add(addValue(DEPARTENTLEVELTYPEMAP, "0", "company", "公司"));
        list.add(addValue(DEPARTENTLEVELTYPEMAP, "1", "departent", "部门"));

        DEPARTENTLEVELTYPEMAP.put("values", list);
        C.put("DEPARTENTLEVELTYPEMAP", DEPARTENTLEVELTYPEMAP);

        //部门表 部门状态(0 启用 1 停用)
        Map DEPARTMENTSTATUSMAP = new HashMap();
        DEPARTMENTSTATUSMAP.put("description", "部门状态");
        DEPARTMENTSTATUSMAP.put("name", "DEPARTMENTSTATUSMAP");

        list = new ArrayList();
        list.add(addValue(DEPARTMENTSTATUSMAP, "0", "run", "启用"));
        list.add(addValue(DEPARTMENTSTATUSMAP, "1", "stop", "停用"));

        DEPARTMENTSTATUSMAP.put("values", list);
        C.put("DEPARTMENTSTATUSMAP", DEPARTMENTSTATUSMAP);

        //员工表 员工状态(0 试岗 1 在职 2 离职)
        Map STAFFSTATUSMAP = new HashMap();
        STAFFSTATUSMAP.put("description", "员工状态");
        STAFFSTATUSMAP.put("name", "STAFFSTATUSMAP");

        list = new ArrayList();
        list.add(addValue(STAFFSTATUSMAP, "0", "try", "试岗"));
        list.add(addValue(STAFFSTATUSMAP, "1", "be", "在职"));
        list.add(addValue(STAFFSTATUSMAP, "2", "leave", "离职"));

        STAFFSTATUSMAP.put("values", list);
        C.put("STAFFSTATUSMAP", STAFFSTATUSMAP);

        //员工表 性别(0 女 1 男)
        Map STAFFGENDERMAP = new HashMap();
        STAFFGENDERMAP.put("description", "性别");
        STAFFGENDERMAP.put("name", "STAFFGENDERMAP");

        list = new ArrayList();
        list.add(addValue(STAFFGENDERMAP, "1", "man", "男"));
        list.add(addValue(STAFFGENDERMAP, "0", "woman", "女"));

        STAFFGENDERMAP.put("values", list);
        C.put("STAFFGENDERMAP", STAFFGENDERMAP);

        //用户表 是否员工(0 否 1 是)
        Map GLOBALUSERISSTAFFMAP = new HashMap();
        GLOBALUSERISSTAFFMAP.put("description", "是否员工");
        GLOBALUSERISSTAFFMAP.put("name", "GLOBALUSERISSTAFFMAP");

        list = new ArrayList();
        list.add(addValue(GLOBALUSERISSTAFFMAP, "0", "globaluser", "普通用户"));
        list.add(addValue(GLOBALUSERISSTAFFMAP, "1", "staff", "员工"));

        GLOBALUSERISSTAFFMAP.put("values", list);
        C.put("GLOBALUSERISSTAFFMAP", GLOBALUSERISSTAFFMAP);

        //角色功能模块表  是否需要数据权限(0 否 1 是)
        Map ISNEEDDATAAUTHMAP = new HashMap();
        ISNEEDDATAAUTHMAP.put("description", "是否需要数据权限");
        ISNEEDDATAAUTHMAP.put("name", "ISNEEDDATAAUTHMAP");

        list = new ArrayList();
        list.add(addValue(ISNEEDDATAAUTHMAP, "0", "no", "否"));
        list.add(addValue(ISNEEDDATAAUTHMAP, "1", "yes", "是"));

        ISNEEDDATAAUTHMAP.put("values", list);
        C.put("ISNEEDDATAAUTHMAP", ISNEEDDATAAUTHMAP);

        //文件管理表 审批状态(0：未提交，1：已提交，2：已审批)
        Map APPROVALSTATUSMAP = new HashMap();
        APPROVALSTATUSMAP.put("description", "审批状态");
        APPROVALSTATUSMAP.put("name", "APPROVALSTATUSMAP");

        list = new ArrayList();
        list.add(addValue(APPROVALSTATUSMAP, "0", "input", "已录入"));
        list.add(addValue(APPROVALSTATUSMAP, "1", "submitted", "已提交"));
        list.add(addValue(APPROVALSTATUSMAP, "2", "approve", "已批阅"));

        APPROVALSTATUSMAP.put("values", list);
        C.put("APPROVALSTATUSMAP", APPROVALSTATUSMAP);

        // 文件类型 文件 1  图片2  边鹏瑞
        Map FILETYPEMAP = new HashMap();
        FILETYPEMAP.put("description", "文件类型");
        FILETYPEMAP.put("name", "FILETYPEMAP");

        list = new ArrayList();
        list.add(addValue(FILETYPEMAP, "1", "file", "文件"));
        list.add(addValue(FILETYPEMAP, "2", "img", "图片"));
        list.add(addValue(FILETYPEMAP, "3", "pdf", "pdf"));
        FILETYPEMAP.put("values", list);
        C.put("FILETYPEMAP", FILETYPEMAP);

        //是否，0：否，1：是
        Map ISOKMAP = new HashMap();
        ISOKMAP.put("description", "是否");
        ISOKMAP.put("name", "ISOKMAP");

        list = new ArrayList();
        list.add(addValue(ISOKMAP, "0", "no", "否"));
        list.add(addValue(ISOKMAP, "1", "yes", "是"));

        ISOKMAP.put("values", list);
        C.put("ISOKMAP", ISOKMAP);

        //文件上传类型 1.数据上传 2.公共文件
        Map FILEUPLOADTYPEMAP = new HashMap();
        FILEUPLOADTYPEMAP.put("description", "文件上传类型");
        FILEUPLOADTYPEMAP.put("name", "FILEUPLOADTYPEMAP");

        list = new ArrayList();
        list.add(addValue(FILEUPLOADTYPEMAP, "1", "dataUpload", "数据上传"));
        list.add(addValue(FILEUPLOADTYPEMAP, "2", "publicFile", "公共文件"));

        FILEUPLOADTYPEMAP.put("values", list);
        C.put("FILEUPLOADTYPEMAP", FILEUPLOADTYPEMAP);

        Map SERVICELIST = new HashMap();

        SERVICELIST.put("BUSSTATIONINFO","transport.bus.addstationInfo");//公交电子站牌信息
        SERVICELIST.put("BUSINFO","transport.bus.addInfo");//公交车辆信息
        SERVICELIST.put("BUSCOMPANYINFO","transport.bus.addcompanyInfo");//公交企业信息
        SERVICELIST.put("BUSPOSITIONINFO","transport.bus.addpositionInfo");//公交车辆位置信息
        SERVICELIST.put("BUSDEPARTINFO","transport.bus.adddepartInfo");//公交车辆发班情况
        SERVICELIST.put("PORTDANGERCOMPANYINFO","transport.port.addDangerCompanyInfo");//港口危化品企业信息
        SERVICELIST.put("PORTDANGERCARINFO","transport.port.addDangerCarInfo");//港口危化品车辆信息
        SERVICELIST.put("PORTDANGERPERSONINFO","transport.port.addDangerPersonInfo");//港口危化品从业人员信息
        SERVICELIST.put("PORTDANGERPOSITIONINFO","transport.port.addDangerPositionInfo");//港口危化品位置信息
        SERVICELIST.put("PORTDANGERSAFEGUARDINFO","transport.port.addDangerSafeguardInfo");//港口危化品维护信息
        SERVICELIST.put("TAXITERMINALINFO","transport.taxi.addTerminalInfo");//出租车辆车载终端信息
        SERVICELIST.put("TAXIMOVECHECKINFO","transport.taxi.addMoveCheckInfo");//出租车辆移动稽查信息
        SERVICELIST.put("TAXISERVICEAPPRAISEINFO","transport.taxi.addServiceAppraiseInfo");//出租车辆服务评价信息
        SERVICELIST.put("TAXIONLINEMANAGEINFO","transport.taxi.addOnlineManageInfo");//出租企业在线管理信息
        SERVICELIST.put("TAXITRACKINFO","transport.taxi.addTrackInfo");//出租车辆轨迹信息
        SERVICELIST.put("COMPLAINTSUPERVISEINFO","transport.complaint.addSuperviseInfo");//投诉监督信息
        SERVICELIST.put("ROADBUILDREPORTINFO","transport.roadBuild.addReportInfo");//公路项目建设信息
        SERVICELIST.put("ROADBUILDAPPROVEINFO","transport.roadBuild.addApproveInfo");//公路项目建设审批信息
        SERVICELIST.put("ROADBUILDSCHEDULEINFO","transport.roadBuild.addScheduleInfo");//公路项目建设进度信息
        SERVICELIST.put("ROADBUILDSCHEDULEQUALITYINFO","transport.roadBuild.addScheduleQualityInfo");//公路项目建设进度质量
        SERVICELIST.put("ENFLAWGATHERINFO","transport.enfLaw.addGatherInfo");//执法案件采集信息
        SERVICELIST.put("ENFLAWPUNISHINFO","transport.enfLaw.addPunishInfo");//执法案件处罚信息
        SERVICELIST.put("ENFLAWREPORTINFO","transport.enfLaw.addReportInfo");//执法案件结案信息上报

        C.put("SERVICELIST",SERVICELIST);
        Map PRINTSTEP = new HashMap();
        PRINTSTEP.put("KH","1");//开户
        PRINTSTEP.put("BG","2");//变更
        PRINTSTEP.put("HZ","3");//换证
        PRINTSTEP.put("BZ","4");//补证
        C.put("PRINTSTEP",PRINTSTEP);
        Map COMMONAREA = new HashMap();
        Map DANGERAREA = new HashMap();
        Map RADIATEAREA = new HashMap();
        List<String> ptList = Arrays.asList("1","7","8","9","3");
        List<String> wxList = Arrays.asList("11","12","13","14","15","16","18","19","20","21","23","24","25","27","28","30","31","33","34","378","377","376");
        List<String> fsList = Arrays.asList("516","517","518","b2.1","b2.2","b2.3","b2.4","b2.5","d4","e5");
        COMMONAREA.put("values",ptList);
        DANGERAREA.put("values",wxList);
        RADIATEAREA.put("values",fsList);

        C.put("COMMONAREA",COMMONAREA);
        C.put("DANGERAREA",DANGERAREA);
        C.put("RADIATEAREA",RADIATEAREA);

        Map DANGERSAFE = new HashMap();
        Map RADIATESAFE = new HashMap();
        List<String> dangerSafe = Arrays.asList("拖地链","防火帽","胶皮垫","标志牌","标志牌");
        List<String> radiateSafe = Arrays.asList("辐射防护用品","检测仪器","车辆、仓储安全设备");
        DANGERSAFE.put("values",dangerSafe);
        RADIATESAFE.put("values",radiateSafe);
        C.put("DANGERSAFE",DANGERSAFE);
        C.put("RADIATESAFE",RADIATESAFE);
    }
}
