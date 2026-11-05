import com.domed.api.service.transportService.vehicleWorkflow.*;
import com.domed.api.controller.transportController.vehicleWorkflow.*;
import com.domed.domain.transportDomain.*;
import com.domed.domain.transportDomain.transportTemp.HyClbTemp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.orm.jpa.*;
import org.springframework.transaction.interceptor.*;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import javax.persistence.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.lang.reflect.*;
import java.security.*;
import java.util.*;
import java.util.regex.*;

/** Isolated H2 persistence/transaction checks. Never connects to the user's Oracle instance. */
public class VehicleWorkflowChecks {
    static final String URL="jdbc:h2:mem:vehicle;MODE=Oracle;DB_CLOSE_DELAY=-1";
    static SessionFactory factory;
    static EntityManager em;
    static IVehicleWorkflowService service;
    static VehicleWorkflowService target;
    static VehicleAccessPolicy access;
    static TransactionTemplate tx;
    static int checks;
    static final VehicleActor applicant=new VehicleActor("applicant","Applicant","210202","DEPT",true,false,false);
    static final VehicleActor other=new VehicleActor("other","Other","210202","DEPT",true,false,false);
    static final VehicleActor pre=new VehicleActor("pre","Pre Reviewer","210202","DEPT",false,true,false);
    static final VehicleActor reviewer=new VehicleActor("review","Reviewer","210202","DEPT",false,false,true);
    static final VehicleActor wrongRegion=new VehicleActor("away","Away","210203","DEPT",false,true,true);
    static final ObjectMapper JSON=new ObjectMapper();

    public static void main(String[] args) throws Exception {
        try {
            schema(Paths.get(args[0]),Paths.get(args[1]));
            Configuration cfg=new Configuration().addAnnotatedClass(HyClb.class).addAnnotatedClass(HyClbTemp.class)
                    .addAnnotatedClass(VehicleWorkflowAudit.class);
            cfg.setProperty("hibernate.connection.driver_class","org.h2.Driver");
            cfg.setProperty("hibernate.connection.url",URL);
            cfg.setProperty("hibernate.dialect","org.hibernate.dialect.H2Dialect");
            cfg.setProperty("hibernate.hbm2ddl.auto","none");
            cfg.setProperty("hibernate.show_sql","false");
            factory=cfg.buildSessionFactory();
            em=SharedEntityManagerCreator.createSharedEntityManager(factory);
            JpaTransactionManager tm=new JpaTransactionManager(factory); tx=new TransactionTemplate(tm);
            VehicleStore store=new VehicleStore(); set(store,"em",em);
            target=new VehicleWorkflowService(); set(target,"store",store); set(target,"manualFormalAccept",false);
            ProxyFactory proxy=new ProxyFactory(target); proxy.setInterfaces(IVehicleWorkflowService.class);
            proxy.addAdvice(new TransactionInterceptor(tm,new AnnotationTransactionAttributeSource()));
            service=(IVehicleWorkflowService)proxy.getProxy();
            access=new VehicleAccessPolicy(); set(access,"em",em); set(access,"audience","vehicle-test");
            run();
            System.out.println("PASS "+checks+" vehicle checks; actual H2 persistence and Spring transactions; NOT Oracle runtime verification.");
        } finally { if(factory!=null)factory.close(); }
    }
    static void run() throws Exception {
        check("formal entity has all 141 DDL columns", Arrays.stream(HyClb.class.getDeclaredFields()).filter(f->f.getAnnotation(Column.class)!=null).count()==141);
        Map<String,Object> form=form("VIN_UNUSED");
        form.put("vin","L1234567890123456"); // 17 characters, valid VIN alphabet
        Map<String,Object> draft=service.holdRoadFirstIssueTemp(applicant,form);
        check("new draft -1 with separate numeric ID, U_CLBM, LETTER_GID",
                ((Number)draft.get("status")).intValue()==-1 && draft.get("id") instanceof String
                && !draft.get("vehicleId").equals(draft.get("letterGid")) && !draft.get("id").equals(draft.get("vehicleId")));
        check("single lookup restores fields", "L1234567890123456".equals(service.getAcceptCarByLetterGid(applicant,id(draft)).get("vin")));
        tx.execute(s->{ HyClbTemp r=em.find(HyClbTemp.class,new java.math.BigInteger(draft.get("id").toString()));
            r.setUpdatedAt(new java.util.Date(r.getUpdatedAt().getTime()/1000*1000)); return null; });
        check("revision survives Oracle DATE second precision",draft.get("revision").equals(service.getAcceptCarByLetterGid(applicant,id(draft)).get("revision")));
        expect("foreign owner cannot read",403,()->service.getAcceptCarByLetterGid(other,id(draft)));
        expect("foreign owner cannot update",403,()->service.holdRoadFirstIssueTemp(other,edit(draft,"remarks","attack")));
        expect("spoofed role/status rejected",422,()->{Map<String,Object> x=edit(draft,"remarks","x");x.put("status",2);service.holdRoadFirstIssueTemp(applicant,x);});
        expect("numeric JSON IDs rejected without precision loss",422,()->{Map<String,Object>x=id(draft);x.put("id",123);service.getAcceptCarByLetterGid(applicant,x);});
        expect("inconsistent identifier rejected",409,()->{Map<String,Object>x=id(draft);x.put("vehicleId","other");service.getAcceptCarByLetterGid(applicant,x);});
        Map<String,Object> updated=service.holdRoadFirstIssueTemp(applicant,edit(draft,"remarks","restored draft update"));
        check("update keeps identities and one record",draft.get("id").equals(updated.get("id")) && count("HY_CLB_TEMP")==1);
        expect("stale revision rejected",409,()->service.holdRoadFirstIssueTemp(applicant,edit(draft,"remarks","stale")));
        expect("invalid VIN rejected",422,()->service.holdRoadFirstIssueTemp(applicant,edit(updated,"vin","INVALID")));
        expect("invalid calendar date rejected",422,()->service.holdRoadFirstIssueTemp(applicant,edit(updated,"registrationDate","2026-02-30")));
        Map<String,Object> inject=form("L1234567890123457"); inject.put("enterpriseId","E' OR '1'='1");
        expect("parameterized enterprise ownership query",403,()->service.holdRoadFirstIssueTemp(applicant,inject));
        Map<String,Object> special=form("L1234567890123457"); special.put("businessType","Danger_first");
        expect("specialist approval cannot be downgraded to ordinary",422,()->service.holdRoadFirstIssueTemp(applicant,special));
        Map<String,Object> partialForm=form("L1234567890123457"); partialForm.put("vin","");
        Map<String,Object> partial=service.holdRoadFirstIssueTemp(applicant,partialForm);
        expect("incomplete draft cannot submit",422,()->service.submit(applicant,id(partial)));
        check("draft pagination count and own scope",((Number)service.listDrafts(applicant,map("page",1,"size",1)).get("total")).longValue()==2
                && ((List<?>)service.listDrafts(other,map("page",1)).get("rows")).isEmpty());
        expect("invalid page rejected",422,()->service.listDrafts(applicant,map("page",0)));
        Map<String,Object> submitted=service.submit(applicant,id(updated));
        check("submit -1 -> 3",Integer.valueOf(3).equals(submitted.get("status")));
        expect("submitted draft cannot edit",409,()->service.holdRoadFirstIssueTemp(applicant,edit(submitted,"remarks","late")));
        expect("wrong regional reviewer denied",403,()->service.updateCarForecastAccept(wrongRegion,id(submitted)));
        expect("wrong reviewer permission denied",403,()->service.updateCarForecastAccept(reviewer,id(submitted)));
        Map<String,Object> acceptedQueue=service.updateCarForecastAccept(pre,id(submitted));
        check("preaccept 3 -> 0 and correct queue",Integer.valueOf(0).equals(acceptedQueue.get("status")) && "dbjlx_sl_dsl".equals(acceptedQueue.get("queueType")));
        expect("duplicate preaccept blocked",409,()->service.updateCarForecastAccept(pre,id(acceptedQueue)));
        expect("unapproved manual formal acceptance disabled",503,()->service.updateCarAccept(reviewer,decision(acceptedQueue,"slhg")));
        check("blocked acceptance rolls back opinion",service.getAcceptCarByLetterGid(reviewer,id(acceptedQueue)).get("opinion")==null && count("HY_CLB")==0);
        expect("correction notice branch explicitly blocked",422,()->service.updateCarAccept(reviewer,decision(acceptedQueue,"slthbz")));
        set(target,"manualFormalAccept",true); // Restore owner-approved default after testing the deployment kill switch.
        Map<String,Object> unconfirmed=decision(acceptedQueue,"slhg");unconfirmed.put("manualVerified",false);
        expect("manual verification confirmation required",422,()->service.updateCarAccept(reviewer,unconfirmed));
        Map<String,Object> expired=decision(acceptedQueue,"slhg");expired.put("inspectionExpiry","2000-01-01");
        expect("expired inspection cannot finalize",422,()->service.updateCarAccept(reviewer,expired));
        Map<String,Object> finalized=service.updateCarAccept(reviewer,decision(acceptedQueue,"slhg"));
        check("formal accept 0 -> 2 with separate formal numeric ID",Integer.valueOf(2).equals(finalized.get("status"))
                && finalized.get("formalId") instanceof String && !finalized.get("id").equals(finalized.get("formalId")));
        check("formal row preserves VIN, vehicle and application identities",tx.execute(s->{
            HyClb row=em.find(HyClb.class,new java.math.BigInteger(finalized.get("formalId").toString()));
            return row.getUClbm().equals(finalized.get("vehicleId")) && row.getLetterGid().equals(finalized.get("letterGid"))
                && row.getCCjh().equals(finalized.get("vin")) && "PERMIT-TEST".equals(row.getCYyzh());
        }));
        expect("repeat formal acceptance blocked",409,()->service.updateCarAccept(reviewer,decision(finalized,"slhg")));
        check("audit records every successful transition only",count("VEHICLE_WORKFLOW_AUDIT")==6);
        try {
            sql("INSERT INTO VEHICLE_WORKFLOW_AUDIT(EVENT_ID,TEMP_ID,VEHICLE_ID,LETTER_GID,ACTOR_ID,ACTION,TO_STATE,EVENT_TIME,ACCEPTED_VIN) VALUES('duplicate',999,'v','l','review','ACCEPT_MANUAL',2,CURRENT_TIMESTAMP,'L1234567890123456')");
            throw new AssertionError("Duplicate accepted VIN was allowed");
        } catch(SQLException expected) { check("database uniqueness guards concurrent duplicate VIN approvals", "23505".equals(expected.getSQLState())); }
        Map<String,Object> f2=service.holdRoadFirstIssueTemp(applicant,form("L1234567890123458"));
        Map<String,Object> q2=service.updateCarForecastAccept(pre,id(service.submit(applicant,id(f2))));
        Map<String,Object> returned=service.updateCarAccept(reviewer,decision(q2,"slth"));
        check("return preserves -3 branch",Integer.valueOf(-3).equals(returned.get("status")));
        Map<String,Object> savedAgain=service.holdRoadFirstIssueTemp(applicant,edit(returned,"remarks","fixed"));
        check("returned record saves back to draft -1",Integer.valueOf(-1).equals(savedAgain.get("status")));
        Map<String,Object> q3=service.updateCarForecastAccept(pre,id(service.submit(applicant,id(savedAgain))));
        // Force failure after the formal INSERT to prove Spring transaction rollback.
        sql("ALTER TABLE HY_CLB_TEMP ADD CONSTRAINT TEST_NO_APPROVAL CHECK (T_ZT <> 2 OR ID <> "+q3.get("id")+")");
        long formalBefore=count("HY_CLB"), auditBefore=count("VEHICLE_WORKFLOW_AUDIT");
        try { service.updateCarAccept(reviewer,decision(q3,"slhg"));throw new AssertionError("Expected SQL constraint failure"); }
        catch(PersistenceException expected){checks++;System.out.println("PASS forced persistence failure");}
        check("formal insert and audit rollback atomically",count("HY_CLB")==formalBefore && count("VEHICLE_WORKFLOW_AUDIT")==auditBefore
                && Integer.valueOf(0).equals(service.getAcceptCarByLetterGid(reviewer,id(q3)).get("status")));
        sql("ALTER TABLE HY_CLB_TEMP DROP CONSTRAINT TEST_NO_APPROVAL"); // isolated test database only
        sql("INSERT INTO HY_CLB_TEMP(ID,U_CLBM,LETTER_GID,C_LRR,XZQH,T_ZT,N_YWLX) VALUES(900,'legacy-car','legacy-letter','applicant','210202',3,'ordinary_ysz_first')");
        Map<String,Object> legacy=service.getAcceptCarByLetterGid(applicant,map("id","900"));
        check("legacy record is visible but read-only",Boolean.FALSE.equals(legacy.get("managed")) && Boolean.FALSE.equals(legacy.get("canPreAccept")));
        expect("legacy data cannot be silently converted",409,()->service.updateCarForecastAccept(pre,id(legacy)));
        sql("INSERT INTO HY_CLB_TEMP(ID,U_CLBM,LETTER_GID,C_LRR,XZQH,T_ZT) VALUES(901,'batch-1','batch-letter','applicant','210202',3)");
        sql("INSERT INTO HY_CLB_TEMP(ID,U_CLBM,LETTER_GID,C_LRR,XZQH,T_ZT) VALUES(902,'batch-2','batch-letter','applicant','210202',3)");
        expect("multi-vehicle letter rejected",409,()->service.getAcceptCarByLetterGid(applicant,map("letterGid","batch-letter")));
        expect("ID cannot bypass multi-vehicle protection",409,()->service.getAcceptCarByLetterGid(applicant,map("id","901")));
        sql("ALTER TABLE HY_CLB_TEMP ADD UNKNOWN_REQUIRED VARCHAR2(10) DEFAULT 'X' NOT NULL");
        sql("ALTER TABLE HY_CLB_TEMP ALTER COLUMN UNKNOWN_REQUIRED DROP DEFAULT");
        expect("unknown mandatory temp column blocks persistence",503,()->service.holdRoadFirstIssueTemp(applicant,form("L1234567890123459")));
        sql("ALTER TABLE HY_CLB_TEMP DROP COLUMN UNKNOWN_REQUIRED");
        sql("ALTER TABLE VEHICLE_WORKFLOW_AUDIT DROP CONSTRAINT UK_VEHICLE_ACCEPTED_VIN");
        expect("missing acceptance uniqueness guard fails closed",503,()->service.listDrafts(applicant,map("page",1)));
        sql("ALTER TABLE VEHICLE_WORKFLOW_AUDIT ADD CONSTRAINT UK_VEHICLE_ACCEPTED_VIN UNIQUE(ACCEPTED_VIN)");
        jwtChecks();
    }
    static void jwtChecks()throws Exception{
        KeyPairGenerator gen=KeyPairGenerator.getInstance("RSA");gen.initialize(2048);
        KeyPair pair=gen.generateKeyPair(), bad=gen.generateKeyPair();
        set(access,"publicKey",Base64.getEncoder().encodeToString(pair.getPublic().getEncoded()));
        long now=System.currentTimeMillis()/1000;
        Map<String,Object> claims=map("user_name","{\"userName\":\"alice\",\"type\":\"admin\"}","exp",now+600,"aud",Arrays.asList("vehicle-test"));
        String valid=jwt(pair,claims,"RS256");
        check("signed existing login JSON and lower-case bearer supported","alice".equals(access.verifySubject("bearer "+valid)));
        expect("forged signature rejected",401,()->access.verifySubject("Bearer "+jwt(bad,claims,"RS256")));
        Map<String,Object> expired=new HashMap<>(claims);expired.put("exp",now-1);
        expect("expired JWT rejected",401,()->access.verifySubject("Bearer "+jwt(pair,expired,"RS256")));
        Map<String,Object> wrongAud=new HashMap<>(claims);wrongAud.put("aud","elsewhere");
        expect("wrong JWT audience rejected",401,()->access.verifySubject("Bearer "+jwt(pair,wrongAud,"RS256")));
        Map<String,Object> future=new HashMap<>(claims);future.put("nbf",now+100);
        expect("future JWT rejected",401,()->access.verifySubject("Bearer "+jwt(pair,future,"RS256")));
        expect("unsupported JWT algorithm rejected",401,()->access.verifySubject("Bearer "+jwt(pair,claims,"none")));
        expect("missing signed authorization rejected",401,()->access.verifySubject(null));
        VehicleActor authenticated=tx.execute(s->access.authenticate("Bearer "+valid));
        check("DB authoritative identity/role/region",authenticated.applicant && authenticated.id.equals("applicant") && authenticated.region.equals("210202") && !authenticated.accept);
        VehicleWorkflowController controller=new VehicleWorkflowController();set(controller,"access",access);set(controller,"service",service);
        MockMvc mvc=MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new VehicleWorkflowErrorHandler()).build();
        MvcResult ok=mvc.perform(post("/vehicleWorkflow/drafts.json").header("Authorization","Bearer "+valid)
                .contentType("application/json").content("{}")).andReturn();
        check("HTTP endpoint uses existing successful Result envelope",ok.getResponse().getStatus()==200
                && "0".equals(JSON.readTree(ok.getResponse().getContentAsString()).path("code").asText()));
        MvcResult invalid=mvc.perform(post("/vehicleWorkflow/holdRoadFirstIssueTemp.json").header("Authorization","Bearer "+valid)
                .contentType("application/json").content("{\"status\":2}")).andReturn();
        check("HTTP business validation preserves 422 status and Result error",invalid.getResponse().getStatus()==422
                && "V422".equals(JSON.readTree(invalid.getResponse().getContentAsString()).path("code").asText()));
        MvcResult forged=mvc.perform(post("/vehicleWorkflow/drafts.json").header("payload","{\"userId\":\"applicant\"}")
                .contentType("application/json").content("{}")).andReturn();
        check("unsigned payload header cannot authenticate HTTP request",forged.getResponse().getStatus()==401);
        sql("UPDATE TAB_YHWH SET STATUS=0 WHERE CODE='alice'");
        expect("revoked account rejected despite valid JWT",403,()->tx.execute(s->access.authenticate("Bearer "+valid)));
        sql("UPDATE TAB_YHWH SET STATUS=1 WHERE CODE='alice'");
        sql("DELETE FROM TAB_CDGL");
        check("revoked menu cannot authorize draft",!tx.execute(s->access.authenticate("Bearer "+valid)).applicant);
        set(access,"publicKey","");
        expect("missing trust configuration fails closed",503,()->access.verifySubject("Bearer "+valid));
    }
    static String jwt(KeyPair pair,Map<String,Object> claims,String alg)throws Exception{
        Base64.Encoder e=Base64.getUrlEncoder().withoutPadding();
        String head=e.encodeToString(JSON.writeValueAsBytes(map("alg",alg))), body=e.encodeToString(JSON.writeValueAsBytes(claims));
        Signature sign=Signature.getInstance("SHA256withRSA");sign.initSign(pair.getPrivate());sign.update((head+"."+body).getBytes(StandardCharsets.US_ASCII));
        return head+"."+body+"."+e.encodeToString(sign.sign());
    }
    static void schema(Path formal,Path audit)throws Exception{
        Class.forName("org.h2.Driver");
        String source=new String(Files.readAllBytes(formal),StandardCharsets.UTF_8);
        Matcher m=Pattern.compile("CREATE TABLE \"TRANSPORT\"\\.\"HY_CLB\" \\((.*?)^\\)",Pattern.DOTALL|Pattern.MULTILINE).matcher(source);
        if(!m.find()) throw new AssertionError("DDL table body not found");
        String body=m.group(1).replaceAll("(?i)\\s+(CHAR|BYTE)\\)",")");
        sql("CREATE TABLE HY_CLB ("+body+", PRIMARY KEY(ID))");
        StringBuilder temp=new StringBuilder("CREATE TABLE HY_CLB_TEMP(");
        for(Field f:HyClbTemp.class.getDeclaredFields()){
            Column c=f.getAnnotation(Column.class);if(c==null)continue;
            if(temp.charAt(temp.length()-1)!='(')temp.append(',');
            temp.append(c.name()).append(' ');
            temp.append(f.getType()==String.class ? "VARCHAR2(4000)" :
                    java.util.Date.class.isAssignableFrom(f.getType())?"TIMESTAMP":"NUMBER(20,0)");
            if(c.name().equals("ID"))temp.append(" PRIMARY KEY");
        }
        sql(temp.append(')').toString());
        String migration=new String(Files.readAllBytes(audit),StandardCharsets.UTF_8);
        Matcher a=Pattern.compile("CREATE TABLE VEHICLE_WORKFLOW_AUDIT \\((.*?)\\);",Pattern.DOTALL).matcher(migration);
        if(!a.find())throw new AssertionError("Audit DDL not found");
        sql(("CREATE TABLE VEHICLE_WORKFLOW_AUDIT ("+a.group(1)+")").replaceAll("(?i)\\s+CHAR\\)",")"));
        sql("CREATE SEQUENCE HY_CLB_TEMP_SEQ START WITH 100 INCREMENT BY 1");
        sql("CREATE SEQUENCE HY_CLB_SEQ START WITH 10000 INCREMENT BY 1");
        sql("CREATE TABLE HY_YHXX(ID VARCHAR2(36) PRIMARY KEY,CREATOR VARCHAR2(36),XKZH VARCHAR2(20),SQRMC VARCHAR2(100))");
        sql("INSERT INTO HY_YHXX VALUES('enterprise','applicant','LICENSE','Test Enterprise')");
        sql("CREATE TABLE HY_DICTIONARY(KEY VARCHAR2(100),VALUE VARCHAR2(100))");
        sql("INSERT INTO HY_DICTIONARY VALUES('ordinary_ysz_first','道路运输证核发')");
        sql("CREATE TABLE TAB_YHWH(GID VARCHAR2(36),NAME VARCHAR2(40),XZQH_CODE VARCHAR2(80),XZJG_CODE VARCHAR2(10),YHCD_GID VARCHAR2(36),CODE VARCHAR2(100),STATUS INTEGER)");
        sql("INSERT INTO TAB_YHWH VALUES('applicant','Applicant','210202','DEPT','app-role','alice',1)");
        sql("CREATE TABLE TAB_YHCD(GID VARCHAR2(36),STATUS INTEGER)");
        sql("INSERT INTO TAB_YHCD VALUES('app-role',1)");
        sql("CREATE TABLE TAB_MENU(CODE VARCHAR2(40),URL VARCHAR2(200),STATUS INTEGER)");
        sql("INSERT INTO TAB_MENU VALUES('vehicle','/frontPage/vehicleWorkflow_index',0)");
        sql("CREATE TABLE TAB_CDGL(YHCD_GID VARCHAR2(36),MENU_CODE VARCHAR2(40))");
        sql("INSERT INTO TAB_CDGL VALUES('app-role','vehicle')");
    }
    static Map<String,Object> form(String vin){
        return map("enterpriseId","enterprise","businessType","ordinary_ysz_first","vin",vin,"plateNumber","辽B12345",
                "plateColor","蓝色","vehicleType","货车","engineNumber","ENGINE","manufacturer","Maker","model","Model",
                "registrationDate","2026-01-01","remarks","Test");
    }
    static Map<String,Object> id(Map<String,Object> r){return map("id",r.get("id"),"letterGid",r.get("letterGid"),"vehicleId",r.get("vehicleId"),"revision",r.get("revision"));}
    static Map<String,Object> edit(Map<String,Object> r,String k,Object v){
        Map<String,Object> x=id(r);
        for(String key:Arrays.asList("enterpriseId","businessType","vin","plateNumber","plateColor","vehicleType","engineNumber","manufacturer","model","registrationDate","remarks"))x.put(key,r.get(key));
        x.put(k,v);return x;
    }
    static Map<String,Object> decision(Map<String,Object> r,String status){Map<String,Object>x=id(r);x.putAll(map("acceptStatus",status,"opinion","Reviewed","permitNumber","PERMIT-TEST","inspectionExpiry","2099-12-31","inspectionEvidence","Test-only reviewed inspection record","manualVerified",true));return x;}
    static Map<String,Object> map(Object...a){Map<String,Object>m=new LinkedHashMap<>();for(int i=0;i<a.length;i+=2)m.put(a[i].toString(),a[i+1]);return m;}
    static void set(Object target,String name,Object value)throws Exception{Field f=target.getClass().getDeclaredField(name);f.setAccessible(true);f.set(target,value);}
    static long count(String table){return tx.execute(s->((Number)em.createNativeQuery("SELECT COUNT(*) FROM "+table).getSingleResult()).longValue());}
    static void sql(String text)throws Exception{try(Connection c=DriverManager.getConnection(URL);Statement s=c.createStatement()){s.execute(text);}}
    interface Task{void run()throws Exception;}
    static void expect(String name,int code,Task action)throws Exception{try{action.run();throw new AssertionError(name+" did not fail");}catch(VehicleWorkflowException e){if(e.getStatus()!=code)throw new AssertionError(name+": expected "+code+" got "+e.getStatus()+": "+e.getMessage(),e);checks++;System.out.println("PASS "+name);}}
    static void check(String name,boolean ok){if(!ok)throw new AssertionError(name);checks++;System.out.println("PASS "+name);}
}
