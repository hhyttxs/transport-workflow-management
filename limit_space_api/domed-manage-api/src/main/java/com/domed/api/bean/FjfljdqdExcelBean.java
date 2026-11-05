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
public class FjfljdqdExcelBean {
    /**
     * name 指定导出excel是生成的列名
     * orderNum可指定导出的该属性对应的所在列的位置
     * width设置单元格宽度
     * type设置导出类型 1是文本 2是图片 3是函数 10数字 默认是文本
     */
    @Excel(name = "编号", orderNum = "1", type = 1, width = 10)
    private String bh;
    @Excel(name = "发布年度", orderNum = "2", type = 1, width = 20)
    private String fbndText;
    @Excel(name = "监督事项", orderNum = "4", type = 1, width = 20)
    private String jdsx;
    @Excel(name = "清单发布对象", orderNum = "8", type = 1, width = 40)
    private String qdfbdx;
    @Excel(name = "有效期", orderNum = "9", type = 1, width = 30)
    private String yxqjz;

}
