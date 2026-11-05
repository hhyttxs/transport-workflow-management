package com.domed.api.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class JdjcjgExcelBean {
    /**
     * name 指定导出excel是生成的列名
     * orderNum可指定导出的该属性对应的所在列的位置
     * width设置单元格宽度
     * type设置导出类型 1是文本 2是图片 3是函数 10数字 默认是文本
     */
    @Excel(name = "监督计划名称", orderNum = "1", type = 1, width = 10)
    private String jdjhmc;
    @Excel(name = "监督计划制定时间", orderNum = "2", type = 1, width = 20)
    private String zdsj;
    @Excel(name = "具体原因", orderNum = "4", type = 1, width = 20)
    private String jtyy;
    @Excel(name = "监督事项拟完成时间", orderNum = "8", type = 1, width = 40)
    private String jdsxnwcsj;
    @Excel(name = "监督责任单位", orderNum = "9", type = 1, width = 30)
    private String jdzrdw;

}
