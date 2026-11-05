package com.domed.api.controller.generateCode;

import javax.crypto.KeyGenerator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName CreateEntity
 * @Description TODO
 * @Author lirong
 * @Date 2019/5/24 0024 13:54
 * @Version 1.0
 */
public class CreateEntity {

    private static final String DB_URL = System.getenv("CODEGEN_DB_URL");

    private static final String DB_USER = System.getenv("CODEGEN_DB_USER");

    private static Connection conn = null;

    private static final byte[] encrypt = AESUtil.encrypt();

    //设定set方法的模版
    private static final StringBuffer GET_METHOD_TEMPLATE = new StringBuffer();

    //设定get方法的模版
    private static final StringBuffer SET_METHOD_TEMPLATE = new StringBuffer();

    //类的名称
    private static final StringBuffer CLASS_TEMPLATE = new StringBuffer();

    //测试类
    public static void main(String[] args) throws Exception {
        Map<String, String> tableNmaeMap = getTableName();
        for (String key : tableNmaeMap.keySet()) {
            String result = createClass(key);
            insertEnvInfo(key, result);
        }
    }

    public static void insertEnvInfo(String taleName, String sql) throws IOException {
        String slqPath = "D:\\transport_project\\smart_customs\\limit_space_api\\domed-foundation\\src\\main\\java\\com\\domed\\domain\\smartCustoms\\" + taleName.toUpperCase() + ".java";//指定生成文件目录
        try {
            File file = new File(slqPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter os = new PrintWriter(slqPath);
            os.write(sql);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            Class.forName("dm.jdbc.driver.DmDriver");

            byte[] decrypt = AESUtil.decrypt(encrypt, System.getenv("CODEGEN_ENCRYPTION_KEY"));
            String DB_PASSWORD = new String(decrypt, "utf-8");

            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            //创建Get方法的模版
            GET_METHOD_TEMPLATE.append("@Column(name=\"%s\")\r\n");
            GET_METHOD_TEMPLATE.append("public %s get%s() {\r\n");
            GET_METHOD_TEMPLATE.append("\treturn %s;\r\n");
            GET_METHOD_TEMPLATE.append("}");

            //设定set方法的模版
            SET_METHOD_TEMPLATE.append("public void set%s(%s %s) {\r\n");
            SET_METHOD_TEMPLATE.append("\tthis.%s=%s;\r\n");
            SET_METHOD_TEMPLATE.append("}");

            //方法体的生成
            CLASS_TEMPLATE.append("package com.domed.domain.smartCustoms;\r\n\r\n");
            CLASS_TEMPLATE.append("import javax.persistence.Column;\r\n");
            CLASS_TEMPLATE.append("import javax.persistence.Entity;\r\n");
            CLASS_TEMPLATE.append("import javax.persistence.Id;\r\n");
            CLASS_TEMPLATE.append("import javax.persistence.Table;\r\n");
            CLASS_TEMPLATE.append("import java.io.Serializable;\r\n\r\n");
            CLASS_TEMPLATE.append("import java.util.Date;\r\n\r\n");

            CLASS_TEMPLATE.append("@Entity\r\n");
            CLASS_TEMPLATE.append("@Table(name=\"%s\")\r\n");
            CLASS_TEMPLATE.append("public class %s implements Serializable{\r\n");
            CLASS_TEMPLATE.append("private static final long serialVersionUID = 1L;\r\n");
            CLASS_TEMPLATE.append("%s");
            CLASS_TEMPLATE.append("\r\n}");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String createClass(String tableName) throws Exception {

        StringBuffer sbBody = new StringBuffer();
        List<String> classBody = CreateEntity.createClassBody(tableName);
        for (String classInfo : classBody) {
            sbBody.append(classInfo);
            sbBody.append("\r\n");
        }

        return String.format(CLASS_TEMPLATE.toString(), tableName, tableName, sbBody.toString());
    }

    public static Map getTableName() {
        Map map = new HashMap();
        map.put("HG_H4AUSER", "1");
        return map;
    }

    /**
     * 生成class的内容
     *
     * @param tableName
     * @return
     * @throws Exception
     */
    public static List<String> createClassBody(String tableName) throws Exception {
        List<EntityInfo> infos = getEntityInfo(tableName);
        //类的方法体
        List<String> classBody = new ArrayList<String>();
        for (EntityInfo info : infos) {
            String comments = info.getComment();
            String dataType = info.getType();
            String filed = underline2Camel(info.getField());
            //当是String类型的
            String fieldResult = String.format("/** %s*/\r\nprivate %s %s;\r\n", comments, dataType, filed);
            classBody.add(fieldResult);
        }
        //setter和getter方法
        for (EntityInfo info : infos) {
            //生成get方法
            String strGetMethod = createGetterMethod(info);
            classBody.add(strGetMethod);

            //生成set方法
            String strSetMethod = createSetterMethod(info);
            classBody.add(strSetMethod);
        }
        return classBody;
    }

    /**
     * 生成get方法
     *
     * @param info
     * @return
     */
    private static String createGetterMethod(EntityInfo info) {
        String strGetMethod = String.format(
                GET_METHOD_TEMPLATE.toString(),
                info.getField(),//注解数据
                info.getType(),//返回的数据类型格式
                underline2Camel(info.getField(), false), //getUserName
                underline2Camel(info.getField()));//userName
        String Primary = info.getPrimary();
        if ("P".equals(Primary)) {
            strGetMethod = "@Id\r\n" + strGetMethod;
        }
        return strGetMethod;
    }

    /**
     * 生成set的方法
     *
     * @param info
     * @return
     */
    private static String createSetterMethod(EntityInfo info) {

        String strSetMethod = String.format(
                SET_METHOD_TEMPLATE.toString(), //模版
                underline2Camel(info.getField(), false), //大驼峰 setUserName
                info.getType(),//数据类型
                underline2Camel(info.getField()), //小驼峰 userName
                underline2Camel(info.getField()), //小驼峰 userName
                underline2Camel(info.getField()) //小驼峰 userName
        );
        return strSetMethod.toString();
    }

    /**
     * 根据传入的数据类型来字符的类型
     * 如果你是其他的数据库，可以根据自己的需求来更改这一块
     *
     * @param dataType oracle字段的类型
     * @param len      字符的长度
     * @return
     */
    private static String getFieldType(String dataType, int len) {
        String fieldType = "";
        //当是String类型的
        switch (dataType) {
            case "CHAR":
                fieldType = "String";
                break;
            case "VARCHAR2":
                fieldType = "String";
                break;
            case "NVARCHAR2":
                fieldType = "String";
                break;
            case "BLOB":
                fieldType = "String";
                break;
            case "CLOB":
                fieldType = "String";
                break;
            case "NCLOB":
                fieldType = "String";
                break;
            case "DATE":
                fieldType = "Date";
                break;
            case "TIMESTAMP":
                fieldType = "Date";
                break;
            case "NUMBER":
                if (len == 0) {
                    fieldType = "Long";
                } else {
                    fieldType = len < 12 ? "Integer" : "Long";
                }
                break;
            case "FLOAT":
                fieldType = len < 12 ? "Float" : "Double";
                break;
        }
        return fieldType;
    }

    /**
     * 获取连接
     *
     * @return
     */
    public static Connection getConnection() {

        try {
            if (conn == null) {
                Class.forName("oracle.jdbc.driver.OracleDriver");

                byte[] decrypt = AESUtil.decrypt(encrypt, System.getenv("CODEGEN_ENCRYPTION_KEY"));
                String DB_PASSWORD = new String(decrypt, "utf-8");

                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            }
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取实体类的字段信息
     *
     * @param tableName
     * @return
     * @throws Exception
     */
    public static List<EntityInfo> getEntityInfo(String tableName){
        Connection con = CreateEntity.getConnection();
        //查询USER_COL_COMMENTS 和USER_TAB_COLUMNS 两个表，查询出字段信息
        String sql = "SELECT COL.COLUMN_NAME,COL.COMMENTS,CASE TAB.DATA_TYPE WHEN 'NUMBER' THEN decode(sign(NVL(TAB.DATA_SCALE,0)),0,'NUMBER','FLOAT') ELSE TAB.DATA_TYPE END AS DATA_TYPE,DECODE(TAB.DATA_TYPE,'NUMBER',TAB.DATA_PRECISION+TAB.DATA_SCALE,TAB.DATA_LENGTH) AS DATA_LENGTH, TT.CONSTRAINT_TYPE FROM USER_COL_COMMENTS COL,USER_TAB_COLUMNS TAB LEFT JOIN (SELECT AU.CONSTRAINT_TYPE,AU.TABLE_NAME,CU.COLUMN_NAME FROM USER_CONS_COLUMNS CU,USER_CONSTRAINTS AU WHERE CU.CONSTRAINT_NAME=AU.CONSTRAINT_NAME AND AU.CONSTRAINT_TYPE='P' AND AU.TABLE_NAME='" + tableName + "') TT ON TT.TABLE_NAME=TAB.TABLE_NAME AND TAB.COLUMN_NAME=TT.COLUMN_NAME WHERE COL.TABLE_NAME = '" + tableName + "' AND TAB.TABLE_NAME=COL.TABLE_NAME AND TAB.COLUMN_NAME=COL.COLUMN_NAME";
        PreparedStatement ps = null;
        ResultSet rs = null;
        //将查询的信息都放到集合里面，然后回
        List<EntityInfo> infos = new ArrayList<EntityInfo>();
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (true) {
                if (!rs.next()) break;
                //字段名次
                String colNm = rs.getString(1);
                //备注
                String comments = rs.getString(2);
                //oracle 数据类型
                String dataType = rs.getString(3);
                //oralce 数据长度
                Integer len = rs.getInt(4);
                String primary = rs.getString(5);
                //获取对应的java苏汇聚类型
                String fieldType = getFieldType(dataType, len);

                infos.add(new EntityInfo(colNm, comments, fieldType, len, primary));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return infos;
    }

    /**
     * 下划线转驼峰法(默认小驼峰)
     *
     * @param line       源字符串
     * @param smallCamel 大小驼峰,是否为小驼峰(驼峰，第一个字符是大写还是小写)
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line, boolean... smallCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);
        //匹配正则表达式
        while (matcher.find()) {
            String word = matcher.group();
            //当是true 或则是空的情况
            if ((smallCamel.length == 0 || smallCamel[0]) && matcher.start() == 0) {
                sb.append(Character.toLowerCase(word.charAt(0)));
            } else {
                sb.append(Character.toUpperCase(word.charAt(0)));
            }

            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰法转下划线
     *
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String line) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase()
                .concat(line.substring(1));
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        return sb.toString();
    }

    /**
     * 用于存储实体类字段信息
     *
     * @author zhangrw
     */
    static class EntityInfo {
        private String field;
        private String comment;
        private String type;
        private Integer length;
        private String primary;

        /**
         *
         */
        public EntityInfo() {
            super();
        }

        /**
         * @param field
         * @param comment
         * @param type
         * @param length
         */
        public EntityInfo(String field, String comment, String type,
                          Integer length, String primary) {
            super();
            this.field = field;
            this.comment = comment;
            this.type = type;
            this.length = length;
            this.primary = primary;
        }


        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getLength() {
            return length;
        }

        public void setLength(Integer length) {
            this.length = length;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getPrimary() {
            return primary;
        }

        public void setPrimary(String primary) {
            this.primary = primary;
        }
    }


//    public static String creatInsertSql(String tableName) throws Exception {
//        List<EntityInfo> infos = getEntityInfo(tableName);
//        return "";
//    }


}
