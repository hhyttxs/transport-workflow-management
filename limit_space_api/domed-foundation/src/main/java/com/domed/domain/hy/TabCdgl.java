package com.domed.domain.hy;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "TAB_CDGL")
public class TabCdgl implements Serializable {

	private static final long serialVersionUID =  7037805932208184033L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "GID")
	private String gid;

	/**
	 * 业户菜单ID
	 */
	@Column(name = "YHCD_GID")
	private String yhcdGid;

	/**
	 * 菜单表编码
	 */
	@Column(name = "MENU_CODE")
	private String menuCode;

}
