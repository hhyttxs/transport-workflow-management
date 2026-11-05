package com.domed.domain;

import com.domed.core.domain.IdEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Description  
 * @Author  
 * @Date 2023-05-21 
 */

@Data
@Entity
@Table ( name ="TAB_SYSTEM_LOGIN_LOG" )
public class TabSystemLoginLog extends IdEntity {
	/**
	 * 登陆IP
	 */
   	@Column(name = "LOGINIP" )
	private String loginip;

	/**
	 * 登陆时间
	 */
   	@Column(name = "LOGINTIME" )
	private Date logintime;

	/**
	 * 登陆账号
	 */
   	@Column(name = "LOGINCODE" )
	private String logincode;

}
