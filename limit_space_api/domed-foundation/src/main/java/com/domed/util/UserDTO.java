package com.domed.util;

import lombok.Data;

import java.util.Date;

/**
 * @author 高杨
 * @Title: 结合globaluser和staff表的信息之和
 * @Description: 结合globaluser和staff表的信息之和
 * @date 2022/3/13 13:32
 * @return:
 */
@Data
public class UserDTO {

//    private String user_id;
//    private String staff_id;
//    private String staffName;
//    private String open_id;
//    private String union_id;
//    private Boolean isStaff;
//    private String loginJson;

    /**
     * 登录名
     */
    private String code;
    /**
     * 密码
     */
    private String password;

    /**
     * 上次登录时间
     */
    private Date lastTime;

    /**
     * 上次修改密码时间
     */
    private Date lastPwdTime;

    /**
     * 状态1 有效0无效
     */
    private Integer status;

    /**
     * 增加人
     */
    private String newUser;

    /**
     * 增加时间
     */
    private Date newUserTime;

    /**
     * 修改人
     */
    private String updUser;

    /**
     * 修改时间
     */
    private Date updUserTime;

    /**
     * 行政区划CODE
     */
    private String xzqhCode;
    /**
     * 行政机构CODE
     */
    private String xzjgCode;

    /**
     * 分配权限
     */
    private Integer fpqx;

    /**
     * 行政机构
     */
    private String zgjg;

    /**
     * 主键
     */
    private String gid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 菜单ID
     */
    private String yhcdGid;

    /**
     * 权限ID
     */
    private String yhqxGid;

    /**
     * 备注
     */
    private String memo;

    /**
     * 省系统主键
     */
    private String yzryxh;

    /**
     * 省系统机构序号
     */
    private String jgxh;

    /**
     * 业户类型
     */
    private String yhlx;

    /**
     * 行政机构（中文）
     */
    private String xzjgName;

    /**
     * 机构全称
     */
    private String jgqc;

    /**
     * 系统标识 1 货运 2 稽查 3 从业人员 4 信誉考核
     */
    private String xtbs;

    private String loginJson;
    private String userType;

    private String tyshxydm;

    private String isHaveYh;

    private String frName;

    private String jbrName;

    private String jbrIdCard;

    private String jbrPhone;

    private String qydz;

    private String jjxz;

    private String jjxzCh;

    private String jgPhone;

    private String xzjgPhone;

    private String glbm;

    private String qy;


    @Override
    public String toString() {
        return "UserDTO{" +
                "loginJson='" + loginJson + '\'' +
                "user_id='" + gid + '\'' +
//                ", staff_id='" + staff_id + '\'' +
                ", password='" + password + '\'' +
//                ", staffName='" + staffName + '\'' +
//                ", open_id='" + open_id + '\'' +
//                ", union_id='" + union_id + '\'' +
//                ", isStaff=" + isStaff +
                '}';
    }
}
