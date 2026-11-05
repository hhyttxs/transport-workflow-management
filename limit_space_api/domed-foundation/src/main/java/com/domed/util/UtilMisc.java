/*
 * $Id: UtilMisc.java,v 1.22 2003/03/18 18:38:55 jonesde Exp $
 *
 *  Copyright (c) 2001, 2002 The Open For Business Project - www.ofbiz.org
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

import com.domed.util.entity.Exp;

import java.util.*;

/**
 * UtilMisc - Misc Utility Functions
 *
 * @author     <a href="mailto:jonesde@ofbiz.org">David E. Jones</a>
 * @author     <a href="mailto:jaz@ofbiz.org">Andy Zeneski</a>
 * @version    $Revision: 1.22 $
 * @since      2.0
 */
public class UtilMisc {

    /**
     * Get an iterator from a collection, returning null if collection is null
     * @param col The collection to be turned in to an iterator
     * @return The resulting Iterator
     */
    public static Iterator toIterator(Collection col) {
        if (col == null)
            return null;
        else
            return col.iterator();
    }

    /**
     * Create a map from passed nameX, valueX parameters
     * @return The resulting Map
     */
    public static Map toMap(String name1, Object value1) {
        return new SimpleMap(name1, value1);

        /* Map fields = new HashMap();
         fields.put(name1, value1);
         return fields;*/
    }

    /**
     * Create a map from passed nameX, valueX parameters
     * @return The resulting Map
     */
    public static Map toMap(String name1, Object value1, String name2, Object value2) {
        return new SimpleMap(name1, value1, name2, value2);

        /* Map fields = new HashMap();
         fields.put(name1, value1);
         fields.put(name2, value2);
         return fields;*/
    }

    /**
     * Create a map from passed nameX, valueX parameters
     * @return The resulting Map
     */
    public static Map toMap(String name1, Object value1, String name2, Object value2, String name3, Object value3) {
        return new SimpleMap(name1, value1, name2, value2, name3, value3);

        /* Map fields = new HashMap();
         fields.put(name1, value1);
         fields.put(name2, value2);
         fields.put(name3, value3);
         return fields;*/
    }

    /**
     * Create a map from passed nameX, valueX parameters
     * @return The resulting Map
     */
    public static Map toMap(String name1, Object value1, String name2, Object value2, String name3,
        Object value3, String name4, Object value4) {
        return new SimpleMap(name1, value1, name2, value2, name3, value3, name4, value4);

        /* Map fields = new HashMap();
         fields.put(name1, value1);
         fields.put(name2, value2);
         fields.put(name3, value3);
         fields.put(name4, value4);
         return fields;*/
    }

    /**
     * Create a map from passed nameX, valueX parameters
     * @return The resulting Map
     */
    public static Map toMap(String name1, Object value1, String name2, Object value2, String name3, Object value3,
        String name4, Object value4, String name5, Object value5) {
        Map fields = new HashMap();

        fields.put(name1, value1);
        fields.put(name2, value2);
        fields.put(name3, value3);
        fields.put(name4, value4);
        fields.put(name5, value5);
        return fields;
    }

    /**
     * Create a map from passed nameX, valueX parameters
     * @return The resulting Map
     */
    public static Map toMap(String name1, Object value1, String name2, Object value2, String name3, Object value3,
        String name4, Object value4, String name5, Object value5, String name6, Object value6) {
        Map fields = new HashMap();

        fields.put(name1, value1);
        fields.put(name2, value2);
        fields.put(name3, value3);
        fields.put(name4, value4);
        fields.put(name5, value5);
        fields.put(name6, value6);
        return fields;
    }


    /**
     * Create a list from passed objX parameters
     * @return The resulting List
     */
    public static List toList(Object obj1) {
        List list = new ArrayList(1);

        list.add(obj1);
        return list;
    }

    /**
     * Create a list from passed objX parameters
     * @return The resulting List
     */
    public static List toList(Object obj1, Object obj2) {
        List list = new ArrayList(2);

        list.add(obj1);
        list.add(obj2);
        return list;
    }

    /**
     * Create a list from passed objX parameters
     * @return The resulting List
     */
    public static List toList(Object obj1, Object obj2, Object obj3) {
        List list = new ArrayList(3);

        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        return list;
    }

    /**
     * Create a list from passed objX parameters
     * @return The resulting List
     */
    public static List toList(Object obj1, Object obj2, Object obj3, Object obj4) {
        List list = new ArrayList(4);

        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);
        return list;
    }

    /**
     * Create a list from passed objX parameters
     * @return The resulting List
     */
    public static List toList(Object obj1, Object obj2, Object obj3, Object obj4, Object obj5) {
        List list = new ArrayList(5);

        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);
        list.add(obj5);
        return list;
    }

    /**
     * Create a list from passed objX parameters
     * @return The resulting List
     */
    public static List toList(Object obj1, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        List list = new ArrayList(6);

        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);
        list.add(obj5);
        list.add(obj6);
        return list;
    }

    public static List toList(Object obj1, Object obj2, Object obj3, Object obj4,
                              Object obj5, Object obj6, Object obj7) {
        List list = new ArrayList(7);

        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);
        list.add(obj5);
        list.add(obj6);
        list.add(obj7);
        return list;
    }

    public static List toList(Object obj1, Object obj2, Object obj3, Object obj4,
                              Object obj5, Object obj6, Object obj7, Object obj8) {
        List list = new ArrayList(8);

        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);
        list.add(obj5);
        list.add(obj6);
        list.add(obj7);
        list.add(obj8);
        return list;
    }

    public static List toList(Object obj1, Object obj2, Object obj3, Object obj4,
                              Object obj5, Object obj6, Object obj7, Object obj8,
                              Object obj9) {
        List list = new ArrayList(9);

        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);
        list.add(obj5);
        list.add(obj6);
        list.add(obj7);
        list.add(obj8);
        list.add(obj9);
        return list;
    }

    public static List toList(Object obj1, Object obj2, Object obj3, Object obj4,
                              Object obj5, Object obj6, Object obj7, Object obj8,
                              Object obj9, Object obj10) {
        List list = new ArrayList(10);

        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);
        list.add(obj5);
        list.add(obj6);
        list.add(obj7);
        list.add(obj8);
        list.add(obj9);
        list.add(obj10);
        return list;
    }

    public static List toList(Object obj1, Object obj2, Object obj3, Object obj4,
                              Object obj5, Object obj6, Object obj7, Object obj8,
                              Object obj9, Object obj10, Object obj11) {
        List list = new ArrayList(11);

        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);
        list.add(obj5);
        list.add(obj6);
        list.add(obj7);
        list.add(obj8);
        list.add(obj9);
        list.add(obj10);
        list.add(obj11);
        return list;
    }

    public static List toList(Object obj1, Object obj2, Object obj3, Object obj4,
                              Object obj5, Object obj6, Object obj7, Object obj8,
                              Object obj9, Object obj10, Object obj11, Object obj12) {
        List list = new ArrayList(12);

        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);
        list.add(obj5);
        list.add(obj6);
        list.add(obj7);
        list.add(obj8);
        list.add(obj9);
        list.add(obj10);
        list.add(obj11);
        list.add(obj12);
        return list;
    }

    public static List toList(Object obj1, Object obj2, Object obj3, Object obj4,
                              Object obj5, Object obj6, Object obj7, Object obj8,
                              Object obj9, Object obj10, Object obj11, Object obj12,
                              Object obj13) {
        List list = new ArrayList(13);

        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);
        list.add(obj5);
        list.add(obj6);
        list.add(obj7);
        list.add(obj8);
        list.add(obj9);
        list.add(obj10);
        list.add(obj11);
        list.add(obj12);
        list.add(obj13);
        return list;
    }

    public static List toList(Object obj1, Object obj2, Object obj3, Object obj4,
                              Object obj5, Object obj6, Object obj7, Object obj8,
                              Object obj9, Object obj10, Object obj11, Object obj12,
                              Object obj13, Object obj14) {
        List list = new ArrayList(14);

        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);
        list.add(obj5);
        list.add(obj6);
        list.add(obj7);
        list.add(obj8);
        list.add(obj9);
        list.add(obj10);
        list.add(obj11);
        list.add(obj12);
        list.add(obj13);
        list.add(obj14);
        return list;
    }

    public static List toList(Object obj1, Object obj2, Object obj3, Object obj4,
                              Object obj5, Object obj6, Object obj7, Object obj8,
                              Object obj9, Object obj10, Object obj11, Object obj12,
                              Object obj13, Object obj14, Object obj15) {
        List list = new ArrayList(15);

        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);
        list.add(obj5);
        list.add(obj6);
        list.add(obj7);
        list.add(obj8);
        list.add(obj9);
        list.add(obj10);
        list.add(obj11);
        list.add(obj12);
        list.add(obj13);
        list.add(obj14);
        list.add(obj15);
        return list;
    }

    public static List toList(Object obj1, Object obj2, Object obj3, Object obj4,
                              Object obj5, Object obj6, Object obj7, Object obj8,
                              Object obj9, Object obj10, Object obj11, Object obj12,
                              Object obj13, Object obj14, Object obj15, Object obj16) {
        List list = new ArrayList(16);

        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);
        list.add(obj5);
        list.add(obj6);
        list.add(obj7);
        list.add(obj8);
        list.add(obj9);
        list.add(obj10);
        list.add(obj11);
        list.add(obj12);
        list.add(obj13);
        list.add(obj14);
        list.add(obj15);
        list.add(obj16);
        return list;
    }

    public static List toList(Object obj1, Object obj2, Object obj3, Object obj4,
                              Object obj5, Object obj6, Object obj7, Object obj8,
                              Object obj9, Object obj10, Object obj11, Object obj12,
                              Object obj13, Object obj14, Object obj15, Object obj16,
                              Object obj17) {
        List list = new ArrayList(17);

        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);
        list.add(obj5);
        list.add(obj6);
        list.add(obj7);
        list.add(obj8);
        list.add(obj9);
        list.add(obj10);
        list.add(obj11);
        list.add(obj12);
        list.add(obj13);
        list.add(obj14);
        list.add(obj15);
        list.add(obj16);
        list.add(obj17);
        return list;
    }

    public static List toList(Object obj1, Object obj2, Object obj3, Object obj4,
                              Object obj5, Object obj6, Object obj7, Object obj8,
                              Object obj9, Object obj10, Object obj11, Object obj12,
                              Object obj13, Object obj14, Object obj15, Object obj16,
                              Object obj17, Object obj18) {
        List list = new ArrayList(18);

        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);
        list.add(obj5);
        list.add(obj6);
        list.add(obj7);
        list.add(obj8);
        list.add(obj9);
        list.add(obj10);
        list.add(obj11);
        list.add(obj12);
        list.add(obj13);
        list.add(obj14);
        list.add(obj15);
        list.add(obj16);
        list.add(obj17);
        list.add(obj18);
        return list;
    }

    public static List toList(Object obj1, Object obj2, Object obj3, Object obj4,
                              Object obj5, Object obj6, Object obj7, Object obj8,
                              Object obj9, Object obj10, Object obj11, Object obj12,
                              Object obj13, Object obj14, Object obj15, Object obj16,
                              Object obj17, Object obj18, Object obj19) {
        List list = new ArrayList(19);

        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);
        list.add(obj5);
        list.add(obj6);
        list.add(obj7);
        list.add(obj8);
        list.add(obj9);
        list.add(obj10);
        list.add(obj11);
        list.add(obj12);
        list.add(obj13);
        list.add(obj14);
        list.add(obj15);
        list.add(obj16);
        list.add(obj17);
        list.add(obj18);
        list.add(obj19);
        return list;
    }

    public static List toList(Object obj1, Object obj2, Object obj3, Object obj4,
                              Object obj5, Object obj6, Object obj7, Object obj8,
                              Object obj9, Object obj10, Object obj11, Object obj12,
                              Object obj13, Object obj14, Object obj15, Object obj16,
                              Object obj17, Object obj18, Object obj19, Object obj20) {
        List list = new ArrayList(20);

        list.add(obj1);
        list.add(obj2);
        list.add(obj3);
        list.add(obj4);
        list.add(obj5);
        list.add(obj6);
        list.add(obj7);
        list.add(obj8);
        list.add(obj9);
        list.add(obj10);
        list.add(obj11);
        list.add(obj12);
        list.add(obj13);
        list.add(obj14);
        list.add(obj15);
        list.add(obj16);
        list.add(obj17);
        list.add(obj18);
        list.add(obj19);
        list.add(obj20);
        return list;
    }

    public static List toList(Collection collection) {
        if (collection == null) return null;
        if (collection instanceof List) {
            return (List) collection;
        } else {
            return new ArrayList(collection);
        }
    }


    /** This is meant to be very quick to create and use for small sized maps, perfect for how we usually use UtilMisc.toMap */
    protected static class SimpleMap implements Map, java.io.Serializable {
        protected Map realMapIfNeeded = null;

        String[] names;
        Object[] listF;

        public SimpleMap() {
            names = new String[0];
            listF = new Object[0];
        }

        public SimpleMap(String name1, Object value1) {
            names = new String[1];
            listF = new Object[1];
            this.names[0] = name1;
            this.listF[0] = value1;
        }

        public SimpleMap(String name1, Object value1, String name2, Object value2) {
            names = new String[2];
            listF = new Object[2];
            this.names[0] = name1;
            this.listF[0] = value1;
            this.names[1] = name2;
            this.listF[1] = value2;
        }

        public SimpleMap(String name1, Object value1, String name2, Object value2, String name3, Object value3) {
            names = new String[3];
            listF = new Object[3];
            this.names[0] = name1;
            this.listF[0] = value1;
            this.names[1] = name2;
            this.listF[1] = value2;
            this.names[2] = name3;
            this.listF[2] = value3;
        }

        public SimpleMap(String name1, Object value1, String name2, Object value2, String name3, Object value3, String name4, Object value4) {
            names = new String[4];
            listF = new Object[4];
            this.names[0] = name1;
            this.listF[0] = value1;
            this.names[1] = name2;
            this.listF[1] = value2;
            this.names[2] = name3;
            this.listF[2] = value3;
            this.names[3] = name4;
            this.listF[3] = value4;
        }

        protected void makeRealMap() {
            realMapIfNeeded = new HashMap();
            for (int i = 0; i < names.length; i++) {
                realMapIfNeeded.put(names[i], listF[i]);
            }
            this.names = null;
            this.listF = null;
        }

        public void clear() {
            if (realMapIfNeeded != null) {
                realMapIfNeeded.clear();
            } else {
                realMapIfNeeded = new HashMap();
                names = null;
                listF = null;
            }
        }

        public boolean containsKey(Object obj) {
            if (realMapIfNeeded != null) {
                return realMapIfNeeded.containsKey(obj);
            } else {
                for (int i = 0; i < names.length; i++) {
                    if (obj == null && names[i] == null) return true;
                    if (names[i] != null && names[i].equals(obj)) return true;
                }
                return false;
            }
        }

        public boolean containsValue(Object obj) {
            if (realMapIfNeeded != null) {
                return realMapIfNeeded.containsValue(obj);
            } else {
                for (int i = 0; i < names.length; i++) {
                    if (obj == null && listF[i] == null) return true;
                    if (listF[i] != null && listF[i].equals(obj)) return true;
                }
                return false;
            }
        }

        public Set entrySet() {
            if (realMapIfNeeded != null) {
                return realMapIfNeeded.entrySet();
            } else {
                this.makeRealMap();
                return realMapIfNeeded.entrySet();
            }
        }

        public Object get(Object obj) {
            if (realMapIfNeeded != null) {
                return realMapIfNeeded.get(obj);
            } else {
                for (int i = 0; i < names.length; i++) {
                    if (obj == null && names[i] == null) return listF[i];
                    if (names[i] != null && names[i].equals(obj)) return listF[i];
                }
                return null;
            }
        }

        public boolean isEmpty() {
            if (realMapIfNeeded != null) {
                return realMapIfNeeded.isEmpty();
            } else {
                if (this.names.length == 0) return true;
                return false;
            }
        }

        public Set keySet() {
            if (realMapIfNeeded != null) {
                return realMapIfNeeded.keySet();
            } else {
                this.makeRealMap();
                return realMapIfNeeded.keySet();
            }
        }

        public Object put(Object obj, Object obj1) {
            if (realMapIfNeeded != null) {
                return realMapIfNeeded.put(obj, obj1);
            } else {
                this.makeRealMap();
                return realMapIfNeeded.put(obj, obj1);
            }
        }

        public void putAll(Map map) {
            if (realMapIfNeeded != null) {
                realMapIfNeeded.putAll(map);
            } else {
                this.makeRealMap();
                realMapIfNeeded.putAll(map);
            }
        }

        public Object remove(Object obj) {
            if (realMapIfNeeded != null) {
                return realMapIfNeeded.remove(obj);
            } else {
                this.makeRealMap();
                return realMapIfNeeded.remove(obj);
            }
        }

        public int size() {
            if (realMapIfNeeded != null) {
                return realMapIfNeeded.size();
            } else {
                return this.names.length;
            }
        }

        public Collection values() {
            if (realMapIfNeeded != null) {
                return realMapIfNeeded.values();
            } else {
                this.makeRealMap();
                return realMapIfNeeded.values();
            }
        }

        public String toString() {
            if (realMapIfNeeded != null) {
                return realMapIfNeeded.toString();
            } else {
                StringBuffer outString = new StringBuffer("{");
                for (int i = 0; i < names.length; i++) {
                    if (i > 0) outString.append(',');
                    outString.append('{');
                    outString.append(names[i]);
                    outString.append(',');
                    outString.append(listF[i]);
                    outString.append('}');
                }
                outString.append('}');
                return outString.toString();
            }
        }

        public int hashCode() {
            if (realMapIfNeeded != null) {
                return realMapIfNeeded.hashCode();
            } else {
                int hashCode = 0;
                for (int i = 0; i < names.length; i++) {
                    //note that this calculation is done based on the calc specified in the Java java.util.Map interface
                    int tempNum = (names[i] == null   ? 0 : names[i].hashCode()) ^
                            (listF[i] == null ? 0 : listF[i].hashCode());
                    hashCode += tempNum;
                }
                return hashCode;
            }
        }

        public boolean equals(Object obj) {
            if (realMapIfNeeded != null) {
                return realMapIfNeeded.equals(obj);
            } else {
                Map mapObj = (Map) obj;

                //first check the size
                if (mapObj.size() != names.length) return false;

                //okay, same size, now check each entry
                for (int i = 0; i < names.length; i++) {
                    //first check the name
                    if (!mapObj.containsKey(names[i])) return false;

                    //if that passes, check the value
                    Object mapValue = mapObj.get(names[i]);
                    if (mapValue == null) {
                        if (listF[i] != null) return false;
                    } else {
                        if (!mapValue.equals(listF[i])) return false;
                    }
                }

                return true;
            }
        }
    }

    /*
     *@Description 直等方法封装
     *@Author 高杨
     *@Date 2019/12/11 17:57
     *@Param [lhs, rhs]
     *@return com.domed.util.entity.Exp
    **/
    public static Exp equalsExp(String lhs, Object rhs){
        return new Exp(lhs, EO.EQUALS, rhs);
    }

    public static Exp notEqualsExp(String lhs, Object rhs){
        return new Exp(lhs, EO.NOT_EQUAL, rhs);
    }

    /*
     *@Description in Map条件
     *@Author 高杨
     *@Date 2019/12/11 19:19
     *@Param [lhs, rhs]
     *@return com.domed.util.entity.Exp
     **/
    public static Exp inExp(String lhs, List<Map> rhsList,String keyName){
        StringBuffer sb = new StringBuffer();
        sb.append(" ( ");
        for (int i = 0; i < rhsList.size(); i++) {
            Map map = rhsList.get(i);
            if(i == 0){
                sb.append("'"+map.get(keyName)+"'");
            }else{
                sb.append(" , " +"'"+ map.get(keyName)+"'");
            }
        }
        sb.append(" ) ");
        return new Exp(lhs, EO.IN, sb.toString());
    }


    /*
     *@Description in String条件
     *@Author 高杨
     *@Date 2019/12/11 19:19
     *@Param [lhs, rhs]
     *@return com.domed.util.entity.Exp
     **/
    public static Exp inStrExp(String lhs, List<String> rhsList){
        StringBuffer sb = new StringBuffer();
        sb.append(" ( ");
        for (int i = 0; i < rhsList.size(); i++) {
            if(i == 0){
                sb.append("'"+ rhsList.get(i)+"'");
            }else{
                sb.append(" , " +"'"+ rhsList.get(i)+"'");
            }
        }
        sb.append(" ) ");
        return new Exp(lhs, EO.IN, sb.toString());
    }
}
