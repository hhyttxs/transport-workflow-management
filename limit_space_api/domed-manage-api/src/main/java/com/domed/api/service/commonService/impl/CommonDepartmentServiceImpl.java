package com.domed.api.service.commonService.impl;


import com.domed.api.service.authService.IAuthService;
import com.domed.api.service.commonService.ICommonDepartmentService;
import com.domed.common.result.Result;
import com.domed.constant.Constant;
import com.domed.core.exception.ApplicationException;
import com.domed.core.service.ICommonService;
import com.domed.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CommonDepartmentServiceImpl implements ICommonDepartmentService {

    @Autowired
    private ICommonService commonService;

    @Autowired
    private IAuthService authService;

    /**
     *
     * @Title: departmentLevelList
     * @Description: 查询部门层级list
     * @param
     * @return:
     * @author: shanyuwei
     * Create Date:  2019/12/19
     * @throws
     */
    @Override
    public Result departmentLevelList() {
        Map retMap = new HashMap();
        List<Map> departmentList = queryDepartment();
        Map<String,List> departmentMap = this.commonService.Linkedlist2MapGroupBy(departmentList,"pid");
        //部门父节点公司
        List<Map> retDepartmentList = this.commonService.getParamList(departmentMap,"ROOT");
        if(retDepartmentList.size() > 0){
            for (Map map:retDepartmentList) {
                String departmentId = this.commonService.getParam(map,"id");
                getAlllevelDepartment(map,departmentMap,departmentId);
            }
        }
        retMap.put("departmentList",retDepartmentList);
        return Result.success(retMap);
    }

    /**
     * 查询部门，用于统计公司部门检索,通过权限进行过滤
     * @param infoMap {staff_id:员工id,function_id:方法模块id}
     * @Author wqh
     * @date 2021/06/23 15:26
     * @return [{departmentName:部门名称,id:部门id,level:部门级别,levelTop:部门上级}]
     */
    @Override
    public Result queryAuthLevelDepartment(Map infoMap){
        Map result = new HashMap();
        Map params = new HashMap();
        //获取员工id
        String staff_id = CommonUtil.getParam(infoMap,"staff_id","员工id").checkNull().checkLong().getString();
        //获取方法模块id
        String functionModel_id = CommonUtil.getParam(infoMap,"function_id","方法模块id").checkNull().checkLong().getString();
        //获取员工权限等级
        Integer roleLevel = getStaffRoleFunctionModelLevel(staff_id,functionModel_id);
        if (roleLevel != null && roleLevel == 6){
            roleLevel = 5;
        }
        //上级部门列表
        List<String> levelTops = getLevelTopsByRoleLevel(roleLevel,staff_id,1);
        //查询所有levelTop中包含上级及本机部门列表的,如果权限大于1级则查看全部
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        sb.append("	id, ");
        sb.append("	departmentName, ");
        sb.append("	level, ");
        sb.append("	levelTop  ");
        sb.append("FROM ");
        sb.append("	t_department  ");
        sb.append("WHERE ");
        sb.append("	departmentStatus = 0  ");
        sb.append("	AND deleteStatus = 0  ");
//        sb.append("	AND departmentName like '%青泥洼%'  ");
        if(levelTops != null && levelTops.size() > 0){
            sb.append("	AND ( (id IN ( :ids ))  ");
            params.put("ids", levelTops);
            sb.append("	OR ( (level > :roleLevel) AND ( ");
            params.put("roleLevel", roleLevel);
            for (int i = 0; i < levelTops.size(); i++) {
                String levelTop = levelTops.get(i);
                if(i > 0){
                    sb.append("OR ");
                }
                sb.append("( levelTop LIKE '%"+levelTop+"%' ) ");
            }
            sb.append("	))) ");
        }
        List<Map> list = commonService.nativeQuery(sb.toString(),params);
        if(list == null){
            list = new ArrayList<>();
        }
        result.put("list",list);
        result.put("roleLevel",roleLevel);
        //当前角色默认查看部门
        String roleDepartmentId = "";
        //权限为0级时看全部,没有默认部门
        if(roleLevel > 0){
            for (Map map : list) {
                if(Integer.valueOf(map.getOrDefault("level","-1").toString()).equals(roleLevel)){
                    roleDepartmentId = map.getOrDefault("id","").toString();
                }
            }
        }
        result.put("roleDepartmentId",roleDepartmentId);
        return Result.success(result);
    }

    /***
     * 查询公司
     */
    @Override
    public Result companyDepartmetList() {
        Map retMap = new HashMap();
        List<Map> departmentList = companyDepartmet();
        retMap.put("departmentList",departmentList);
        return Result.success(retMap);
    }
    /**
     *
     * @Title: getAlllevelDepartment
     * @Description: 递归整合数据
     * @param
     * @return:
     * @author: shanyuwei
     * Create Date:  2019/12/19
     * @throws
     */
    private void getAlllevelDepartment(Map map, Map<String, List> departmentMap, String departmentId) {
        String level = this.commonService.getParam(map,"level");
        List<Map> childList = this.commonService.getParamList(departmentMap,departmentId);
        if(childList.size() > 0){
            map.put("children",childList);
            if(!"".equals(level) && Integer.valueOf(level) < 4){
                map.put("expand",true);
            }
            for (Map childMap:childList) {
                String childDepartmentId = this.commonService.getParam(childMap,"id");
                getAlllevelDepartment(childMap,departmentMap,childDepartmentId);
            }
        }
    }


    public List queryDepartment() {
//        String user_id = this.commonService.getParam(infoMap,"user_id");
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append("   d.id, ");
        sql.append("   d.departmentName as title, ");
        sql.append("   da.dictionaryTitle as cityName, ");
        sql.append("   d.pid, ");
        sql.append("   d.level, ");
        sql.append("   d.levelTop, ");
        sql.append("   d.departmentLevelType, ");
        sql.append("   d.departmentType_id, ");
        sql.append("   s.staffName, ");
        sql.append("   d.workBeginTime, ");
        sql.append("   d.workEndTime, ");
        sql.append("   d.departmentRestDay ");
        sql.append(" FROM ");
        sql.append("   t_department AS d ");
        sql.append("   LEFT JOIN t_dictionary AS da ON da.deleteStatus = '0' ");
        sql.append("   AND da.id = d.city_id ");
        sql.append("   LEFT JOIN t_staff as s ON s.deleteStatus = '0' ");
        sql.append("   AND s.id = d.departmentChargeStaff_id ");
        sql.append(" WHERE ");
        sql.append("   d.deleteStatus = '0' ");
        sql.append("   AND d.deleteStatus = '0' ");
        sql.append("   AND d.departmentStatus = "+ Constant.getValueLast("DEPARTMENTSTATUSMAP","run"));
        Map params = new HashMap();
//        final String functionModel_id = "46";   //数据统计 列表查看
//        DataAuthModelDTO authModelDTO = new DataAuthModelDTO();
//        authModelDTO.setParam(params);
//        authModelDTO.setTableName("s");
//        authModelDTO.setSb(sql);
//        authModelDTO.setFunctionModel_id(functionModel_id);
////        authModelDTO.setStaff_id(user_id);
//        this.authService.addDataAuth(authModelDTO);

        //Map result = new HashMap();
        //        //获取员工的角色列表
        //        List staffRoleList = this.getStaffRoleList(staff_id);
        //        //获取员工所在部门的层级关系
        //        Map staffDepartmentMap = this.getStaffDepartmentMap(staff_id);
        List<Map> list = this.commonService.nativeQuery(sql.toString());
//        if(list == null || list.size() == 0){
//            throw new ApplicationException("-1", "暂无部门");
//        }
        return list;
    }

    public List companyDepartmet() {
//        String user_id = this.commonService.getParam(infoMap,"user_id");
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append("   d.id, ");
        sql.append("   d.departmentName as title, ");
        sql.append("   da.dictionaryTitle as cityName, ");
        sql.append("   d.pid, ");
        sql.append("   d.level, ");
        sql.append("   d.levelTop, ");
        sql.append("   d.departmentLevelType, ");
        sql.append("   d.departmentType_id, ");
        sql.append("   s.staffName, ");
        sql.append("   d.workBeginTime, ");
        sql.append("   d.workEndTime, ");
        sql.append("   d.departmentRestDay ");
        sql.append(" FROM ");
        sql.append("   t_department AS d ");
        sql.append("   LEFT JOIN t_dictionary AS da ON da.deleteStatus = '0' ");
        sql.append("   AND da.id = d.city_id ");
        sql.append("   LEFT JOIN t_staff as s ON s.deleteStatus = '0' ");
        sql.append("   AND s.id = d.departmentChargeStaff_id ");
        sql.append(" WHERE ");
        sql.append("   d.deleteStatus = '0' ");
        sql.append("   AND d.deleteStatus = '0' ");
        sql.append("   AND d.pid='ROOT' ");
        sql.append("   AND d.departmentStatus = "+ Constant.getValueLast("DEPARTMENTSTATUSMAP","run"));
        Map params = new HashMap();
//        final String functionModel_id = "46";   //数据统计 列表查看
//        DataAuthModelDTO authModelDTO = new DataAuthModelDTO();
//        authModelDTO.setParam(params);
//        authModelDTO.setTableName("s");
//        authModelDTO.setSb(sql);
//        authModelDTO.setFunctionModel_id(functionModel_id);
////        authModelDTO.setStaff_id(user_id);
//        this.authService.addDataAuth(authModelDTO);

        //Map result = new HashMap();
        //        //获取员工的角色列表
        //        List staffRoleList = this.getStaffRoleList(staff_id);
        //        //获取员工所在部门的层级关系
        //        Map staffDepartmentMap = this.getStaffDepartmentMap(staff_id);
        List<Map> list = this.commonService.nativeQuery(sql.toString());
        if(list == null || list.size() == 0){
            throw new ApplicationException("-1", "暂无部门");
        }
        return list;
    }

    /**
     * 获取员工的权限等级
     * @param staff_id 员工id
     * @param functionModel_id 方法模块id
     * @Author wqh
     * @return 权限等级,如果没有权限等级则返回-1
     */
    private Integer getStaffRoleFunctionModelLevel(String staff_id,String functionModel_id) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        sb.append("dataLevel AS level ");
        sb.append("FROM t_rolefunctionmodel ");
        sb.append("WHERE " );
        sb.append("staffRole_id IN ( ");
        sb.append("SELECT staffRole_id FROM t_staffStaffRole " );
        sb.append("WHERE deleteStatus = 0 AND staff_id = :staff_id ) " );
        sb.append("AND functionModel_id = :functionModel_id " );
        sb.append("AND deleteStatus = '0' ");
        Map params = new HashMap();
        params.put("staff_id",staff_id);
        params.put("functionModel_id",functionModel_id);

        List<Map> list = commonService.nativeQuery(sb.toString(),params);
        if(list != null && list.size() > 0){
            Map roleMap = list.get(0);
            if(roleMap != null){
                return Integer.valueOf(roleMap.getOrDefault("level",-1).toString());
            }
        }
        return -1;
    }

    /**
     * 获取员工的上级公司的id
     * @param roleLevel 角色权限级别
     * @param staff_id 员工id
     * @param authLevel 过滤权限级别
     * @Author wqh
     * @return [1,...]
     */
    private List<String> getLevelTopsByRoleLevel(Integer roleLevel,String staff_id,Integer authLevel){
        List<String> levelTops = null;
        //权限越大级别越小
        //通过过滤权限级别进行过滤,如果权限级别大于等于过滤级别就显示当前的部门,如果小于当前级别不进行过滤,因为权限已经高于当前部门
        if(roleLevel >= authLevel){
            //获取该员工所在部门包括当前部门在内的所有上级部门的id
            levelTops = getLevelTops(staff_id);
        }
        return levelTops;
    }

    /**
     * 获取员工的levelTop
     * @param staff_id 员工id
     * @Author wqh
     * @return [1,...]
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
        params.put("staff_id",staff_id);

        List<Map> list = this.commonService.nativeQuery(sb.toString(),params);

        if(list == null || list.size() == 0){
            throw new ApplicationException("员工部门不存在,员工id: "+staff_id );
        }

        Map info = list.get(0);
        String levelTop = this.commonService.getParam(info,"levelTop");

        String[] levelTop_str = levelTop.split(",");

        return Arrays.asList(levelTop_str);
    }
}
