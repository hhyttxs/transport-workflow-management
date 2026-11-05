package com.domed.api.controller.generateCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName CreateDaoService
 * @Description
 * @Author lirong
 * @Date 2022/7/28 13:49
 **/
public class CreateDaoService {

    //测试类
    public static void main(String[] args) throws Exception {
        Map<String, String> tableNmaeMap = getTableName();
        for (String key : tableNmaeMap.keySet()) {
//            List<Map<String, String>> tableCon = getColInfo(key);
            String daoPath = "D:\\transport_project\\smart_customs\\limit_space_api\\domed-foundation\\src\\main\\java\\com\\domed\\dao\\smartCustoms\\";
            createDao(daoPath, key);
//            String servicePath = "D:\\oldComputer\\myproject\\blog\\exchange_data\\src\\main\\java\\com\\domed\\traffic\\service\\";
//            createService(servicePath, key);
//            String serviceImplPath = "D:\\oldComputer\\myproject\\blog\\exchange_data\\src\\main\\java\\com\\domed\\traffic\\service\\impl\\";
//            createServiceImpl(serviceImplPath, key, tableCon);
        }
    }


    private static void createDao(String daoPath, String key) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("package com.domed.dao.smartCustoms;\r\n");
        sb.append("import com.domed.domain.smartCustoms." + key + ";\r\n ");
        sb.append("import org.springframework.stereotype.Repository;\r\n ");
        sb.append("import com.domed.core.dao.GenericDAO;\r\n ");
        sb.append("@Repository(\"" + key + "Dao\")\r\n ");
        sb.append("public class " + key + "Dao extends GenericDAO<" + key + "> {}\r\n ");
        insertEnvInfo(daoPath, key + "Dao", sb.toString());
    }

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

    public static void insertEnvInfo(String path, String name, String text) throws IOException {
        String slqPath = path + name + ".java";//指定生成文件目录
        try {
            File file = new File(slqPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter os = new PrintWriter(slqPath);
            os.write(text);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static List<Map<String, String>> getColInfo(String tableName) throws Exception {
//        Connection con = CreateEntity.getConnection();
//        String sql = "SELECT COL.COLUMN_NAME,COL.COMMENTS,CASE TAB.DATA_TYPE WHEN 'NUMBER' THEN decode(sign(NVL(TAB.DATA_SCALE,0)),0,'NUMBER','FLOAT') ELSE TAB.DATA_TYPE END AS DATA_TYPE,DECODE(TAB.DATA_TYPE,'NUMBER',TAB.DATA_PRECISION+TAB.DATA_SCALE,TAB.DATA_LENGTH) AS DATA_LENGTH, TT.CONSTRAINT_TYPE FROM USER_COL_COMMENTS COL,USER_TAB_COLUMNS TAB LEFT JOIN (SELECT AU.CONSTRAINT_TYPE,AU.TABLE_NAME,CU.COLUMN_NAME FROM USER_CONS_COLUMNS CU,USER_CONSTRAINTS AU WHERE CU.CONSTRAINT_NAME=AU.CONSTRAINT_NAME AND AU.CONSTRAINT_TYPE='P' AND AU.TABLE_NAME='" + tableName + "') TT ON TT.TABLE_NAME=TAB.TABLE_NAME AND TAB.COLUMN_NAME=TT.COLUMN_NAME WHERE COL.TABLE_NAME = '" + tableName + "' AND TAB.TABLE_NAME=COL.TABLE_NAME AND TAB.COLUMN_NAME=COL.COLUMN_NAME";
//        PreparedStatement ps = con.prepareStatement(sql);
//        ResultSet rs = ps.executeQuery();
//        List<Map<String, String>> infos = new ArrayList<>();
//        while (rs.next()) {
//            String colNm = rs.getString(1);
//            String dataType = rs.getString(3);
//            Map<String, String> map = new HashMap<>();
//            map.put("colNm", colNm);
//            map.put("dataType", dataType);
//            infos.add(map);
//        }
////        ps.close();
////        con.close();
//        return infos;
//    }

    public static Map getTableName() {
        Map map = new HashMap();
        map.put("HG_PJGL_PRINT_LOG", "1");
        return map;
    }
}
