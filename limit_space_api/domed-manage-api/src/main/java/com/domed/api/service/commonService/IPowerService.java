package com.domed.api.service.commonService;


import java.util.List;
import java.util.Map;

public interface IPowerService {

    /**
     *
     * @Title: queyUserMenuPower
     * @Description: 查询用户权限
     * @param staff_id 员工ID
     * @param modelName 功能名称
     * @return:
     * @author: shanyuwei
     * Create Date:  2019/12/16
     * @throws
     */
    public Map queyUserModelPower(String staff_id,String modelName);


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
    public List<Map> queyStaffRole(String staff_id);

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
    public Map queyUserDepartment(String staff_id);

}
