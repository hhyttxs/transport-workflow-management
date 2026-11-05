package com.domed.domain.transportDomain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.*;
import java.util.Date;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "HY_CLB")
@Access(AccessType.FIELD)
@DynamicInsert
@DynamicUpdate
public class HyClb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "vehicle_hy_clb_seq", sequenceName = "HY_CLB_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_hy_clb_seq")
    @Column(name = "ID", columnDefinition = "NUMBER(20,0)", nullable = false, precision = 20, scale = 0)
    private BigInteger id;

    @Column(name = "U_CLBM", columnDefinition = "VARCHAR2(36 CHAR)", nullable = true, length = 36)
    private String uClbm;

    @Column(name = "U_YHBM", columnDefinition = "VARCHAR2(36 CHAR)", nullable = true, length = 36)
    private String uYhbm;

    @Column(name = "C_YHBH", columnDefinition = "VARCHAR2(20 CHAR)", nullable = true, length = 20)
    private String cYhbh;

    @Column(name = "C_XKZH", columnDefinition = "VARCHAR2(20 CHAR)", nullable = true, length = 20)
    private String cXkzh;

    @Column(name = "C_YYZH", columnDefinition = "VARCHAR2(20 CHAR)", nullable = true, length = 20)
    private String cYyzh;

    @Column(name = "C_CH", columnDefinition = "VARCHAR2(50 CHAR)", nullable = true, length = 50)
    private String cCh;

    @Column(name = "C_CPXH", columnDefinition = "VARCHAR2(50 CHAR)", nullable = true, length = 50)
    private String cCpxh;

    @Column(name = "C_CLLX", columnDefinition = "VARCHAR2(50 CHAR)", nullable = true, length = 50)
    private String cCllx;

    @Column(name = "D_CLDW", columnDefinition = "NUMBER", nullable = true)
    private BigDecimal dCldw;

    @Column(name = "C_JSDJ", columnDefinition = "VARCHAR2(3 CHAR)", nullable = true, length = 3)
    private String cJsdj;

    @Column(name = "C_RLLX", columnDefinition = "VARCHAR2(10 CHAR)", nullable = true, length = 10)
    private String cRllx;

    @Column(name = "C_YYZT", columnDefinition = "VARCHAR2(10 CHAR)", nullable = true, length = 10)
    private String cYyzt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "D_JFZR", columnDefinition = "DATE", nullable = true)
    private Date dJfzr;

    @Column(name = "C_JYFW", columnDefinition = "VARCHAR2(800 CHAR)", nullable = true, length = 800)
    private String cJyfw;

    @Column(name = "C_JYFW1", columnDefinition = "NUMBER(11,0)", nullable = true, precision = 11, scale = 0)
    private BigInteger cJyfw1;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "D_CCRQ", columnDefinition = "DATE", nullable = true)
    private Date dCcrq;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "D_HFRQ", columnDefinition = "DATE", nullable = true)
    private Date dHfrq;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "D_LRSJ", columnDefinition = "DATE", nullable = true)
    private Date dLrsj;

    @Column(name = "C_LRR", columnDefinition = "VARCHAR2(40 CHAR)", nullable = true, length = 40)
    private String cLrr;

    @Column(name = "C_LRBM", columnDefinition = "VARCHAR2(10 CHAR)", nullable = true, length = 10)
    private String cLrbm;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "D_XGSJ", columnDefinition = "DATE", nullable = true)
    private Date dXgsj;

    @Column(name = "C_XGR", columnDefinition = "VARCHAR2(30 CHAR)", nullable = true, length = 30)
    private String cXgr;

    @Column(name = "C_XGBM", columnDefinition = "VARCHAR2(10 CHAR)", nullable = true, length = 10)
    private String cXgbm;

    @Column(name = "T_CX", columnDefinition = "NUMBER(4,0)", nullable = true, precision = 4, scale = 0)
    private BigDecimal tCx;

    @Column(name = "T_CZ", columnDefinition = "NUMBER(4,0)", nullable = true, precision = 4, scale = 0)
    private BigDecimal tCz;

    @Column(name = "F_ZZ", columnDefinition = "NUMBER", nullable = true)
    private BigDecimal fZz;

    @Column(name = "C_YHKH", columnDefinition = "VARCHAR2(50 CHAR)", nullable = true, length = 50)
    private String cYhkh;

    @Column(name = "C_JGDM", columnDefinition = "VARCHAR2(50 CHAR)", nullable = true, length = 50)
    private String cJgdm;

    @Column(name = "C_YCZ", columnDefinition = "VARCHAR2(50 CHAR)", nullable = true, length = 50)
    private String cYcz;

    @Column(name = "I_EJWHZQ", columnDefinition = "NUMBER(11,0)", nullable = true, precision = 11, scale = 0)
    private BigInteger iEjwhzq;

    @Column(name = "F_DE", columnDefinition = "NUMBER", nullable = true)
    private BigDecimal fDe;

    @Column(name = "F_DES", columnDefinition = "NUMBER", nullable = true)
    private BigDecimal fDes;

    @Column(name = "F_FL", columnDefinition = "NUMBER", nullable = true)
    private BigDecimal fFl;

    @Column(name = "F_SL", columnDefinition = "NUMBER", nullable = true)
    private BigDecimal fSl;

    @Column(name = "F_FZL", columnDefinition = "NUMBER", nullable = true)
    private BigDecimal fFzl;

    @Column(name = "F_SZL", columnDefinition = "NUMBER", nullable = true)
    private BigDecimal fSzl;

    @Column(name = "F_SFFS", columnDefinition = "NUMBER", nullable = true)
    private BigDecimal fSffs;

    @Column(name = "I_SFDZS", columnDefinition = "NUMBER(11,0)", nullable = true, precision = 11, scale = 0)
    private BigInteger iSfdzs;

    @Column(name = "T_JYSX", columnDefinition = "NUMBER(4,0)", nullable = true, precision = 4, scale = 0)
    private BigDecimal tJysx;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "D_MSQR", columnDefinition = "DATE", nullable = true)
    private Date dMsqr;

    @Column(name = "V_RWBH", columnDefinition = "VARCHAR2(36 CHAR)", nullable = true, length = 36)
    private String vRwbh;

    @Column(name = "V_RWBH_JC", columnDefinition = "VARCHAR2(36 CHAR)", nullable = true, length = 36)
    private String vRwbhJc;

    @Column(name = "V_RWBH_ZC", columnDefinition = "VARCHAR2(36 CHAR)", nullable = true, length = 36)
    private String vRwbhZc;

    @Column(name = "I_SFSF", columnDefinition = "NUMBER(11,0)", nullable = true, precision = 11, scale = 0)
    private BigInteger iSfsf;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "D_JFQR", columnDefinition = "DATE", nullable = true)
    private Date dJfqr;

    @Column(name = "M_SF", columnDefinition = "NUMBER", nullable = true)
    private BigDecimal mSf;

    @Column(name = "T_SFWZ", columnDefinition = "NUMBER(4,0)", nullable = true, precision = 4, scale = 0)
    private BigDecimal tSfwz;

    @Column(name = "V_WZXX", columnDefinition = "VARCHAR2(255 CHAR)", nullable = true, length = 255)
    private String vWzxx;

    @Column(name = "T_SFJC", columnDefinition = "NUMBER(4,0)", nullable = true, precision = 4, scale = 0)
    private BigDecimal tSfjc;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "D_JCRQ", columnDefinition = "DATE", nullable = true)
    private Date dJcrq;

    @Column(name = "C_YYLX", columnDefinition = "VARCHAR2(2 CHAR)", nullable = true, length = 2)
    private String cYylx;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "D_CJRQ", columnDefinition = "DATE", nullable = true)
    private Date dCjrq;

    @Column(name = "C_YYZHOLD", columnDefinition = "VARCHAR2(20 CHAR)", nullable = true, length = 20)
    private String cYyzhold;

    @Column(name = "C_SJJG", columnDefinition = "VARCHAR2(200 CHAR)", nullable = true, length = 200)
    private String cSjjg;

    @Column(name = "T_ZS", columnDefinition = "NUMBER(4,0)", nullable = true, precision = 4, scale = 0)
    private BigDecimal tZs;

    @Column(name = "C_CJH", columnDefinition = "VARCHAR2(50 CHAR)", nullable = true, length = 50)
    private String cCjh;

    @Column(name = "C_XKZHOLD", columnDefinition = "VARCHAR2(20 CHAR)", nullable = true, length = 20)
    private String cXkzhold;

    @Column(name = "C_FDJH", columnDefinition = "VARCHAR2(50 CHAR)", nullable = true, length = 50)
    private String cFdjh;

    @Column(name = "C_DPH", columnDefinition = "VARCHAR2(50 CHAR)", nullable = true, length = 50)
    private String cDph;

    @Column(name = "C_CSYS", columnDefinition = "VARCHAR2(50 CHAR)", nullable = true, length = 50)
    private String cCsys;

    @Column(name = "C_SFGKJY", columnDefinition = "VARCHAR2(50 CHAR)", nullable = true, length = 50)
    private String cSfgkjy;

    @Column(name = "C_SFCRJ", columnDefinition = "VARCHAR2(50 CHAR)", nullable = true, length = 50)
    private String cSfcrj;

    @Column(name = "C_SFZZJY", columnDefinition = "VARCHAR2(50 CHAR)", nullable = true, length = 50)
    private String cSfzzjy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_CLLY", columnDefinition = "DATE", nullable = true)
    private Date cClly;

    @Column(name = "T_ZT", columnDefinition = "NUMBER(11,0)", nullable = true, precision = 11, scale = 0)
    private BigInteger tZt;

    @Column(name = "F_CLCD", columnDefinition = "NUMBER", nullable = true)
    private BigDecimal fClcd;

    @Column(name = "F_CLKD", columnDefinition = "NUMBER", nullable = true)
    private BigDecimal fClkd;

    @Column(name = "F_CLGD", columnDefinition = "NUMBER", nullable = true)
    private BigDecimal fClgd;

    @Column(name = "I_PKZQ", columnDefinition = "NUMBER(11,0)", nullable = true, precision = 11, scale = 0)
    private BigInteger iPkzq;

    @Column(name = "C_JJLX", columnDefinition = "VARCHAR2(20 CHAR)", nullable = true, length = 20)
    private String cJjlx;

    @Column(name = "F_DE_Q", columnDefinition = "NUMBER", nullable = true)
    private BigDecimal fDeQ;

    @Column(name = "C_EXPLAIN", columnDefinition = "VARCHAR2(50 CHAR)", nullable = true, length = 50)
    private String cExplain;

    @Column(name = "C_YYZH_Y", columnDefinition = "VARCHAR2(12 CHAR)", nullable = true, length = 12)
    private String cYyzhY;

    @Column(name = "N_BFNX", columnDefinition = "NUMBER(11,0)", nullable = true, precision = 11, scale = 0)
    private BigInteger nBfnx;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "D_BFRQ", columnDefinition = "DATE", nullable = true)
    private Date dBfrq;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "D_NSYSQ", columnDefinition = "DATE", nullable = true)
    private Date dNsysq;

    @Column(name = "T_ZCRQ", columnDefinition = "NUMBER(11,0)", nullable = true, precision = 11, scale = 0)
    private BigInteger tZcrq;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "D_NSYXQ", columnDefinition = "DATE", nullable = true)
    private Date dNsyxq;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "D_CLXCRQ", columnDefinition = "DATE", nullable = true)
    private Date dClxcrq;

    @Column(name = "C_NSBZ", columnDefinition = "VARCHAR2(500 CHAR)", nullable = true, length = 500)
    private String cNsbz;

    @Column(name = "RLXHL", columnDefinition = "NUMBER(4,0)", nullable = true, precision = 4, scale = 0)
    private BigDecimal rlxhl;

    @Column(name = "QTBDZT", columnDefinition = "NUMBER(4,0)", nullable = true, precision = 4, scale = 0)
    private BigDecimal qtbdzt;

    @Column(name = "SLYJ", columnDefinition = "VARCHAR2(50 CHAR)", nullable = true, length = 50)
    private String slyj;

    @Column(name = "SPYJ", columnDefinition = "VARCHAR2(50 CHAR)", nullable = true, length = 50)
    private String spyj;

    @Column(name = "SPR", columnDefinition = "VARCHAR2(20 CHAR)", nullable = true, length = 20)
    private String spr;

    @Column(name = "SLRXM", columnDefinition = "VARCHAR2(20 CHAR)", nullable = true, length = 20)
    private String slrxm;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SLSJ", columnDefinition = "DATE", nullable = true)
    private Date slsj;

    @Column(name = "SPRLAST", columnDefinition = "VARCHAR2(20 CHAR)", nullable = true, length = 20)
    private String sprlast;

    @Column(name = "SPYJLAST", columnDefinition = "VARCHAR2(20 CHAR)", nullable = true, length = 20)
    private String spyjlast;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SPSJ", columnDefinition = "DATE", nullable = true)
    private Date spsj;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SPSJLAST", columnDefinition = "DATE", nullable = true)
    private Date spsjlast;

    @Column(name = "DJFS", columnDefinition = "NUMBER(4,0)", nullable = true, precision = 4, scale = 0)
    private BigDecimal djfs;

    @Column(name = "GTRJ", columnDefinition = "NUMBER", nullable = true)
    private BigDecimal gtrj;

    @Column(name = "WHPM", columnDefinition = "VARCHAR2(1000 CHAR)", nullable = true, length = 1000)
    private String whpm;

    @Column(name = "MD", columnDefinition = "NUMBER", nullable = true)
    private BigDecimal md;

    @Column(name = "LXFS", columnDefinition = "VARCHAR2(500 CHAR)", nullable = true, length = 500)
    private String lxfs;

    @Column(name = "XZQH", columnDefinition = "VARCHAR2(80 BYTE)", nullable = true, length = 80)
    private String xzqh;

    @Column(name = "SUO", columnDefinition = "VARCHAR2(80 BYTE)", nullable = true, length = 80)
    private String suo;

    @Column(name = "ZHAN", columnDefinition = "VARCHAR2(80 BYTE)", nullable = true, length = 80)
    private String zhan;

    @Column(name = "C_ZWH", columnDefinition = "VARCHAR2(4 BYTE)", nullable = true, length = 4)
    private String cZwh;

    @Column(name = "ISNIANSHEN", columnDefinition = "VARCHAR2(4 BYTE)", nullable = true, length = 4)
    private String isnianshen;

    @Column(name = "C_CPYS", columnDefinition = "VARCHAR2(4 BYTE)", nullable = true, length = 4)
    private String cCpys;

    @Column(name = "SY_CLXH", columnDefinition = "VARCHAR2(255 BYTE)", nullable = true, length = 255)
    private String syClxh;

    @Column(name = "SY_YYZZ", columnDefinition = "VARCHAR2(255 BYTE)", nullable = true, length = 255)
    private String syYyzz;

    @Column(name = "SY_YYZH", columnDefinition = "VARCHAR2(255 BYTE)", nullable = true, length = 255)
    private String syYyzh;

    @Column(name = "N_BZ", columnDefinition = "VARCHAR2(4000 BYTE)", nullable = true, length = 4000)
    private String nBz;

    @Nationalized
    @Column(name = "N_SXMC", columnDefinition = "NVARCHAR2(50)", nullable = true, length = 50)
    private String nSxmc;

    @Column(name = "N_YWLX", columnDefinition = "VARCHAR2(50 BYTE)", nullable = true, length = 50)
    private String nYwlx;

    @Column(name = "N_HDZZL", columnDefinition = "NUMBER", nullable = true)
    private BigDecimal nHdzzl;

    @Nationalized
    @Column(name = "N_CLLX", columnDefinition = "NVARCHAR2(50)", nullable = true, length = 50)
    private String nCllx;

    @Column(name = "DO_THING_STATUS", columnDefinition = "CHAR(1 BYTE)", nullable = true, length = 1)
    private String doThingStatus;

    @Column(name = "DBJLX", columnDefinition = "VARCHAR2(255 BYTE)", nullable = true, length = 255)
    private String dbjlx;

    @Column(name = "N_CP", columnDefinition = "VARCHAR2(40 BYTE)", nullable = true, length = 40)
    private String nCp;

    @Column(name = "N_XH", columnDefinition = "VARCHAR2(40 BYTE)", nullable = true, length = 40)
    private String nXh;

    @Column(name = "N_JBRID", columnDefinition = "VARCHAR2(255 BYTE)", nullable = true, length = 255)
    private String nJbrid;

    @Column(name = "N_YWFZRID", columnDefinition = "VARCHAR2(255 BYTE)", nullable = true, length = 255)
    private String nYwfzrid;

    @Column(name = "C_JYFWFIRST", columnDefinition = "VARCHAR2(255 BYTE)", nullable = true, length = 255)
    private String cJyfwfirst;

    @Column(name = "ISUPLOADLACK", columnDefinition = "VARCHAR2(255 BYTE)", nullable = true, length = 255)
    private String isuploadlack;

    @Column(name = "LETTER_GID", columnDefinition = "VARCHAR2(36 BYTE)", nullable = true, length = 36)
    private String letterGid;

    @Column(name = "ISDAILY", columnDefinition = "CHAR(1 BYTE)", nullable = true, length = 1)
    private String isdaily;

    @Column(name = "ISMUSTUPLOAD", columnDefinition = "CHAR(1 BYTE)", nullable = true, length = 1)
    private String ismustupload;

    @Column(name = "EXTERNAL_CLBM", columnDefinition = "VARCHAR2(255 BYTE)", nullable = true, length = 255)
    private String externalClbm;

    @Column(name = "DATAFROM", columnDefinition = "VARCHAR2(255 BYTE)", nullable = true, length = 255)
    private String datafrom;

    @Column(name = "APPLICATIONFORMTYPE", columnDefinition = "VARCHAR2(10 BYTE)", nullable = true, length = 10)
    private String applicationformtype;

    @Column(name = "N_OCLBM", columnDefinition = "VARCHAR2(255 BYTE)", nullable = true, length = 255)
    private String nOclbm;

    @Column(name = "XKWSBH", columnDefinition = "VARCHAR2(255 BYTE)", nullable = true, length = 255)
    private String xkwsbh;

    @Column(name = "ZHOUSHU", columnDefinition = "VARCHAR2(255 BYTE)", nullable = true, length = 255)
    private String zhoushu;

    @Column(name = "POWER", columnDefinition = "VARCHAR2(255 BYTE)", nullable = true, length = 255)
    private String power;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CCRQ", columnDefinition = "DATE", nullable = true)
    private Date ccrq;

    @Column(name = "OLD_YYZH", columnDefinition = "VARCHAR2(255 BYTE)", nullable = true, length = 255)
    private String oldYyzh;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "GMRQ", columnDefinition = "DATE", nullable = true)
    private Date gmrq;

    @Column(name = "OLD_XKZH", columnDefinition = "VARCHAR2(255 BYTE)", nullable = true, length = 255)
    private String oldXkzh;

    @Column(name = "HH_FLAG", columnDefinition = "VARCHAR2(255 BYTE)", nullable = true, length = 255)
    private String hhFlag;

    @Column(name = "CLSYXZ", columnDefinition = "VARCHAR2(255 BYTE)", nullable = true, length = 255)
    private String clsyxz;

    @Column(name = "C_PFBZ", columnDefinition = "VARCHAR2(255 BYTE)", nullable = true, length = 255)
    private String cPfbz;

    @Column(name = "JCFROM", columnDefinition = "VARCHAR2(255 BYTE)", nullable = true, length = 255)
    private String jcfrom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "GPSKS", columnDefinition = "DATE", nullable = true)
    private Date gpsks;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "GPSJZ", columnDefinition = "DATE", nullable = true)
    private Date gpsjz;

    @Column(name = "D_ZQYZZL", columnDefinition = "VARCHAR2(255 BYTE)", nullable = true, length = 255)
    private String dZqyzzl;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_ZJRQ", columnDefinition = "DATE", nullable = true)
    private Date lastZjrq;

    public BigInteger getId() { return id; }
    public void setId(BigInteger value) { this.id = value; }
    public String getUClbm() { return uClbm; }
    public void setUClbm(String value) { this.uClbm = value; }
    public String getUYhbm() { return uYhbm; }
    public void setUYhbm(String value) { this.uYhbm = value; }
    public String getCYhbh() { return cYhbh; }
    public void setCYhbh(String value) { this.cYhbh = value; }
    public String getCXkzh() { return cXkzh; }
    public void setCXkzh(String value) { this.cXkzh = value; }
    public String getCYyzh() { return cYyzh; }
    public void setCYyzh(String value) { this.cYyzh = value; }
    public String getCCh() { return cCh; }
    public void setCCh(String value) { this.cCh = value; }
    public String getCCpxh() { return cCpxh; }
    public void setCCpxh(String value) { this.cCpxh = value; }
    public String getCCllx() { return cCllx; }
    public void setCCllx(String value) { this.cCllx = value; }
    public BigDecimal getDCldw() { return dCldw; }
    public void setDCldw(BigDecimal value) { this.dCldw = value; }
    public String getCJsdj() { return cJsdj; }
    public void setCJsdj(String value) { this.cJsdj = value; }
    public String getCRllx() { return cRllx; }
    public void setCRllx(String value) { this.cRllx = value; }
    public String getCYyzt() { return cYyzt; }
    public void setCYyzt(String value) { this.cYyzt = value; }
    public Date getDJfzr() { return dJfzr; }
    public void setDJfzr(Date value) { this.dJfzr = value; }
    public String getCJyfw() { return cJyfw; }
    public void setCJyfw(String value) { this.cJyfw = value; }
    public BigInteger getCJyfw1() { return cJyfw1; }
    public void setCJyfw1(BigInteger value) { this.cJyfw1 = value; }
    public Date getDCcrq() { return dCcrq; }
    public void setDCcrq(Date value) { this.dCcrq = value; }
    public Date getDHfrq() { return dHfrq; }
    public void setDHfrq(Date value) { this.dHfrq = value; }
    public Date getDLrsj() { return dLrsj; }
    public void setDLrsj(Date value) { this.dLrsj = value; }
    public String getCLrr() { return cLrr; }
    public void setCLrr(String value) { this.cLrr = value; }
    public String getCLrbm() { return cLrbm; }
    public void setCLrbm(String value) { this.cLrbm = value; }
    public Date getDXgsj() { return dXgsj; }
    public void setDXgsj(Date value) { this.dXgsj = value; }
    public String getCXgr() { return cXgr; }
    public void setCXgr(String value) { this.cXgr = value; }
    public String getCXgbm() { return cXgbm; }
    public void setCXgbm(String value) { this.cXgbm = value; }
    public BigDecimal getTCx() { return tCx; }
    public void setTCx(BigDecimal value) { this.tCx = value; }
    public BigDecimal getTCz() { return tCz; }
    public void setTCz(BigDecimal value) { this.tCz = value; }
    public BigDecimal getFZz() { return fZz; }
    public void setFZz(BigDecimal value) { this.fZz = value; }
    public String getCYhkh() { return cYhkh; }
    public void setCYhkh(String value) { this.cYhkh = value; }
    public String getCJgdm() { return cJgdm; }
    public void setCJgdm(String value) { this.cJgdm = value; }
    public String getCYcz() { return cYcz; }
    public void setCYcz(String value) { this.cYcz = value; }
    public BigInteger getIEjwhzq() { return iEjwhzq; }
    public void setIEjwhzq(BigInteger value) { this.iEjwhzq = value; }
    public BigDecimal getFDe() { return fDe; }
    public void setFDe(BigDecimal value) { this.fDe = value; }
    public BigDecimal getFDes() { return fDes; }
    public void setFDes(BigDecimal value) { this.fDes = value; }
    public BigDecimal getFFl() { return fFl; }
    public void setFFl(BigDecimal value) { this.fFl = value; }
    public BigDecimal getFSl() { return fSl; }
    public void setFSl(BigDecimal value) { this.fSl = value; }
    public BigDecimal getFFzl() { return fFzl; }
    public void setFFzl(BigDecimal value) { this.fFzl = value; }
    public BigDecimal getFSzl() { return fSzl; }
    public void setFSzl(BigDecimal value) { this.fSzl = value; }
    public BigDecimal getFSffs() { return fSffs; }
    public void setFSffs(BigDecimal value) { this.fSffs = value; }
    public BigInteger getISfdzs() { return iSfdzs; }
    public void setISfdzs(BigInteger value) { this.iSfdzs = value; }
    public BigDecimal getTJysx() { return tJysx; }
    public void setTJysx(BigDecimal value) { this.tJysx = value; }
    public Date getDMsqr() { return dMsqr; }
    public void setDMsqr(Date value) { this.dMsqr = value; }
    public String getVRwbh() { return vRwbh; }
    public void setVRwbh(String value) { this.vRwbh = value; }
    public String getVRwbhJc() { return vRwbhJc; }
    public void setVRwbhJc(String value) { this.vRwbhJc = value; }
    public String getVRwbhZc() { return vRwbhZc; }
    public void setVRwbhZc(String value) { this.vRwbhZc = value; }
    public BigInteger getISfsf() { return iSfsf; }
    public void setISfsf(BigInteger value) { this.iSfsf = value; }
    public Date getDJfqr() { return dJfqr; }
    public void setDJfqr(Date value) { this.dJfqr = value; }
    public BigDecimal getMSf() { return mSf; }
    public void setMSf(BigDecimal value) { this.mSf = value; }
    public BigDecimal getTSfwz() { return tSfwz; }
    public void setTSfwz(BigDecimal value) { this.tSfwz = value; }
    public String getVWzxx() { return vWzxx; }
    public void setVWzxx(String value) { this.vWzxx = value; }
    public BigDecimal getTSfjc() { return tSfjc; }
    public void setTSfjc(BigDecimal value) { this.tSfjc = value; }
    public Date getDJcrq() { return dJcrq; }
    public void setDJcrq(Date value) { this.dJcrq = value; }
    public String getCYylx() { return cYylx; }
    public void setCYylx(String value) { this.cYylx = value; }
    public Date getDCjrq() { return dCjrq; }
    public void setDCjrq(Date value) { this.dCjrq = value; }
    public String getCYyzhold() { return cYyzhold; }
    public void setCYyzhold(String value) { this.cYyzhold = value; }
    public String getCSjjg() { return cSjjg; }
    public void setCSjjg(String value) { this.cSjjg = value; }
    public BigDecimal getTZs() { return tZs; }
    public void setTZs(BigDecimal value) { this.tZs = value; }
    public String getCCjh() { return cCjh; }
    public void setCCjh(String value) { this.cCjh = value; }
    public String getCXkzhold() { return cXkzhold; }
    public void setCXkzhold(String value) { this.cXkzhold = value; }
    public String getCFdjh() { return cFdjh; }
    public void setCFdjh(String value) { this.cFdjh = value; }
    public String getCDph() { return cDph; }
    public void setCDph(String value) { this.cDph = value; }
    public String getCCsys() { return cCsys; }
    public void setCCsys(String value) { this.cCsys = value; }
    public String getCSfgkjy() { return cSfgkjy; }
    public void setCSfgkjy(String value) { this.cSfgkjy = value; }
    public String getCSfcrj() { return cSfcrj; }
    public void setCSfcrj(String value) { this.cSfcrj = value; }
    public String getCSfzzjy() { return cSfzzjy; }
    public void setCSfzzjy(String value) { this.cSfzzjy = value; }
    public Date getCClly() { return cClly; }
    public void setCClly(Date value) { this.cClly = value; }
    public BigInteger getTZt() { return tZt; }
    public void setTZt(BigInteger value) { this.tZt = value; }
    public BigDecimal getFClcd() { return fClcd; }
    public void setFClcd(BigDecimal value) { this.fClcd = value; }
    public BigDecimal getFClkd() { return fClkd; }
    public void setFClkd(BigDecimal value) { this.fClkd = value; }
    public BigDecimal getFClgd() { return fClgd; }
    public void setFClgd(BigDecimal value) { this.fClgd = value; }
    public BigInteger getIPkzq() { return iPkzq; }
    public void setIPkzq(BigInteger value) { this.iPkzq = value; }
    public String getCJjlx() { return cJjlx; }
    public void setCJjlx(String value) { this.cJjlx = value; }
    public BigDecimal getFDeQ() { return fDeQ; }
    public void setFDeQ(BigDecimal value) { this.fDeQ = value; }
    public String getCExplain() { return cExplain; }
    public void setCExplain(String value) { this.cExplain = value; }
    public String getCYyzhY() { return cYyzhY; }
    public void setCYyzhY(String value) { this.cYyzhY = value; }
    public BigInteger getNBfnx() { return nBfnx; }
    public void setNBfnx(BigInteger value) { this.nBfnx = value; }
    public Date getDBfrq() { return dBfrq; }
    public void setDBfrq(Date value) { this.dBfrq = value; }
    public Date getDNsysq() { return dNsysq; }
    public void setDNsysq(Date value) { this.dNsysq = value; }
    public BigInteger getTZcrq() { return tZcrq; }
    public void setTZcrq(BigInteger value) { this.tZcrq = value; }
    public Date getDNsyxq() { return dNsyxq; }
    public void setDNsyxq(Date value) { this.dNsyxq = value; }
    public Date getDClxcrq() { return dClxcrq; }
    public void setDClxcrq(Date value) { this.dClxcrq = value; }
    public String getCNsbz() { return cNsbz; }
    public void setCNsbz(String value) { this.cNsbz = value; }
    public BigDecimal getRlxhl() { return rlxhl; }
    public void setRlxhl(BigDecimal value) { this.rlxhl = value; }
    public BigDecimal getQtbdzt() { return qtbdzt; }
    public void setQtbdzt(BigDecimal value) { this.qtbdzt = value; }
    public String getSlyj() { return slyj; }
    public void setSlyj(String value) { this.slyj = value; }
    public String getSpyj() { return spyj; }
    public void setSpyj(String value) { this.spyj = value; }
    public String getSpr() { return spr; }
    public void setSpr(String value) { this.spr = value; }
    public String getSlrxm() { return slrxm; }
    public void setSlrxm(String value) { this.slrxm = value; }
    public Date getSlsj() { return slsj; }
    public void setSlsj(Date value) { this.slsj = value; }
    public String getSprlast() { return sprlast; }
    public void setSprlast(String value) { this.sprlast = value; }
    public String getSpyjlast() { return spyjlast; }
    public void setSpyjlast(String value) { this.spyjlast = value; }
    public Date getSpsj() { return spsj; }
    public void setSpsj(Date value) { this.spsj = value; }
    public Date getSpsjlast() { return spsjlast; }
    public void setSpsjlast(Date value) { this.spsjlast = value; }
    public BigDecimal getDjfs() { return djfs; }
    public void setDjfs(BigDecimal value) { this.djfs = value; }
    public BigDecimal getGtrj() { return gtrj; }
    public void setGtrj(BigDecimal value) { this.gtrj = value; }
    public String getWhpm() { return whpm; }
    public void setWhpm(String value) { this.whpm = value; }
    public BigDecimal getMd() { return md; }
    public void setMd(BigDecimal value) { this.md = value; }
    public String getLxfs() { return lxfs; }
    public void setLxfs(String value) { this.lxfs = value; }
    public String getXzqh() { return xzqh; }
    public void setXzqh(String value) { this.xzqh = value; }
    public String getSuo() { return suo; }
    public void setSuo(String value) { this.suo = value; }
    public String getZhan() { return zhan; }
    public void setZhan(String value) { this.zhan = value; }
    public String getCZwh() { return cZwh; }
    public void setCZwh(String value) { this.cZwh = value; }
    public String getIsnianshen() { return isnianshen; }
    public void setIsnianshen(String value) { this.isnianshen = value; }
    public String getCCpys() { return cCpys; }
    public void setCCpys(String value) { this.cCpys = value; }
    public String getSyClxh() { return syClxh; }
    public void setSyClxh(String value) { this.syClxh = value; }
    public String getSyYyzz() { return syYyzz; }
    public void setSyYyzz(String value) { this.syYyzz = value; }
    public String getSyYyzh() { return syYyzh; }
    public void setSyYyzh(String value) { this.syYyzh = value; }
    public String getNBz() { return nBz; }
    public void setNBz(String value) { this.nBz = value; }
    public String getNSxmc() { return nSxmc; }
    public void setNSxmc(String value) { this.nSxmc = value; }
    public String getNYwlx() { return nYwlx; }
    public void setNYwlx(String value) { this.nYwlx = value; }
    public BigDecimal getNHdzzl() { return nHdzzl; }
    public void setNHdzzl(BigDecimal value) { this.nHdzzl = value; }
    public String getNCllx() { return nCllx; }
    public void setNCllx(String value) { this.nCllx = value; }
    public String getDoThingStatus() { return doThingStatus; }
    public void setDoThingStatus(String value) { this.doThingStatus = value; }
    public String getDbjlx() { return dbjlx; }
    public void setDbjlx(String value) { this.dbjlx = value; }
    public String getNCp() { return nCp; }
    public void setNCp(String value) { this.nCp = value; }
    public String getNXh() { return nXh; }
    public void setNXh(String value) { this.nXh = value; }
    public String getNJbrid() { return nJbrid; }
    public void setNJbrid(String value) { this.nJbrid = value; }
    public String getNYwfzrid() { return nYwfzrid; }
    public void setNYwfzrid(String value) { this.nYwfzrid = value; }
    public String getCJyfwfirst() { return cJyfwfirst; }
    public void setCJyfwfirst(String value) { this.cJyfwfirst = value; }
    public String getIsuploadlack() { return isuploadlack; }
    public void setIsuploadlack(String value) { this.isuploadlack = value; }
    public String getLetterGid() { return letterGid; }
    public void setLetterGid(String value) { this.letterGid = value; }
    public String getIsdaily() { return isdaily; }
    public void setIsdaily(String value) { this.isdaily = value; }
    public String getIsmustupload() { return ismustupload; }
    public void setIsmustupload(String value) { this.ismustupload = value; }
    public String getExternalClbm() { return externalClbm; }
    public void setExternalClbm(String value) { this.externalClbm = value; }
    public String getDatafrom() { return datafrom; }
    public void setDatafrom(String value) { this.datafrom = value; }
    public String getApplicationformtype() { return applicationformtype; }
    public void setApplicationformtype(String value) { this.applicationformtype = value; }
    public String getNOclbm() { return nOclbm; }
    public void setNOclbm(String value) { this.nOclbm = value; }
    public String getXkwsbh() { return xkwsbh; }
    public void setXkwsbh(String value) { this.xkwsbh = value; }
    public String getZhoushu() { return zhoushu; }
    public void setZhoushu(String value) { this.zhoushu = value; }
    public String getPower() { return power; }
    public void setPower(String value) { this.power = value; }
    public Date getCcrq() { return ccrq; }
    public void setCcrq(Date value) { this.ccrq = value; }
    public String getOldYyzh() { return oldYyzh; }
    public void setOldYyzh(String value) { this.oldYyzh = value; }
    public Date getGmrq() { return gmrq; }
    public void setGmrq(Date value) { this.gmrq = value; }
    public String getOldXkzh() { return oldXkzh; }
    public void setOldXkzh(String value) { this.oldXkzh = value; }
    public String getHhFlag() { return hhFlag; }
    public void setHhFlag(String value) { this.hhFlag = value; }
    public String getClsyxz() { return clsyxz; }
    public void setClsyxz(String value) { this.clsyxz = value; }
    public String getCPfbz() { return cPfbz; }
    public void setCPfbz(String value) { this.cPfbz = value; }
    public String getJcfrom() { return jcfrom; }
    public void setJcfrom(String value) { this.jcfrom = value; }
    public Date getGpsks() { return gpsks; }
    public void setGpsks(Date value) { this.gpsks = value; }
    public Date getGpsjz() { return gpsjz; }
    public void setGpsjz(Date value) { this.gpsjz = value; }
    public String getDZqyzzl() { return dZqyzzl; }
    public void setDZqyzzl(String value) { this.dZqyzzl = value; }
    public Date getLastZjrq() { return lastZjrq; }
    public void setLastZjrq(Date value) { this.lastZjrq = value; }
}

