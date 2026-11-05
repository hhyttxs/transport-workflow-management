package com.domed.api.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class AjqkExcelBean {
    /**
     * name 指定导出excel是生成的列名
     * orderNum可指定导出的该属性对应的所在列的位置
     * width设置单元格宽度
     * type设置导出类型 1是文本 2是图片 3是函数 10数字 默认是文本
     */
    @Excel(name = "序号", orderNum = "1", type = 1, width = 10)
    private String bh;
    @Excel(name = "人员", orderNum = "2", type = 1, width = 10)
    private String ry;
    @Excel(name = "单位", orderNum = "3", type = 1, width = 20)
    private String dw;
    @Excel(name = "职务", orderNum = "4", type = 1, width = 20)
    private String zw;
    @Excel(name = "主要案情", orderNum = "5", type = 1, width = 40)
    private String zyaq;
    @Excel(name = "立案时间", orderNum = "6", type = 1, width = 30)
    private String lasj;
    @Excel(name = "第一种形态", orderNum = "7", type = 1, width = 30)
    private String dyzxt;
    @Excel(name = "党纪处分", orderNum = "8", type = 1, width = 30)
    private String djcf;
    @Excel(name = "处分", orderNum = "9", type = 1, width = 30)
    private String cf;
    @Excel(name = "组织处理", orderNum = "10", type = 1, width = 30)
    private String zzcl;
    @Excel(name = "违纪类别", orderNum = "11", type = 1, width = 30)
    private String wjlb;
    @Excel(name = "违纪类别备注", orderNum = "12", type = 1, width = 30)
    private String wjlbbz;
    @Excel(name = "案发领域", orderNum = "13", type = 1, width = 30)
    private String afly;
    @Excel(name = "案发领域备注", orderNum = "14", type = 1, width = 30)
    private String aflybz;
    @Excel(name = "违反八项规定", orderNum = "15", type = 1, width = 30)
    private String wfbxgd;
    @Excel(name = "首次违纪违法时间", orderNum = "16", type = 1, width = 30)
    private String scwjwfsj;
    @Excel(name = "涉案财物", orderNum = "17", type = 1, width = 30)
    private String sacw;
    @Excel(name = "是否自主查发", orderNum = "18", type = 1, width = 30)
    private String sfzzcf;
    @Excel(name = "一案双查", orderNum = "19", type = 1, width = 30)
    private String yasc;
    @Excel(name = "是否年轻干部", orderNum = "20", type = 1, width = 30)
    private String sfnqgb;
    @Excel(name = "四种形态", orderNum = "21", type = 1, width = 30)
    private String szxt;

}
