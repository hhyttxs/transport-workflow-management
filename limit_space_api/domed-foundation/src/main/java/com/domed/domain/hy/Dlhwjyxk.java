package com.domed.domain.hy;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "DLHWJYXK")
public class Dlhwjyxk implements Serializable {

	private static final long serialVersionUID =  4092607663813073939L;

	@Id
	@Column(name = "GID")
	private String gid;

	@Column(name = "SXMC")
	private String sxmc;

	@Column(name = "YWLX")
	private String ywlx;

	@Column(name = "JBR")
	private String jbr;

	@Column(name = "QYDZ")
	private String qydz;

	@Column(name = "YYZZFZRQ")
	private Date yyzzfzrq;

	@Column(name = "TYSHXYDM")
	private String tyshxydm;

	@Column(name = "FRXM")
	private String frxm;

	@Column(name = "FRSFZH")
	private String frsfzh;

	@Column(name = "FRLXDH")
	private String frlxdh;

	@Column(name = "JJLX")
	private String JJLX;

	@Column(name = "YSFS")
	private String ysfs;

	@Column(name = "STATUS")
	private String status;



}
