package local.vehicle.testing;

import com.domed.api.service.transportService.vehicleWorkflow.*;
import com.domed.common.result.Result;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.time.*;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;

/** Explicitly imported only by the isolated H2 executable; never scanned by the production reactor.
 * LOCAL_* tables are test adapters, not proposed Oracle schemas or production authentication stores.
 */
@RestController
@Transactional
public class LocalSystemEndpoints {
    @Autowired private JdbcTemplate jdbc;
    @Autowired private VehicleAccessPolicy access;
    private final ObjectMapper json = new ObjectMapper();
    private static final String[] FORM={"projectName","businessType","operator","enterpriseAddress","licenseIssueDate","socialCreditCode","legalPersonName","legalPersonIdNo","legalPersonPhone","economicType","transportTypes"};
    private static final String[] ENTITY={"sxmc","ywlx","jbr","qydz","yyzzfzrq","tyshxydm","frxm","frsfzh","frlxdh","jjlx","ysfs"};
    private VehicleActor applicant(){VehicleActor a=access.authenticate(null);need(a.applicant,403,"请使用申请人身份");return a;}
    private VehicleActor admin(){VehicleActor a=access.authenticate(null);need("LVTEST_LOCAL_ADMIN".equals(a.id),403,"当前身份无系统管理权限");return a;}
    static void need(boolean b,int code,String msg){if(!b)throw new VehicleWorkflowException(code,msg);}
    static String text(Map<String,Object>b,String key,int max){Object value=b.get(key);need(value==null||value instanceof String,422,"Invalid field: "+key);String s=value==null?"":((String)value).trim();need(s.length()<=max,422,"Field too long: "+key);return s;}
    private String required(Map<String,Object>b,String key,int max){String s=text(b,key,max);need(!s.isEmpty(),422,"Required: "+key);return s;}
    private String id(){return "LOCALDATA-"+UUID.randomUUID().toString();}
    private String encode(Object value){try{return json.writeValueAsString(value);}catch(Exception e){throw new VehicleWorkflowException(422,"Invalid form data");}}
    private Map<String,Object> decode(String s){try{return json.readValue(s,new TypeReference<Map<String,Object>>(){});}catch(Exception e){throw new VehicleWorkflowException(503,"Local fixture data unavailable");}}
    private int integer(Map<String,Object>b,String key,int fallback,int max){Object v=b.get(key);if(v==null)return fallback;try{int n=Integer.parseInt(v.toString());need(n>=1&&n<=max,422,"Invalid pagination");return n;}catch(NumberFormatException e){throw new VehicleWorkflowException(422,"Invalid pagination");}}
    private Map<String,Object> page(List<Map<String,Object>> all,Map<String,Object>b){int n=integer(b,"numberPage",10,1000),p=integer(b,"currentPage",1,100000);int start=Math.min(all.size(),(p-1)*n),end=Math.min(all.size(),start+n);Map<String,Object> result=new LinkedHashMap<>(),inner=new LinkedHashMap<>();inner.put("result",all.subList(start,end));result.put("data",inner);result.put("pageCount",all.size());result.put("totalPage",(all.size()+n-1)/n);return result;}
    private Map<String,Object> obj(Object...values){Map<String,Object> m=new LinkedHashMap<>();for(int i=0;i<values.length;i+=2)m.put(values[i].toString(),values[i+1]);return m;}

    @PostMapping("/dlhwjyxk/saveOrUpdateDlhwjyxk.json")
    public Result<Map<String,Object>> saveLicense(@RequestBody Map<String,Object>b){
        VehicleActor a=applicant();String gid=text(b,"gid",60),status=required(b,"status",1);
        need("0".equals(status)||"1".equals(status),422,"Only draft or local submission allowed");
        Map<String,Object> form=new LinkedHashMap<>();for(String field:FORM)form.put(field,text(b,field,field.equals("enterpriseAddress")?200:100));
        need("roadTransportLicense".equals(form.get("projectName"))&&"firstApply".equals(form.get("businessType")),422,"Unsupported license business");
        String date=(String)form.get("licenseIssueDate");if(!date.isEmpty())try{LocalDate.parse(date);}catch(Exception e){throw new VehicleWorkflowException(422,"Invalid license date");}
        Object vehicles=b.get("vehicleList");need(vehicles instanceof List&&((List<?>)vehicles).size()<=100,422,"Invalid vehicle list");
        for(Object v:(List<?>)vehicles){need(v instanceof Map,422,"Invalid vehicle");Map<String,Object> car=(Map<String,Object>)v;required(car,"brandModel",100);required(car,"vehicleType",50);required(car,"technicalLevel",20);Object q=car.get("quantity");need(q instanceof Number&&((Number)q).doubleValue()==((Number)q).intValue()&&((Number)q).intValue()>0&&((Number)q).intValue()<=10000,422,"Invalid vehicle quantity");}
        form.put("vehicleList",vehicles);need(encode(form).length()<=100000,422,"Application too large");
        if("1".equals(status)){
            for(String field:FORM)need(!((String)form.get(field)).isEmpty(),422,"Required: "+field);
            need(((String)form.get("socialCreditCode")).matches("[0-9A-Z]{18}"),422,"Invalid social credit code");
            need(((String)form.get("legalPersonIdNo")).matches("([0-9]{15}|[0-9]{17}[0-9Xx])"),422,"Invalid identity number");
            need(((String)form.get("legalPersonPhone")).matches("([0-9]{3,4}-?)?[0-9]{7,11}"),422,"Invalid telephone");
            need(Arrays.asList("stateOwned","collective","private","individual","jointStock","foreignFunded","hongKongMacaoTaiwan","other").contains(form.get("economicType")),422,"Invalid economic type");
            for(String t:((String)form.get("transportTypes")).split(","))need(Arrays.asList("generalFreight","largeObject","container","refrigerated","tank").contains(t),422,"Invalid transport type");
        }
        if(gid.isEmpty()){gid=id();jdbc.update("INSERT INTO LOCAL_LICENSE_APPLICATION(GID,OWNER_ID,STATUS,FORM_JSON,UPDATED_AT) VALUES (?,?,?,?,CURRENT_TIMESTAMP)",gid,a.id,status,encode(form));}
        else{
            List<Map<String,Object>> rows=jdbc.queryForList("SELECT OWNER_ID,STATUS FROM LOCAL_LICENSE_APPLICATION WHERE GID=? FOR UPDATE",gid);
            need(rows.size()==1,404,"Draft not found");need(a.id.equals(rows.get(0).get("OWNER_ID")),403,"Draft ownership mismatch");need("0".equals(rows.get(0).get("STATUS")),409,"Submitted application is read-only");
            jdbc.update("UPDATE LOCAL_LICENSE_APPLICATION SET STATUS=?,FORM_JSON=?,UPDATED_AT=CURRENT_TIMESTAMP WHERE GID=? AND OWNER_ID=? AND STATUS='0'",status,encode(form),gid,a.id);
        }
        return Result.success(obj("gid",gid,"status",status));
    }
    private Map<String,Object> licenseRow(Map<String,Object> row){Map<String,Object> form=decode((String)row.get("FORM_JSON")),out=obj("gid",row.get("GID"),"status",row.get("STATUS"),"vehicleList",form.get("vehicleList"));for(int i=0;i<FORM.length;i++)out.put(ENTITY[i],form.get(FORM[i]));return out;}
    @PostMapping("/dlhwjyxk/getDlhwjyxkInfoByGid.json")
    public Result<Map<String,Object>> license(@RequestBody Map<String,Object>b){VehicleActor a=applicant();List<Map<String,Object>> rows=jdbc.queryForList("SELECT * FROM LOCAL_LICENSE_APPLICATION WHERE GID=? AND OWNER_ID=?",required(b,"gid",60),a.id);need(rows.size()==1,404,"Draft not found for current applicant");return Result.success(licenseRow(rows.get(0)));}
    @PostMapping("/dlhwjyxk/queryDraftList.json")
    public Result<Map<String,Object>> drafts(@RequestBody Map<String,Object>b){VehicleActor a=applicant();String name=text(b,"name",100),sfzh=text(b,"sfzh",100);List<Map<String,Object>> all=new ArrayList<>();for(Map<String,Object> r:jdbc.queryForList("SELECT * FROM LOCAL_LICENSE_APPLICATION WHERE OWNER_ID=? AND STATUS='0' ORDER BY UPDATED_AT DESC,GID",a.id)){Map<String,Object> row=licenseRow(r);if(row.get("frxm").toString().contains(name)&&row.get("frsfzh").toString().contains(sfzh))all.add(row);}return Result.success(page(all,b));}

    @PostMapping("/yhwhlb/queryUserList.json")
    public Result<Map<String,Object>> users(@RequestBody Map<String,Object>b){admin();String code=text(b,"code",60),name=text(b,"name",100);List<Map<String,Object>> rows=new ArrayList<>();for(Map<String,Object> r:jdbc.queryForList("SELECT * FROM LOCAL_TEST_USER ORDER BY CODE"))if(r.get("CODE").toString().contains(code)&&r.get("NAME").toString().contains(name))rows.add(obj("gid",r.get("GID"),"code",r.get("CODE"),"name",r.get("NAME"),"phone",r.get("PHONE"),"type",r.get("USER_TYPE"),"status","1","newUser","系统管理员","newUserTime",r.get("CREATED_AT").toString(),"yhcdGid",r.get("ROLE_GID")));return Result.success(page(rows,b));}
    @PostMapping("/yhwhlb/saveOrUpdateUserInfo.json")
    public Result<Map<String,Object>> saveUser(@RequestBody Map<String,Object>b){admin();String gid=id(),code=required(b,"code",60),name=required(b,"name",100),phone=required(b,"phone",30),type=required(b,"type",10),password=required(b,"password",128);need(code.matches("[A-Za-z0-9_-]{3,60}")&&!code.startsWith("LVTEST_"),422,"Use a separate test data account code");need(Arrays.asList("admin","user").contains(type),422,"Invalid fixture user type");need(phone.matches("[0-9+() -]{7,30}"),422,"Invalid phone");need(password.length()>=8,422,"Test password must contain at least 8 characters");need(jdbc.queryForObject("SELECT COUNT(*) FROM LOCAL_TEST_USER WHERE CODE=?",Integer.class,code)==0,409,"Account already exists");
        jdbc.update("INSERT INTO LOCAL_TEST_USER(GID,CODE,NAME,PHONE,USER_TYPE,PASSWORD_HASH,CREATED_AT) VALUES(?,?,?,?,?,?,CURRENT_TIMESTAMP)",gid,code,name,phone,type,hash(password));return Result.success(obj("gid",gid));}
    private String hash(String password){try{byte[] salt=new byte[16];new SecureRandom().nextBytes(salt);PBEKeySpec spec=new PBEKeySpec(password.toCharArray(),salt,65536,256);byte[] hash=SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();spec.clearPassword();return "PBKDF2$65536$"+Base64.getEncoder().encodeToString(salt)+"$"+Base64.getEncoder().encodeToString(hash);}catch(Exception e){throw new VehicleWorkflowException(503,"Test password hashing unavailable");}}
    @PostMapping("/yhwhlb/bindUserRole.json")
    public Result<Map<String,Object>> bind(@RequestBody Map<String,Object>b){admin();String user=required(b,"userGid",60),role=required(b,"yhcdGid",60);need(jdbc.queryForObject("SELECT COUNT(*) FROM LOCAL_TEST_ROLE WHERE GID=?",Integer.class,role)==1,404,"Role not found");need(jdbc.update("UPDATE LOCAL_TEST_USER SET ROLE_GID=? WHERE GID=?",role,user)==1,404,"Fixture user not found");return Result.success(obj("userGid",user,"yhcdGid",role));}
    @PostMapping("/yhcd/queryYhList.json")
    public Result<Map<String,Object>> roles(@RequestBody Map<String,Object>b){admin();String name=text(b,"name",100),code=text(b,"code",100);List<Map<String,Object>> rows=new ArrayList<>();for(Map<String,Object> r:jdbc.queryForList("SELECT * FROM LOCAL_TEST_ROLE ORDER BY NAME"))if(r.get("NAME").toString().contains(name)&&r.get("DESCRIPTION").toString().contains(code))rows.add(obj("gid",r.get("GID"),"name",r.get("NAME"),"desc",r.get("DESCRIPTION"),"status","1","newUser","系统管理员","newUserTime",r.get("CREATED_AT").toString()));return Result.success(page(rows,b));}
    @PostMapping("/localSystem/saveRole.json")
    public Result<Map<String,Object>> saveRole(@RequestBody Map<String,Object>b){admin();String name=required(b,"name",100),desc=text(b,"desc",200),gid=id();need(jdbc.queryForObject("SELECT COUNT(*) FROM LOCAL_TEST_ROLE WHERE NAME=?",Integer.class,name)==0,409,"Role already exists");jdbc.update("INSERT INTO LOCAL_TEST_ROLE VALUES(?,?,?,CURRENT_TIMESTAMP)",gid,name,desc);return Result.success(obj("gid",gid));}
    @PostMapping("/cd/getTreeMenuList.json")
    public Result<List<Map<String,Object>>> menu(@RequestBody Map<String,Object>b){admin();String role=text(b,"yhcdGid",60);List<String> checked=jdbc.queryForList("SELECT MENU_CODE FROM LOCAL_TEST_ROLE_MENU WHERE ROLE_GID=?",String.class,role);List<Map<String,Object>> result=new ArrayList<>();for(Map<String,Object> r:jdbc.queryForList("SELECT * FROM LOCAL_TEST_MENU ORDER BY CODE"))result.add(obj("title",r.get("TITLE"),"code",r.get("CODE"),"checked",checked.contains(r.get("CODE")),"expand",true,"path",r.get("PATH")));return Result.success(result);}
    @PostMapping("/yhcd/saveCdgl.json")
    public Result<Map<String,Object>> permissions(@RequestBody Map<String,Object>b){admin();String role=required(b,"yhcdGid",60);need(jdbc.queryForObject("SELECT COUNT(*) FROM LOCAL_TEST_ROLE WHERE GID=?",Integer.class,role)==1,404,"Role not found");Object raw=b.get("menuCodeList");need(raw instanceof List&&((List<?>)raw).size()<=100,422,"Invalid permission list");Set<String> codes=new LinkedHashSet<>();for(Object code:(List<?>)raw){need(code instanceof String&&jdbc.queryForObject("SELECT COUNT(*) FROM LOCAL_TEST_MENU WHERE CODE=?",Integer.class,code)==1,422,"Unknown menu code");codes.add((String)code);}jdbc.queryForList("SELECT GID FROM LOCAL_TEST_ROLE WHERE GID=? FOR UPDATE",role);jdbc.update("DELETE FROM LOCAL_TEST_ROLE_MENU WHERE ROLE_GID=?",role);for(String code:codes)jdbc.update("INSERT INTO LOCAL_TEST_ROLE_MENU VALUES(?,?)",role,code);return Result.success(obj("saved",codes.size()));}

    @PostMapping({"/servicePreAccept/queryAllPreAccept.json","/serviceAccept/queryAllAccept.json"})
    public Result<Map<String,Object>> queues(@RequestBody Map<String,Object>b,javax.servlet.http.HttpServletRequest request) {
        VehicleActor actor=access.authenticate(null);
        boolean pre=request.getRequestURI().startsWith("/servicePreAccept/");
        need(pre?actor.preAccept:actor.accept,403,pre?"请使用预审员身份":"请使用受理员身份");
        String name=text(b,"name",100), state=text(b,"bjzt",40), category=text(b,"dbjlx",60);
        need(text(b,"bjxl",10).isEmpty(),422,"当前服务尚未配置办理时限");
        Instant start=instant(b,"startTime"),end=instant(b,"endTime");
        need(start==null||end==null||!start.isAfter(end),422,"Invalid date range");
        List<Map<String,Object>> rows=new ArrayList<>();
        for(Map<String,Object> record:jdbc.queryForList("SELECT ID,LETTER_GID,C_CH,N_YWLX,DBJLX,D_LRSJ FROM HY_CLB_TEMP WHERE XZQH=? AND T_ZT=? ORDER BY D_LRSJ DESC,ID DESC",actor.region,pre?3:0)) {
            String enterprise="运输企业（示例）", stateLabel=pre?"待预受理":"待受理", categoryLabel=pre?"待预受理件":"待受理件";
            List<java.sql.Timestamp> submits=jdbc.queryForList("SELECT EVENT_TIME FROM VEHICLE_WORKFLOW_AUDIT WHERE TEMP_ID=? AND ACTION='SUBMIT' ORDER BY EVENT_TIME DESC",java.sql.Timestamp.class,record.get("ID"));
            java.sql.Timestamp created=submits.isEmpty()?(java.sql.Timestamp)record.get("D_LRSJ"):submits.get(0);
            if(!enterprise.contains(name)||(!state.isEmpty()&&!state.equals(stateLabel))||(!category.isEmpty()&&!category.equals(categoryLabel)&&!category.equals(record.get("DBJLX")))||
               (start!=null&&created.toInstant().isBefore(start))||(end!=null&&created.toInstant().isAfter(end))) continue;
            List<java.sql.Timestamp> reviewed=jdbc.queryForList("SELECT EVENT_TIME FROM VEHICLE_WORKFLOW_AUDIT WHERE TEMP_ID=? AND ACTION='PRE_ACCEPT' ORDER BY EVENT_TIME DESC",java.sql.Timestamp.class,record.get("ID"));
            rows.add(obj("id",record.get("ID").toString(),"letterGid",record.get("LETTER_GID"),"typeChild","道路运输证核发","type","道路运输证","source",1,
                "name",enterprise,"manageType","道路运输证核发","ywlx",record.get("N_YWLX"),"dbjCode",record.get("DBJLX"),
                "createDate",created.toString(),"yslsj",reviewed.isEmpty()?"":reviewed.get(0).toString(),"yslyjTwo",reviewed.isEmpty()?"":"预受理通过",
                "slsx","—","dbjlx",categoryLabel,"dljlxTwo",categoryLabel,"bjzt",stateLabel,"plateNumber",record.get("C_CH")));
        }
        return Result.success(page(rows,b));
    }
    private Instant instant(Map<String,Object>b,String key){String s=text(b,key,40);if(s.isEmpty())return null;try{return Instant.parse(s);}catch(Exception e){throw new VehicleWorkflowException(422,"Invalid queue date");}}
}
