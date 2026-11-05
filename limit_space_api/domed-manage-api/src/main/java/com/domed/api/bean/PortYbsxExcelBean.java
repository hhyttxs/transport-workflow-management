package com.domed.api.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * TODO
 *
 * @Description
 * @Author lirong
 * @Date 2023/3/1 16:48
 **/
public class PortYbsxExcelBean {
    /**
     * name 指定导出excel是生成的列名
     * orderNum可指定导出的该属性对应的所在列的位置
     * width设置单元格宽度
     * type设置导出类型 1是文本 2是图片 3是函数 10数字 默认是文本
     */
    @Excel(name = "序号", orderNum = "1", type = 1, width = 10)
    private String xh;
    @Excel(name = "任务名称", orderNum = "2", type = 1, width = 20)
    private String misName;
    @Excel(name = "任务类型", orderNum = "3", type = 1, width = 20)
    private String misType;
    @Excel(name = "完成时限", orderNum = "4", type = 1, width = 20)
    private String clearTimelimit;
    @Excel(name = "责任部门", orderNum = "5", type = 1, width = 20)
    private String sector;
    @Excel(name = "发布人", orderNum = "6", type = 1, width = 20)
    private String adder;
    @Excel(name = "发布时间", orderNum = "7", type = 1, width = 40)
    private String addTime;
    @Excel(name = "阅读状态", orderNum = "8", type = 1, width = 40)
    private String readState;
    @Excel(name = "任务状态", orderNum = "9", type = 1, width = 30)
    private String misState;

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getMisName() {
        return misName;
    }

    public void setMisName(String misName) {
        this.misName = misName;
    }

    public String getMisType() {
        return misType;
    }

    public void setMisType(String misType) {
        this.misType = misType;
    }

    public String getClearTimelimit() {
        return clearTimelimit;
    }

    public void setClearTimelimit(String clearTimelimit) {
        this.clearTimelimit = clearTimelimit;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getAdder() {
        return adder;
    }

    public void setAdder(String adder) {
        this.adder = adder;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getReadState() {
        return readState;
    }

    public void setReadState(String readState) {
        this.readState = readState;
    }

    public String getMisState() {
        return misState;
    }

    public void setMisState(String misState) {
        this.misState = misState;
    }

}
