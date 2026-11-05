package com.domed.util.entity;

import com.domed.util.EO;
import lombok.Data;


/* 
 *@Description 查询条件
 *@Author 高杨
 *@Date 2019/12/11 10:16
 *@Param 
 *@return 
**/
@Data
public class Exp {

    private String lhs;
    private EO entityOperator;
    private Object rhs;

    protected Exp() {}

    public Exp(String lhs, EO entityOperator, Object rhs) {
        if (lhs == null) {
            throw new IllegalArgumentException("The field name cannot be null");
        }
        if (entityOperator == null) {
            throw new IllegalArgumentException("The operator argument cannot be null");
        }

        this.lhs = lhs;
        this.entityOperator = entityOperator;
        this.rhs = rhs;
    }

    public String makeWhereString() {
        StringBuffer whereStringBuffer = new StringBuffer();
        whereStringBuffer.append(lhs);
        if (this.getRhs() == null) {
            if (EO.NOT_EQUAL.equals(this.getEntityOperator())) {
                whereStringBuffer.append(" IS NOT NULL ");
            } else {
                whereStringBuffer.append(" IS NULL ");
            }
        }else {
            whereStringBuffer.append(" " + this.getEntityOperator() + " " + rhs);
        }
        return whereStringBuffer.toString();
    }
}
