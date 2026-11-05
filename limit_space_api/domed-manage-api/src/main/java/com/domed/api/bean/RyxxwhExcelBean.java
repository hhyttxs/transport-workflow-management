package com.domed.api.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class RyxxwhExcelBean {
    /**
     * name 指定导出excel是生成的列名
     * orderNum可指定导出的该属性对应的所在列的位置
     * width设置单元格宽度
     * type设置导出类型 1是文本 2是图片 3是函数 10数字 默认是文本
     */
    @Excel(name = "姓名", orderNum = "1", type = 1, width = 10)
    private String name;
    @Excel(name = "胸牌号", orderNum = "2", type = 1, width = 20)
    private String xph;
    @Excel(name = "管理部门", orderNum = "4", type = 1, width = 20)
    private String glbmTxt;
    @Excel(name = "在编部门", orderNum = "8", type = 1, width = 40)
    private String zbbmTxt;
    @Excel(name = "职务、职级", orderNum = "9", type = 1, width = 30)
    private String zwzj;
    @Excel(name = "人员类型", orderNum = "9", type = 1, width = 30)
    private String rylx;
    @Excel(name = "增加时间", orderNum = "9", type = 1, width = 30)
    private String newUserTime;

}
