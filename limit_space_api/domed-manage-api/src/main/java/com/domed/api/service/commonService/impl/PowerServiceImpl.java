package com.domed.api.service.commonService.impl;

import com.domed.api.service.commonService.IPowerService;
import com.domed.core.exception.ApplicationException;
import com.domed.core.service.ICommonService;
import com.domed.service.IDelegator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.domed.util.UtilMisc.*;


@Service
@Transactional
public class PowerServiceImpl implements IPowerService{

    @Autowired
    private IDelegator delegator;

    @Autowired
    private ICommonService commonService;


    /**
     *
     * @Title: queyUserMenuPower
     * @Description: 查询用户
     * @param staff_id 员工ID
     * @param modelName 功能名称
     * @return:
     * @author: shanyuwei
     * Create Date:  2019/12/16
     * @throws
     */
    @Override
    public Map queyUserModelPower(String staff_id, String modelName) {
        Map retMap = new HashMap();
        List<Map> retList = new ArrayList();
        String type = "1";  //1：局部权限   2：全部权限
        //员工角色List
        List<Map> staffRoleList = queyStaffRole(staff_id);
        if(staffRoleList != null && staffRoleList.size() > 0){
            //根据 员工角色 及 模块名称 查询门店层级及ID
            List<Map> dataLevelList = queryRoleFunctionModelLevel(staffRoleList,modelName);
            //查询用户部门ID
            Map userDepartmentMap = queyUserDepartment(staff_id);
            String userLevel = commonService.getParam(userDepartmentMap,"level");
            int i_userLevel = Integer.parseInt(userLevel);
            String userLevelTop = commonService.getParam(userDepartmentMap,"levelTop");
            String userDepartment_id = commonService.getParam(userDepartmentMap,"id");

            if(dataLevelList != null && dataLevelList.size() > 0){
                for (Map dataLevelMap:dataLevelList) {
                    String isNeedDataAuth = commonService.getParam(dataLevelMap,"isNeedDataAuth");  //是否需要数据权限(0 否 1 是)
                    if("1".equals(isNeedDataAuth)){
                        //0 全部  1:公司  2:区  3:店  4:部  5:组
                        String dataLevel = commonService.getParam(dataLevelMap,"dataLevel");
                        if("0".equals(dataLevel)){
                            type = "2";
                        }
                        int i_dataLevel = Integer.parseInt(dataLevel);
                        //配置的权限小于用户权限
                        if(i_userLevel >= i_dataLevel){
                            Map paramsMap = new HashMap();
                            String[] userLevelTopSpile = userLevelTop.split(",");
                            if(userLevelTopSpile.length != i_userLevel){
                                throw new ApplicationException("-1", "部门层级配置异常");
                            }
                            paramsMap.put("level",dataLevel);
                            paramsMap.put("department_id",userLevelTopSpile[i_dataLevel]);
                            retList.add(paramsMap);
                        }
                    }
                }
            }

            //查询 角色功能模块部门表
            List<Map> roleFunctionModelDepartmentList = queryRoleFunctionModelDepartment(staffRoleList,modelName);
            retList.addAll(roleFunctionModelDepartmentList);
        }
        retMap.put("type",type);
        retMap.put("powerList",retList);
        return retMap;
    }

    /*
     *  角色功能模块部门表
     *  根据 员工角色 及 模块名称 查询门店层级及ID
     */
    private List<Map> queryRoleFunctionModelDepartment(List<Map> staffRoleList, String modelName) {
        List<Map> dataLevelList = delegator.findByView(
                toMap("t_roleFunctionModelDepartment rfmd",
                        toList(
                                toMap("t_functionModel fm ",
                                        toList(
                                                equalsExp("fm.deleteStatus", 0),
                                                equalsExp("fm.id", "rfmd.functionModel_id")
                                        )
                                )
                        )
                ),
                toList("rfmd.level","rfmd.department_id"),//查询列
                toList(
                        equalsExp("rfmd.deleteStatus", 0),
                        equalsExp("fm.modelName", "'"+modelName+"'"),
                        inExp("rfmd.staffRole_id", staffRoleList,"staffRole_id")
                ),
                toList("rfmd.id asc")//排序
        );
        return dataLevelList;
    }

    /*
     *  角色功能模块表
     *  根据 员工角色 及 模块名称 查询门店层级及ID
     */
    public List<Map> queryRoleFunctionModelLevel(List<Map> staffRoleList, String modelName) {
        List<Map> dataLevelList = delegator.findByView(
                toMap("t_roleFunctionModel rfm",
                        toList(
                                toMap("t_functionModel fm ",
                                        toList(
                                                equalsExp("fm.deleteStatus", 0),
                                                equalsExp("fm.id", "rfm.functionModel_id")
                                        )
                                )
                        )
                ),
                toList("rfm.dataLevel","rfm.isNeedDataAuth"),//查询列
                toList(
                        equalsExp("rfm.deleteStatus", 0),
                        equalsExp("fm.modelName", "'"+modelName+"'"),
                        inExp("rfm.staffRole_id", staffRoleList,"staffRole_id")
                ),
                toList("rfm.id asc")//排序
        );
        return dataLevelList;
    }

    /**
     *
     * @Title: queyStaffRole
     * @Description: 查询员工角色List
     * @param staff_id 员工ID
     * @return:
     * @author: shanyuwei
     * Create Date:  2019/12/16
     * @throws
     */
    @Override
    public List<Map> queyStaffRole(String staff_id) {
        List<Map> staffRoleList = delegator.findByAnd("t_staffStaffRole",
                toList("staffRole_id"),
                toMap("deleteStatus", "0", "staff_id", staff_id));
        return staffRoleList;
    }

    /**
     *
     * @Title: queyUserDepartment
     * @Description: 查询用户部门所在层级
     * @param staff_id 员工ID
     * @return:
     * @author: shanyuwei
     * Create Date:  2019/12/16
     * @throws
     */
    @Override
    public Map queyUserDepartment(String staff_id) {
        List<Map> departmentList = delegator.findByView(
                toMap("t_department dm",
                        toList(
                                toMap("t_staff s ", toList(
                                        equalsExp("s.deleteStatus", 0),
                                        equalsExp("s.department_id", "dm.id")))
                        )
                ),
                toList("dm.level","dm.levelTop","dm.id","substring_index(dm.levelTop,',',1) as cId "),//查询列
                toList(
                        equalsExp("dm.deleteStatus", 0),
                        equalsExp("s.id", staff_id)
                ),
                toList("dm.id asc")//排序
        );

        if(departmentList == null || departmentList.size() == 0){
            throw new ApplicationException("-1", "用户部门ID异常");
        }
        Map departmentMap = (Map) departmentList.get(0);
        return departmentMap;
    }

}
