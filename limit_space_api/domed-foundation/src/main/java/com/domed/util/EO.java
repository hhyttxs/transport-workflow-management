/*
 * $Id: EntityOperator.java,v 1.9 2003/02/13 06:19:49 azeneski Exp $
 *
 *  Copyright (c) 2002 The Open For Business Project - www.ofbiz.org
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a
 *  copy of this software and associated documentation files (the "Software"),
 *  to deal in the Software without restriction, including without limitation
 *  the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *  and/or sell copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included
 *  in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 *  OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 *  CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 *  OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 *  THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.domed.util;


import java.util.Objects;

public class EO implements java.io.Serializable {

    public static final int ID_EQUALS = 1;
    public static final int ID_NOT_EQUAL = 2;
    public static final int ID_LESS_THAN = 3;
    public static final int ID_GREATER_THAN = 4;
    public static final int ID_LESS_THAN_EQUAL_TO = 5;
    public static final int ID_GREATER_THAN_EQUAL_TO = 6;
    public static final int ID_IN = 7;
    public static final int ID_BETWEEN = 8;
    public static final int ID_NOT = 9;
    public static final int ID_AND = 10;
    public static final int ID_OR = 11;
    public static final int ID_LIKE = 12;
    public static final int ID_LEFTJOIN = 13;

    public static final EO EQUALS = new EO(ID_EQUALS, "=");
    public static final EO NOT_EQUAL = new EO(ID_NOT_EQUAL, "<>");
    public static final EO LESS_THAN = new EO(ID_LESS_THAN, "<");
    public static final EO GREATER_THAN = new EO(ID_GREATER_THAN, ">");
    public static final EO LESS_THAN_EQUAL_TO = new EO(ID_LESS_THAN_EQUAL_TO, "<=");
    public static final EO GREATER_THAN_EQUAL_TO = new EO(ID_GREATER_THAN_EQUAL_TO, ">=");
    public static final EO IN = new EO(ID_IN, "IN");
    public static final EO BETWEEN = new EO(ID_BETWEEN, "BETWEEN");
    public static final EO NOT = new EO(ID_NOT, "NOT");
    public static final EO AND = new EO(ID_AND, "AND");
    public static final EO OR = new EO(ID_OR, "OR");
    public static final EO LIKE = new EO(ID_LIKE, "LIKE");
    public static final EO LEFTJOIN = new EO(ID_LEFTJOIN, "LEFT JOIN");

    private int idInt;
    private String codeString;

    public EO(int id, String code) {
        idInt = id;
        codeString = code;
    }

    public String getCode() {
        if (codeString == null) {
            return "null";
        }else {
            return codeString;
        }
    }

    public int getId() {
        return idInt;
    }

    public String toString() {
        return codeString;
    }

    public boolean equals(Object obj) {
        EO otherOper = (EO) obj;
        return this.idInt == otherOper.idInt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInt, codeString);
    }
}
