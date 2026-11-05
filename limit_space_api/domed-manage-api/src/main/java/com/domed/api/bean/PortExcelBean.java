package com.domed.api.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * TODO
 *
 * @Description
 * @Author lirong
 * @Date 2023/3/1 16:48
 **/
public class PortExcelBean {
    /**
     * name 指定导出excel是生成的列名
     * orderNum可指定导出的该属性对应的所在列的位置
     * width设置单元格宽度
     * type设置导出类型 1是文本 2是图片 3是函数 10数字 默认是文本
     */
    @Excel(name = "序号", orderNum = "1", type = 1, width = 10)
    private String xh;
    @Excel(name = "港口经营许可证编号", orderNum = "2", type = 1, width = 20)
    private String xkzh;
    @Excel(name = "企业名称", orderNum = "3", type = 1, width = 20)
    private String qymc;
    @Excel(name = "企业法人", orderNum = "4", type = 1, width = 20)
    private String qyfr;
    @Excel(name = "社会信用代码", orderNum = "5", type = 1, width = 20)
    private String shxydm;
    @Excel(name = "企业地址", orderNum = "6", type = 1, width = 20)
    private String qydz;
    @Excel(name = "经营范围", orderNum = "7", type = 1, width = 40)
    private String jyfw;
    @Excel(name = "经营地域", orderNum = "8", type = 1, width = 40)
    private String jydd;
    @Excel(name = "经营期限", orderNum = "9", type = 1, width = 30)
    private String jyqx;
    @Excel(name = "联系方式", orderNum = "10", type = 1, width = 30)
    private String lxfs;

    @Excel(name = "辖区", orderNum = "10", type = 1, width = 30)
    private String xzqh;

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getXkzh() {
        return xkzh;
    }

    public void setXkzh(String xkzh) {
        this.xkzh = xkzh;
    }

    public String getQymc() {
        return qymc;
    }

    public void setQymc(String qymc) {
        this.qymc = qymc;
    }

    public String getQyfr() {
        return qyfr;
    }

    public void setQyfr(String qyfr) {
        this.qyfr = qyfr;
    }

    public String getShxydm() {
        return shxydm;
    }

    public void setShxydm(String shxydm) {
        this.shxydm = shxydm;
    }

    public String getQydz() {
        return qydz;
    }

    public void setQydz(String qydz) {
        this.qydz = qydz;
    }

    public String getJyfw() {
        return jyfw;
    }

    public void setJyfw(String jyfw) {
        this.jyfw = jyfw;
    }

    public String getJydd() {
        return jydd;
    }

    public void setJydd(String jydd) {
        this.jydd = jydd;
    }

    public String getJyqx() {
        return jyqx;
    }

    public void setJyqx(String jyqx) {
        this.jyqx = jyqx;
    }

    public String getLxfs() {
        return lxfs;
    }

    public void setLxfs(String lxfs) {
        this.lxfs = lxfs;
    }

    public String getXzqh() {
        return xzqh;
    }

    public void setXzqh(String xzqh) {
        this.xzqh = xzqh;
    }
}
