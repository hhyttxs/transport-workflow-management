package com.domed.domain.hy;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "TAB_YHWH")
public class TabYhwh implements Serializable {

	private static final long serialVersionUID =  1170795644226759397L;

	/**
	 * 账号
	 */
	@Column(name = "CODE")
	private String code;

	/**
	 * 密码
	 */
	@Column(name = "PASSWORD")
	private String password;

	/**
	 * 状态（0删除，1正常）
	 */
	@Column(name = "STATUS")
	private String status;

	/**
	 * 创建人
	 */
	@Column(name = "NEW_USER")
	private String newUser;

	/**
	 * 创建时间
	 */
	@Column(name = "NEW_USER_TIME")
	private Date newUserTime;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "GID")
	private String gid;

	/**
	 * 姓名
	 */
	@Column(name = "NAME")
	private String name;

	/**
	 * 业户菜单GID
	 */
	@Column(name = "YHCD_GID")
	private String yhcdGid;

	/**
	 * 电话
	 */
	@Column(name = "PHONE")
	private String phone;

	/**
	 * 类型
	 */
	@Column(name = "TYPE")
	private String type;

}
