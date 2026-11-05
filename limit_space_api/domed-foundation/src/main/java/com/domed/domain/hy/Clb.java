package com.domed.domain.hy;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "CLB")
public class Clb implements Serializable {

	private static final long serialVersionUID =  14899531781456447L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "GID")
	private String gid;

	@Column(name = "ADDTIME")
	private Date addtime;

	/**
	 * 业户表GID
	 */
	@Column(name = "YHBM")
	private String yhbm;

	/**
	 * 状态（0暂存、1待预受理、2待受理、3退回、4审批通过）
	 */
	@Column(name = "STATUS")
	private String status;

	/**
	 * 待办件类型
	 */
	@Column(name = "DBJLX")
	private String dbjlx;

	@Column(name = "YWLX")
	private String ywlx;

	@Column(name = "JBR")
	private String jbr;

	@Column(name = "JYFW")
	private String jyfw;

	@Column(name = "CLLY")
	private String clly;

	@Column(name = "CLSBDM")
	private String clsbdm;

	@Column(name = "CLLX")
	private String cllx;

	@Column(name = "XSZH")
	private String xszh;

	@Column(name = "ZZL")
	private String zzl;

	@Column(name = "HDZZL")
	private String hdzzl;

	@Column(name = "CLCP")
	private String clcp;

	@Column(name = "CLXH")
	private String clxh;

	@Column(name = "ZCRQ")
	private Date zcrq;

	@Column(name = "BFRQ")
	private Date bfrq;

	@Column(name = "CLCD")
	private String clcd;

	@Column(name = "CLKD")
	private String clkd;

	@Column(name = "CLGD")
	private String clgd;

	@Column(name = "RLLX")
	private String rllx;

	@Column(name = "JSDJ")
	private String jsdj;

	@Column(name = "FDJH")
	private String fdjh;

	@Column(name = "ZS")
	private String zs;

	@Column(name = "CCRQ")
	private Date ccrq;

	@Column(name = "CLGMRQ")
	private Date clgmrq;

	@Column(name = "POWER")
	private String power;

	@Column(name = "CPYS")
	private String cpys;

	@Column(name = "PFBZ")
	private String pfbz;

}
