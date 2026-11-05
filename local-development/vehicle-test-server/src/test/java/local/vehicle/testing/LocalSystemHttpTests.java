package local.vehicle.testing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes=VehicleLocalTestApplication.class,webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT,
 properties={"vehicle.local-test.enabled=true","vehicle.local-test.key=local-http-test-key-012345678901234567890123",
             "spring.datasource.password=local-http-db-password-0123456789"})
@ActiveProfiles("vehicle-local-test")
class LocalSystemHttpTests {
    @Autowired TestRestTemplate http;
    @Autowired JdbcTemplate jdbc;
    static Map<String,Object> map(Object...args){Map<String,Object> b=new LinkedHashMap<>();for(int i=0;i<args.length;i+=2)b.put(args[i].toString(),args[i+1]);return b;}
    ResponseEntity<Map> call(String actor,String path,Map<String,Object> body){HttpHeaders h=new HttpHeaders();h.setContentType(MediaType.APPLICATION_JSON);h.set("X-Vehicle-Local-Key",LocalWorkflowHttpTests.KEY);h.set("X-Vehicle-Local-Actor",actor);return http.exchange("http://127.0.0.1:18092/"+path+".json",HttpMethod.POST,new HttpEntity<>(body,h),Map.class);}
    Map<String,Object> ok(String actor,String path,Map<String,Object>b){ResponseEntity<Map> r=call(actor,path,b);assertEquals(200,r.getStatusCodeValue(),String.valueOf(r.getBody()));assertEquals("0",r.getBody().get("code"));return (Map<String,Object>)r.getBody().get("data");}
    Map<String,Object> form(){return map("status","0","projectName","roadTransportLicense","businessType","firstApply","operator","LOCAL TEST OPERATOR","enterpriseAddress","Local fictitious address","licenseIssueDate","2026-01-01","socialCreditCode","TEST00000000000001","legalPersonName","LOCAL PERSON","legalPersonIdNo","000000000000000001","legalPersonPhone","13000000000","economicType","private","transportTypes","generalFreight","vehicleList",Collections.singletonList(map("brandModel","TEST MODEL","vehicleType","TEST TRUCK","technicalLevel","二级","quantity",1)));}
    @Test void licenseDraftRestorationUpdatesSubmissionOwnershipAndValidation(){
        Map<String,Object> b=form(),saved=ok("applicant","dlhwjyxk/saveOrUpdateDlhwjyxk",b);String gid=saved.get("gid").toString();
        Map<String,Object> restored=ok("applicant","dlhwjyxk/getDlhwjyxkInfoByGid",map("gid",gid));assertEquals(b.get("socialCreditCode"),restored.get("tyshxydm"));assertEquals(b.get("vehicleList"),restored.get("vehicleList"));
        b.put("gid",gid);b.put("enterpriseAddress","Edited local address");assertEquals(gid,ok("applicant","dlhwjyxk/saveOrUpdateDlhwjyxk",b).get("gid"));assertEquals("Edited local address",ok("applicant","dlhwjyxk/getDlhwjyxkInfoByGid",map("gid",gid)).get("qydz"));
        Map<String,Object> list=ok("applicant","dlhwjyxk/queryDraftList",map("numberPage",1,"currentPage",1,"name","LOCAL PERSON"));assertEquals(1,list.get("pageCount"));
        assertEquals(403,call("local-admin","dlhwjyxk/getDlhwjyxkInfoByGid",map("gid",gid)).getStatusCodeValue());
        jdbc.update("UPDATE LOCAL_LICENSE_APPLICATION SET OWNER_ID=? WHERE GID=?","OTHER_FIXTURE_OWNER",gid);
        assertEquals(404,call("applicant","dlhwjyxk/getDlhwjyxkInfoByGid",map("gid",gid)).getStatusCodeValue());
        assertEquals(403,call("applicant","dlhwjyxk/saveOrUpdateDlhwjyxk",b).getStatusCodeValue());
        jdbc.update("UPDATE LOCAL_LICENSE_APPLICATION SET OWNER_ID=? WHERE GID=?","LVTEST_APPLICANT",gid);
        b.put("status","1");b.put("legalPersonPhone","invalid");assertEquals(422,call("applicant","dlhwjyxk/saveOrUpdateDlhwjyxk",b).getStatusCodeValue());assertEquals("0",jdbc.queryForObject("SELECT STATUS FROM LOCAL_LICENSE_APPLICATION WHERE GID=?",String.class,gid));
        b.put("legalPersonPhone","13000000000");ok("applicant","dlhwjyxk/saveOrUpdateDlhwjyxk",b);assertEquals("1",jdbc.queryForObject("SELECT STATUS FROM LOCAL_LICENSE_APPLICATION WHERE GID=?",String.class,gid));
        assertEquals(409,call("applicant","dlhwjyxk/saveOrUpdateDlhwjyxk",b).getStatusCodeValue());
    }
    @Test void administrationIsPersistentTransactionalAndCannotEscalatePinnedActors(){
        assertEquals(403,call("applicant","yhwhlb/queryUserList",map()).getStatusCodeValue());
        Map<String,Object> role=ok("local-admin","localSystem/saveRole",map("name","LOCAL ROLE "+UUID.randomUUID(),"desc","ISOLATED TEST"));String roleId=role.get("gid").toString();
        Map<String,Object> b=map("code","fixture_"+UUID.randomUUID().toString().substring(0,8),"name","Fictitious user","phone","13000000000","password","Disposable-fixture-only","type","admin");
        String user=ok("local-admin","yhwhlb/saveOrUpdateUserInfo",b).get("gid").toString();assertEquals(409,call("local-admin","yhwhlb/saveOrUpdateUserInfo",b).getStatusCodeValue());
        String hash=jdbc.queryForObject("SELECT PASSWORD_HASH FROM LOCAL_TEST_USER WHERE GID=?",String.class,user);assertTrue(hash.startsWith("PBKDF2$65536$"));assertFalse(hash.contains(b.get("password").toString()));
        ok("local-admin","yhwhlb/bindUserRole",map("userGid",user,"yhcdGid",roleId));
        ok("local-admin","yhcd/saveCdgl",map("yhcdGid",roleId,"menuCodeList",Arrays.asList("LOCAL-LICENSE","LOCAL-VEHICLE")));
        assertEquals(2,jdbc.queryForObject("SELECT COUNT(*) FROM LOCAL_TEST_ROLE_MENU WHERE ROLE_GID=?",Integer.class,roleId));
        assertEquals(422,call("local-admin","yhcd/saveCdgl",map("yhcdGid",roleId,"menuCodeList",Arrays.asList("LOCAL-LICENSE","MISSING-MENU"))).getStatusCodeValue());
        assertEquals(2,jdbc.queryForObject("SELECT COUNT(*) FROM LOCAL_TEST_ROLE_MENU WHERE ROLE_GID=?",Integer.class,roleId));
        assertEquals(roleId,jdbc.queryForObject("SELECT ROLE_GID FROM LOCAL_TEST_USER WHERE GID=?",String.class,user));
        Map<String,Object> rows=ok("local-admin","yhwhlb/queryUserList",map("code",b.get("code")));assertFalse(rows.toString().contains(hash));assertFalse(rows.toString().contains("PASSWORD"));
        assertEquals(403,call("local-admin","vehicleWorkflow/options",map()).getStatusCodeValue());
        assertEquals(403,call(b.get("code").toString(),"yhwhlb/queryUserList",map()).getStatusCodeValue());
        assertEquals(404,call("local-admin","yhwhlb/bindUserRole",map("userGid","LVTEST_APPLICANT","yhcdGid",roleId)).getStatusCodeValue());
    }
    @Test void queryAdaptersScopeAndValidateRequests(){
        assertEquals(403,call("applicant","servicePreAccept/queryAllPreAccept",map()).getStatusCodeValue());
        assertEquals(403,call("local-admin","serviceAccept/queryAllAccept",map()).getStatusCodeValue());
        assertEquals(422,call("preaccept","servicePreAccept/queryAllPreAccept",map("startTime","invalid")).getStatusCodeValue());
        ok("preaccept","servicePreAccept/queryAllPreAccept",map("name","' OR 1=1 --"));
        assertEquals(422,call("local-admin","yhwhlb/queryUserList",map("numberPage",-1)).getStatusCodeValue());
    }
}
