package com.domed.api.service.authService.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.StringUtils;
import com.domed.api.service.authService.IAuthUserService;
import com.domed.api.util.IpUtil.IpUntils;
import com.domed.common.result.Result;
import com.domed.core.dao.IGenericDAO;
import com.domed.core.exception.ApplicationException;
import com.domed.core.service.ICommonService;
import com.domed.domain.TabSystemLoginLog;
import com.domed.util.CommonUtil;
import com.domed.util.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("AuthUserServiceImpl")
@Transactional
public class AuthUserServiceImpl implements IAuthUserService {

    @Autowired
    private ICommonService commonService;

    @Autowired
    private AuthUserFactoryServiceImpl factoryService;

    @Resource(name = "tabSystemLoginLogDAO")
    private IGenericDAO<TabSystemLoginLog> tabSystemLoginLogDAO;

    @Override
    public Result<UserDTO> getUserInfo(String userName) {

        Map userNameMap = null;
        try {
            userNameMap = JSON.parseObject(userName);
        } catch (Exception e) {
            throw new ApplicationException("用户名格式异常");
        }

        String realUserName = CommonUtil.getParam(userNameMap, "userName", "用户名").getString();
        String type = CommonUtil.getParam(userNameMap, "type", "登录类型").checkNull().getString();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if ("back".equals(type)) {
            String outIPV4 = IpUntils.getOutIPV4(request);
            TabSystemLoginLog tabSystemLoginLog = new TabSystemLoginLog();
            tabSystemLoginLog.setLoginip(outIPV4);
            tabSystemLoginLog.setLogincode(realUserName);
            tabSystemLoginLog.setLogintime(new Date());
            tabSystemLoginLogDAO.save(tabSystemLoginLog);
        }

        UserDTO userDTO = this.factoryService.getService(type).getUserInfoDetailInfo(realUserName);
        log.info(userDTO.toString());
        userDTO.setLoginJson(userName);
        return Result.success(userDTO);
    }

    /**
     * 获取用户基本信息
     * 根据用户名或者用户id
     *
     * @param infoMap
     * @return
     */
    protected UserDTO getUserInfo(Map infoMap) {
        String userName = this.commonService.getParam(infoMap, "userName");
//        String unionid = this.commonService.getParam(infoMap,"unionid");


        StringBuffer sb = new StringBuffer();
        Map params = new HashMap();
        sb.append(" SELECT * FROM TAB_YHWH gu WHERE 1 = 1 ");
        sb.append(" AND gu.\"STATUS\" = 1 ");
        if (!"".equals(userName)) {
            sb.append(" AND gu.CODE = :userName ");
            params.put("userName", userName);
        }
        List<Map> list = this.commonService.nativeQuery(sb.toString(), params);
        if (list == null || list.size() == 0) {
            return null;
        }

        Map item = list.get(0);
        String gid = this.commonService.getParam(item, "GID");
        String password = this.commonService.getParam(item, "PASSWORD");
        String code = this.commonService.getParam(item, "CODE");
        String status = this.commonService.getParam(item, "STATUS");
        String xzjgCode = this.commonService.getParam(item, "XZJG_CODE");
        String fpqx = this.commonService.getParam(item, "FPQX");
        String zgjg = this.commonService.getParam(item, "ZGJG");
        String name = this.commonService.getParam(item, "NAME");
        String yhcdGid = this.commonService.getParam(item, "YHCD_GID");
        String yzryxh = this.commonService.getParam(item, "YZRYXH");
        String jgxh = this.commonService.getParam(item, "JGXH");
        String yhlx = this.commonService.getParam(item, "YHLX");
        String xtbs = this.commonService.getParam(item, "XTBS");
        String xzqhCode = this.commonService.getParam(item, "XZQH_CODE");
        String xzjgName = this.commonService.getParam(item, "xzjgName");
        String userType = this.commonService.getParam(item, "userType");
        String phone = this.commonService.getParam(item, "phone");
        String xzjgPhone = this.commonService.getParam(item, "xzjgPhone");
        String qy = this.commonService.getParam(item, "QY");

        UserDTO userDTO = new UserDTO();
//        userDTO.setUser_id(user_id);
        userDTO.setUserType(userType);
        userDTO.setPassword(password);
        userDTO.setGid(gid);
        userDTO.setCode(code);
        userDTO.setXzjgName(xzjgName);
        if (StringUtils.isNotEmpty(status)) userDTO.setStatus(new Integer(status));
        userDTO.setXzjgCode(xzjgCode);
        if (StringUtils.isNotEmpty(fpqx)) userDTO.setFpqx(new Integer(fpqx));
        userDTO.setZgjg(zgjg);
        userDTO.setName(name);
        userDTO.setYhcdGid(yhcdGid);
        userDTO.setYzryxh(yzryxh);
        userDTO.setJgxh(jgxh);
        userDTO.setYhlx(yhlx);
        userDTO.setXtbs(xtbs);
        userDTO.setXzqhCode(xzqhCode);
        userDTO.setJbrPhone(phone);
        userDTO.setXzjgPhone(xzjgPhone);
        userDTO.setQy(qy);
//        userDTO.setOpen_id(open_id);
//        userDTO.setUnion_id(union_id);
//        if("1".equals(isStaff)) {
//            userDTO.setIsStaff(true);
//        }else {
//            userDTO.setIsStaff(false);
//        }
        return userDTO;

    }

    /**
     * 获取用户基本信息
     * 根据用户名或者用户id
     *
     * @param infoMap
     * @return
     */
    protected UserDTO getUserInfoFront(Map infoMap) {
        String userName = this.commonService.getParam(infoMap, "userName");
        StringBuffer sb = new StringBuffer();
        Map params = new HashMap();
        sb.append(" SELECT * FROM TAB_YHWH WHERE STATUS = '1' ");
        sb.append(" AND ( ");
        sb.append(" 1 = 2 ");
        if (!"".equals(userName)) {
            sb.append(" OR CODE = :userName ");
            params.put("userName", userName);
        }
        sb.append(" ) ");
        List<Map> list = this.commonService.nativeQuery(sb.toString(), params);
        if (list == null || list.size() == 0) {
            return null;
        }
        Map item = list.get(0);
        String gid = this.commonService.getParam(item, "GID");
        String name = this.commonService.getParam(item, "USER_NAME");
        String account = this.commonService.getParam(item, "CODE");
        String password = this.commonService.getParam(item, "PASSWORD");
        String cdid = this.commonService.getParam(item, "YHCD_GID");
        String glbm = this.commonService.getParam(item, "GLBM");
        UserDTO userDTO = new UserDTO();
        userDTO.setGid(gid);
        userDTO.setCode(account);
        userDTO.setName(name);
        userDTO.setPassword(password);
        userDTO.setYhcdGid(cdid);
        userDTO.setGlbm(glbm);
        return userDTO;

    }

    /**
     * 根据用户id获取员工信息
     *
     * @param user_id
     * @return
     */
    protected UserDTO getStaffInfoFromUserId(String user_id, String loginType) {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT ");
        sb.append(" s.id,s.staffName,d.departmentStatus ");
        sb.append(" FROM t_staff s ");
        sb.append(" LEFT JOIN t_department as d on s.department_id = d.id AND d.deleteStatus = '0' ");
        sb.append(" WHERE 1 = 1 ");
        sb.append(" AND s.deleteStatus = 0 ");
        sb.append(" AND s.globaluser_id = :globaluser_id ");

        Map params = new HashMap();
        params.put("globaluser_id", user_id);

        List<Map> list = this.commonService.nativeQuery(sb.toString(), params);
        if (list == null || list.size() == 0) {
            throw new ApplicationException("用户不存在");
        }

        if (list.size() > 1) throw new ApplicationException("员工信息异常:该用户对应多个员工");

        Map infoMap = list.get(0);
        String staff_id = this.commonService.getParam(infoMap, "id");
        String staffName = this.commonService.getParam(infoMap, "staffName");
        UserDTO userDTO = new UserDTO();
        //部门状态(0 启用 1 停用)
        String departmentStatus = this.commonService.getParam(infoMap, "departmentStatus");
        //后台登录 部门异常禁止登录
        if ("back".equals(loginType)) {
            if (!"0".equals(departmentStatus)) {
                throw new ApplicationException("-1", "部门状态异常");
            }
        }
//        if("0".equals(departmentStatus)){
//            userDTO.setStaff_id(staff_id);
//            userDTO.setStaffName(staffName);
//        }
        return userDTO;
    }


    /**
     * 父类的空方法  用来被子类继承
     *
     * @param realUserName
     * @return
     */
    public UserDTO getUserInfoDetailInfo(String realUserName) {
        return new UserDTO();
    }
}
