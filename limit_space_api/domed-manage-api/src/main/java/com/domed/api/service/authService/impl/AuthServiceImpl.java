package com.domed.api.service.authService.impl;

import com.alibaba.fastjson.JSON;
import com.domed.api.service.authService.IAuthService;
import com.domed.api.util.entity.DataAuthDTO;
import com.domed.api.util.entity.DataAuthLevelDepartmentDTO;
import com.domed.api.util.entity.DataAuthModelDTO;
import com.domed.common.result.Result;
import com.domed.core.dao.IGenericDAO;
import com.domed.core.exception.ApplicationException;
import com.domed.core.service.ICommonService;
import com.domed.domain.DataAuth;
import com.domed.util.CommonUtil;
import com.domed.util.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private ICommonService commonService;

    /**
     * 获取员工在某一菜单下所有有权限的模块
     *
     * @param staff_id
     * @return
     */
    @Override
    public List getStaffFuntionModelList(String staff_id, String menuName) {
        //获取员工的角色列表
        List staffRoleList = this.getStaffRoleList(staff_id);
        //根据角色列表和菜单id 获取模块列表
        return this.getFunctionModelList(staffRoleList, menuName);
    }

    @Override
    public Map getStaffFuntionModelList(Map infoMap) {
        Map result = new HashMap();

        String staff_id = this.commonService.getParam(infoMap, "staff_id");
        String menuName = this.commonService.getParam(infoMap, "menuName");

        List modelList = this.getStaffFuntionModelList(staff_id, menuName);

        result.put("data", modelList);
        return result;
    }

    /**
     * 获取员工在某一模块下的所有数据权限
     *
     * @param staff_id
     * @param functionModel_id
     * @return
     */
    @Override
    public DataAuthDTO getStaffDataAuthMap(String staff_id, String functionModel_id) {
        DataAuthDTO authDTO = new DataAuthDTO();

        Map result = new HashMap();
        //获取员工的角色列表
        List staffRoleList = this.getStaffRoleList(staff_id);
        //获取员工所在部门的层级关系
        Map staffDepartmentMap = this.getStaffDepartmentMap(staff_id);

        //获取根据层级查询到的所有的部门id
        DataAuthLevelDepartmentDTO dataAuthLevelDepartmentDTO = this.getLevelDepartMentIdSet(staffRoleList, staffDepartmentMap, functionModel_id);

        if (dataAuthLevelDepartmentDTO.getSeeAll()) {
            authDTO.setSeeAll(true);
            return authDTO;
        }

        Set levelDepartMentIdSet = dataAuthLevelDepartmentDTO.getDepartMentSet();
        //获取额外的部门id列表
        Set extraDepartMentIdSet = this.getExtraDepartMentIdSet(staffRoleList, functionModel_id);

        Set departMentIdSet = new HashSet(levelDepartMentIdSet);
        departMentIdSet.addAll(extraDepartMentIdSet);

        //根据部门id列表获取其层级并格式化
        result = this.getDepartMentLevel(departMentIdSet);

//        if(result.size() > 0){
        //增加本人
        result.put("6", new ArrayList() {{
            add(staff_id);
        }});
//        }

        authDTO.setDataMap(result);

        return authDTO;
    }

    /**
     * 为查询sql添加数据权限
     */
    @Override
    public void addDataAuth(DataAuthModelDTO dataAuthModelDTO) {
        dataAuthModelDTO.check();

        DataAuthDTO dataAuthDTO = this.getStaffDataAuthMap(dataAuthModelDTO.getStaff_id(), dataAuthModelDTO.getFunctionModel_id());

        if (dataAuthDTO.getSeeAll()) return;

        Map authMap = dataAuthDTO.getDataMap();
//        {1=[1], 3=[3], 4=[4]}

        StringBuffer sb = dataAuthModelDTO.getSb();

        //如果没有权限 什么都查不到
        if (authMap.size() == 0) {
            if (dataAuthModelDTO.getExtraOrStr() != null) {
                sb.append(" AND ( " + dataAuthModelDTO.getExtraOrStr() + " )");
            } else {
                sb.append(" AND 1 = 0 ");
            }
        } else {
            Map<String, String> departmentLevelMap = new HashMap();
            departmentLevelMap.put("1", "companyDepartment_id");
            departmentLevelMap.put("2", "areaDepartment_id");//大区总监
            departmentLevelMap.put("3", "storeDepartment_id");//商圈经理
            departmentLevelMap.put("4", "sectionDepartment_id");//业务管家
            departmentLevelMap.put("5", "groupDepartment_id");
            departmentLevelMap.put("6", "matainStaff_id");

            Map<String, String> departmentLevelParamMap = new HashMap();
            departmentLevelParamMap.put("1", "companyDepartment_id" + UUID.randomUUID().toString().replaceAll("-", ""));
            departmentLevelParamMap.put("2", "areaDepartment_id" + UUID.randomUUID().toString().replaceAll("-", ""));//大区总监
            departmentLevelParamMap.put("3", "storeDepartment_id" + UUID.randomUUID().toString().replaceAll("-", ""));//商圈经理
            departmentLevelParamMap.put("4", "sectionDepartment_id" + UUID.randomUUID().toString().replaceAll("-", ""));//业务管家
            departmentLevelParamMap.put("5", "groupDepartment_id" + UUID.randomUUID().toString().replaceAll("-", ""));
            departmentLevelParamMap.put("6", "matainStaff_id" + UUID.randomUUID().toString().replaceAll("-", ""));

            String prefix = "";
            sb.append(" AND ( ");
            for (String type : departmentLevelMap.keySet()) {
                String field = departmentLevelMap.get(type);
                String fieldParam = departmentLevelParamMap.get(type);

                List departmentList = this.commonService.getParamList(authMap, type);
                if (departmentList.size() == 0) continue;

                sb.append(" " + prefix + " " + dataAuthModelDTO.getTableName() + "." + field + " in (:" + fieldParam + ") ");
                dataAuthModelDTO.getParam().put(fieldParam, departmentList);
                prefix = "OR";
            }

            if (dataAuthModelDTO.getExtraOrStr() != null) {
                sb.append(" or " + dataAuthModelDTO.getExtraOrStr() + " ");
            }
            sb.append(" ) ");
        }
    }

    /**
     * 为数据设置数据权限
     *
     * @param dataAuth
     * @param staff_id
     */
    @Override
    public void setDataAuth(DataAuth dataAuth, String staff_id) {
        Map departmentMap = this.getStaffDepartmentMap(staff_id);

        dataAuth.setCompanyDepartment_id(null);
        dataAuth.setAreaDepartment_id(null);
        dataAuth.setStoreDepartment_id(null);
        dataAuth.setSectionDepartment_id(null);
        dataAuth.setGroupDepartment_id(null);
        dataAuth.setMatainStaff_id(null);

        String level1 = this.commonService.getParam(departmentMap, "1");
        if (!"".equals(level1)) dataAuth.setCompanyDepartment_id(level1);

        String level2 = this.commonService.getParam(departmentMap, "2");
        if (!"".equals(level2)) dataAuth.setAreaDepartment_id(level2);

        String level3 = this.commonService.getParam(departmentMap, "3");
        if (!"".equals(level3)) dataAuth.setStoreDepartment_id(level3);

        String level4 = this.commonService.getParam(departmentMap, "4");
        if (!"".equals(level4)) dataAuth.setSectionDepartment_id(level4);

        String level5 = this.commonService.getParam(departmentMap, "5");
        if (!"".equals(level5)) dataAuth.setGroupDepartment_id(level5);

        if (dataAuth.getAddStaff_id() == null) dataAuth.setAddStaff_id(staff_id);
        dataAuth.setMatainStaff_id(staff_id);

    }

    /**
     * 获取员工的菜单列表
     */
    @Override
    public Map browseAuthList(Map infoMap) {
        Map result = new HashMap();

        String staff_id = commonService.getParam(infoMap, "staff_id");
        if ("".equals(staff_id)) {
            throw new ApplicationException("暂无菜单权限，请联系管理员。");
        }

        //用户的角色列表
        List roleList = this.getStaffRoleList(staff_id);
        //获取用户所有的有数据权限的模块
        List<String> withDataAuthMenuList = this.getWithDataAuthMenuList(roleList);
        result.put("data", withDataAuthMenuList);
        return result;
    }

    /*
        校验 工作台 是否显示 流程审批->装修审批 按钮 （权限list 里面已经存在 装修审批权限会返回 false）
     */
    private boolean checkUserApiRenovation(Map infoMap, List<String> withDataAuthMenuList) {
        boolean retType = false;
        String user_api = this.commonService.getParam(infoMap, "user_api");
        if (!"".equals(user_api)) {
            for (String menuName : withDataAuthMenuList) {
                //renovation_index 装修审批   renovationDesign 装修方案审批  renovationAccept 装修验收审批 renovationFinal 装修决算审批
                if (menuName.equals("renovation_index")) {
                    return false;
                } else {
                    if (menuName.equals("renovationDesign") || menuName.equals("renovationAccept") || menuName.equals("renovationFinal")) {
                        retType = true;
                    }
                }
            }
        }
        return retType;
    }

    /**
     * 获取顶级公司信息
     *
     * @param staff_id
     * @return
     */
    @Override
    public Map getTopDepartment(String staff_id) {
        Map department = this.getStaffDepartmentMap(staff_id);

        String id = CommonUtil.getParam(department, "1").getString();
        if ("".equals(id)) throw new ApplicationException("用户部门层级异常");

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append(" d.id, ");
        sb.append(" d.departmentName ");
        sb.append(" FROM t_department d");
        sb.append(" WHERE 1 = 1 ");
        sb.append(" AND d.deleteStatus = 0 ");
        sb.append(" AND d.id = :id ");

        Map params = new HashMap();
        params.put("id", id);

        List<Map> list = this.commonService.nativeQuery(sb.toString(), params);

        if (list == null || list.size() == 0) {
            throw new ApplicationException("部门不存在");
        }


        return list.get(0);
    }

    /**
     * @param staffRoleList
     * @return
     */
    private List getWithDataAuthMenuList(List staffRoleList) {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append("	menu.URL \"menuName\", ");
        sb.append("	menu.ID \"menu_id\" ");
        sb.append(" FROM ");
        sb.append("	TAB_MENU menu  ");
        sb.append("	INNER JOIN TAB_CDGL cdgl ON menu.CODE = cdgl.MENU_CODE ");
        sb.append("	INNER JOIN TAB_YHCD yhcd ON cdgl.YHCD_GID = yhcd.GID and yhcd.STATUS = '1' ");
        sb.append(" WHERE ");
        sb.append("	yhcd.GID in (:staffRoleList) ");
        sb.append("	GROUP BY menu.\"ID\",menu.\"URL\" ");
        Map params = new HashMap();
        params.put("staffRoleList", staffRoleList);
        List<Map> list = this.commonService.nativeQuery(sb.toString(), params);
        List menuNameList = this.commonService.listMap2PropList(list, "menuName");
        return menuNameList;

    }

    /**
     * 根据部门id列表获取其层级并格式化
     *
     * @param departMentIdSet
     * @return
     */
    private Map getDepartMentLevel(Set departMentIdSet) {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append(" d.id,d.level ");
        sb.append(" FROM t_department d ");
        sb.append(" WHERE 1 = 1 ");
        sb.append(" AND d.deleteStatus = 0 ");
        sb.append(" AND d.id in (:ids) ");

        Map params = new HashMap();
        params.put("ids", departMentIdSet);
        List<Map> list = new ArrayList<>();
        if (departMentIdSet != null && departMentIdSet.size() > 0) {
            list = this.commonService.nativeQuery(sb.toString(), params);
        }

        Map idLevelItemMap = this.commonService.Linkedlist2MapGroupBy(list, "level");

        Map idLevelMap = new HashMap();
        for (Object obj : idLevelItemMap.keySet()) {
            String key = obj.toString();
            List subList = this.commonService.getParamList(idLevelItemMap, key);
            idLevelMap.put(key, this.commonService.listMap2PropList(subList, "id"));
        }
        return idLevelMap;
    }

    private Set getExtraDepartMentIdSet(List staffRoleList, String functionModel_id) {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append(" rfmd.department_id ");
        sb.append(" FROM t_roleFunctionModelDepartment rfmd ");
        sb.append(" WHERE 1 = 1 ");
        sb.append(" AND rfmd.deleteStatus = 0 ");
        sb.append(" AND rfmd.functionModel_id = :functionModel_id ");
        sb.append(" AND rfmd.staffRole_id in (:staffRoleList) ");

        Map params = new HashMap();
        params.put("staffRoleList", staffRoleList);
        params.put("functionModel_id", functionModel_id);

        List<Map> list = this.commonService.nativeQuery(sb.toString(), params);
        List departIdList = this.commonService.listMap2PropList(list, "department_id");

        return new HashSet(departIdList);
    }

    /**
     * 获取根据层级查询到的所有的部门id
     *
     * @param staffRoleList
     * @param staffDepartmentMap
     * @param functionModel_id
     * @return
     */
    private DataAuthLevelDepartmentDTO getLevelDepartMentIdSet(List staffRoleList, Map staffDepartmentMap, String functionModel_id) {
        Set departMentIdSet = new HashSet();
        DataAuthLevelDepartmentDTO levelDepartmentDTO = new DataAuthLevelDepartmentDTO();


        //获取改用户所有的角色对某个模块的层级关系
        Map allDataLevelMap = this.getAllDataLevelMap(staffRoleList, functionModel_id);

        for (String staffRole_id : (List<String>) staffRoleList) {
            //根据角色id 获取当前的数据层级
            String dataLevel = this.getStaffDataLevel(staffRole_id, allDataLevelMap);
            if ("".equals(dataLevel)) continue;

            if ("0".equals(dataLevel)) {
                levelDepartmentDTO.setSeeAll(true);
                return levelDepartmentDTO;
            }

            //根据层级获取部门id
            String departMentId = this.getLevelDepartementId(staffDepartmentMap, dataLevel);
            if ("".equals(departMentId)) continue;
            departMentIdSet.add(departMentId);
        }

        levelDepartmentDTO.setDepartMentSet(departMentIdSet);
        return levelDepartmentDTO;
    }

    /**
     * 获取改用户所有的角色对某个模块的层级关系
     *
     * @param staffRoleList
     * @param functionModel_id
     * @return
     */
    private Map getAllDataLevelMap(List staffRoleList, String functionModel_id) {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append(" rfm.dataLevel,rfm.staffRole_id ");
        sb.append(" FROM t_roleFunctionModel rfm ");
        sb.append(" WHERE 1 = 1 ");
        sb.append(" AND rfm.deleteStatus = 0 ");
        sb.append(" AND rfm.functionModel_id = :functionModel_id ");
        sb.append(" AND rfm.staffRole_id in (:staffRoleList) ");

        Map params = new HashMap();
        params.put("functionModel_id", functionModel_id);
        params.put("staffRoleList", staffRoleList);

        List<Map> list = this.commonService.nativeQuery(sb.toString(), params);

        Map result = new HashMap();
        for (Map tempMap : list) {
            String staffRole_id = this.commonService.getParam(tempMap, "staffRole_id");
            String dataLevel = this.commonService.getParam(tempMap, "dataLevel");
            result.put(staffRole_id, dataLevel);
        }


        return result;
    }

    /**
     * 根据层级获取部门id
     *
     * @param staffDepartmentMap
     * @param dataLevel
     * @return
     */
    private String getLevelDepartementId(Map staffDepartmentMap, String dataLevel) {
        if ("6".equals(dataLevel)) return "";
        String departmentId = "";
        do {
            departmentId = this.commonService.getParam(staffDepartmentMap, dataLevel);
            dataLevel = (Integer.valueOf(dataLevel) - 1) + "";
        } while ("".equals(departmentId) && Integer.valueOf(dataLevel) > 0);

        return departmentId;
    }

    /**
     * 获取员工所在部门的层级关系
     *
     * @param staff_id
     * @return
     */
    private Map getStaffDepartmentMap(String staff_id) {
        // 获取员工的levelTOp
        List<String> levelTops = this.getLevelTops(staff_id);
        if (levelTops.size() > 5) levelTops = levelTops.subList(0, 5);

        //根据levelTop格式化部门信息
        Map staffDepartmentMap = this.formatStaffDepartmentMap(levelTops);

        return staffDepartmentMap;


    }

    public List<Map> getStaffDepartmentList(String staff_id) {
        // 获取员工的levelTOp
        List<String> levelTops = this.getLevelTops(staff_id);
        if (levelTops.size() > 5) levelTops = levelTops.subList(0, 5);
        return this.formatStaffDepartmentList(levelTops);
    }

    public List<Map> getStaffDepartmentListBylevelTops(String levelTops) {
        if (levelTops == null || levelTops.length() == 0) {
            return new ArrayList<Map>();
        }
        String[] levelTop_str = levelTops.split(",");
        List<String> levelTopList = Arrays.asList(levelTop_str);
        if (levelTopList.size() > 5) levelTopList = levelTopList.subList(0, 5);
        return this.formatStaffDepartmentList(levelTopList);
    }

    /**
     * 根据levelTop格式化部门信息
     *
     * @param levelTops
     * @return
     */
    @Override
    public Map formatStaffDepartmentMap(List<String> levelTops) {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append(" d.id,d.level ");
        sb.append(" FROM t_department d  ");
        sb.append(" WHERE 1 = 1 ");
        sb.append(" AND d.deleteStatus = 0 ");
        sb.append(" AND d.id in (:ids) ");

        Map params = new HashMap();
        params.put("ids", levelTops);

        List<Map> list = this.commonService.nativeQuery(sb.toString(), params);

        if (list == null || list.size() == 0) {
            throw new ApplicationException("部门层级不存在");
        }

        Map result = new HashMap();

        for (Map tempMap : list) {
            String level = this.commonService.getParam(tempMap, "level");
            String id = this.commonService.getParam(tempMap, "id");

            result.put(level, id);
        }

        return result;
    }

    private List<Map> formatStaffDepartmentList(List<String> levelTops) {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append(" d.id,d.level,departmentName ");
        sb.append(" FROM t_department d  ");
        sb.append(" WHERE 1 = 1 ");
        sb.append(" AND d.deleteStatus = 0 ");
        sb.append(" AND d.id in (:ids) ");
        sb.append(" order by d.level asc ");
        Map params = new HashMap();
        params.put("ids", levelTops);

        List<Map> list = this.commonService.nativeQuery(sb.toString(), params);

        if (list == null || list.size() == 0) {
            throw new ApplicationException("部门层级不存在");
        }
        return list;
    }

    /**
     * 获取员工的levelTOp
     *
     * @param staff_id
     * @return
     */
    private List<String> getLevelTops(String staff_id) {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append(" d.levelTop ");
        sb.append(" FROM t_staff s ");
        sb.append(" LEFT JOIN t_department d ON d.id = s.department_id ");
        sb.append(" WHERE 1 = 1 ");
        sb.append(" AND s.deleteStatus = 0 ");
        sb.append(" AND s.id = :staff_id ");

        Map params = new HashMap();
        params.put("staff_id", staff_id);

        List<Map> list = this.commonService.nativeQuery(sb.toString(), params);

        if (list == null || list.size() == 0) {
            throw new ApplicationException("员工部门不存在,员工id: " + staff_id);
        }

        Map info = list.get(0);
        String levelTop = this.commonService.getParam(info, "levelTop");

        String[] levelTop_str = levelTop.split(",");

        return Arrays.asList(levelTop_str);
    }

    /**
     * 根据角色id 获取当前的数据层级
     *
     * @param staffRole_id
     * @param allDataLevelMap
     * @return
     */
    private String getStaffDataLevel(String staffRole_id, Map allDataLevelMap) {
        return this.commonService.getParam(allDataLevelMap, staffRole_id);
    }

    /**
     * 根据角色列表和菜单id 获取模块列表
     *
     * @param staffRoleList
     * @return
     */
    private List getFunctionModelList(List staffRoleList, String menuName) {
        if (staffRoleList.size() == 0) staffRoleList.add("-1");

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append(" fm.modelName ");
        sb.append(" FROM t_roleFunctionModel rfm ");
        sb.append(" LEFT JOIN t_functionModel fm ON fm.id = rfm.functionModel_id ");
        sb.append(" LEFT JOIN t_menu m ON m.id = fm.menu_id ");
        sb.append(" WHERE 1 = 1 ");
        sb.append(" AND rfm.deleteStatus = 0 ");
        sb.append(" AND m.menuName = :menuName ");
        sb.append(" AND rfm.staffRole_id in (:staffRoleList) ");

        Map params = new HashMap();
        params.put("staffRoleList", staffRoleList);
        params.put("menuName", menuName);

        List<Map> list = this.commonService.nativeQuery(sb.toString(), params);
        Set modelNameSet = new HashSet(this.commonService.listMap2PropList(list, "modelName"));

        return new ArrayList(modelNameSet);
    }

    /**
     * 获取员工的角色列表
     *
     * @param staff_id
     * @return
     */
    private List getStaffRoleList(String staff_id) {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append(" ssr.GID \"staffRole_id\" ");
        sb.append(" FROM TAB_YHCD ssr ");
        sb.append(" WHERE 1 = 1 ");
        sb.append(" AND ssr.STATUS = 1 ");
        sb.append(" AND ssr.GID = :staff_id ");

        Map params = new HashMap();
        params.put("staff_id", staff_id);

        List<Map> list = this.commonService.nativeQuery(sb.toString(), params);

        return this.commonService.listMap2PropList(list, "staffRole_id");
    }

    /**
     * @Description: 查询模块下用户的区店部组权限
     * @author syl
     * @date 2020/9/11 13:46
     */
    public Map getStaffFuntionDepartmentList(Map infoMap) {

        Map result = new HashMap();

        String staff_id = this.commonService.getParam(infoMap, "staff_id");
        String functionModel_id = this.commonService.getParam(infoMap, "functionModel_id");

        String menuName = this.commonService.getParam(infoMap, "menuName");

        List modelList = this.getStaffFuntionModelList(staff_id, menuName);

        result.put("data", modelList);

        DataAuthDTO dataAuthDTO = this.getStaffDataAuthMap(staff_id, functionModel_id);
        if (dataAuthDTO.getSeeAll()) {
            result.put("all", "all");
            return result;
        }
        Map dataMap = dataAuthDTO.getDataMap();

        Map<String, String> departmentLevelMap = new HashMap();
        departmentLevelMap.put("1", "companyDepartment_id");
        departmentLevelMap.put("2", "areaDepartment_id");//大区总监
        departmentLevelMap.put("3", "storeDepartment_id");//商圈经理
        departmentLevelMap.put("4", "sectionDepartment_id");//业务管家
        departmentLevelMap.put("5", "groupDepartment_id");
        departmentLevelMap.put("6", "matainStaff_id");

        for (String type : departmentLevelMap.keySet()) {
            String field = departmentLevelMap.get(type);

            List departmentList = this.commonService.getParamList(dataMap, type);
            if (departmentList.size() == 0) continue;

            result.put(field, departmentList);
        }
        return result;
    }
}
