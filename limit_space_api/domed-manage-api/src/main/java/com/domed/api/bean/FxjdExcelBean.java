package com.domed.api.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * TODO
 *
 * @Description
 * @Author lirong
 * @Date 2023/3/1 16:48
 **/
@Data
public class FxjdExcelBean {
    /**
     * name 指定导出excel是生成的列名
     * orderNum可指定导出的该属性对应的所在列的位置
     * width设置单元格宽度
     * type设置导出类型 1是文本 2是图片 3是函数 10数字 默认是文本
     */
    @Excel(name = "业务领域", orderNum = "1", type = 1, width = 10)
    private String ywly;
    @Excel(name = "业务类型", orderNum = "2", type = 1, width = 20)
    private String ywlx;
    @Excel(name = "节点环节", orderNum = "4", type = 1, width = 20)
    private String jdhj;
    @Excel(name = "风险等级", orderNum = "8", type = 1, width = 40)
    private String fxdj;
    @Excel(name = "是否使用", orderNum = "9", type = 1, width = 30)
    private String sfsy;

}
