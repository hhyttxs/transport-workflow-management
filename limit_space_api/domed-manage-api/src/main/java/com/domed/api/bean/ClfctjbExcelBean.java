package com.domed.api.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class ClfctjbExcelBean {
    /**
     * name 指定导出excel是生成的列名
     * orderNum可指定导出的该属性对应的所在列的位置
     * width设置单元格宽度
     * type设置导出类型 1是文本 2是图片 3是函数 10数字 默认是文本
     */
    @Excel(name = "年度", orderNum = "1", type = 1, width = 10)
    private String nd;
    @Excel(name = "节假日", orderNum = "2", type = 1, width = 20)
    private String jjr;
    @Excel(name = "填报单位", orderNum = "4", type = 1, width = 20)
    private String deptName;
    @Excel(name = "单位名称", orderNum = "8", type = 1, width = 40)
    private String dwmc;
    @Excel(name = "总数量", orderNum = "9", type = 1, width = 30)
    private String zsl;
    @Excel(name = "状态", orderNum = "9", type = 1, width = 30)
    private String stateHz;
    @Excel(name = "录入时间", orderNum = "9", type = 1, width = 30)
    private String addTime;

}
