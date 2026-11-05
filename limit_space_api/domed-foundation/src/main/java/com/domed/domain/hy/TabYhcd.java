package com.domed.domain.hy;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "TAB_YHCD")
public class TabYhcd implements Serializable {

	private static final long serialVersionUID =  2426022938119720668L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "GID")
	private String gid;

	/**
	 * 0删除 1正常
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
	 * 角色名
	 */
	@Column(name = "NAME")
	private String name;

	/**
	 * 备注
	 */
	@Column(name = "DESC")
	private String desc;

	/**
	 * 公司GID
	 */
	@Column(name = "COMPANY_GID")
	private String companyGid;

}
