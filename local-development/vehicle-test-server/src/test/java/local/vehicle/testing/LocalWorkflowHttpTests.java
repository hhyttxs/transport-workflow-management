package local.vehicle.testing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import javax.sql.DataSource;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes=VehicleLocalTestApplication.class,webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT,
 properties={"vehicle.local-test.enabled=true","vehicle.local-test.key=local-http-test-key-012345678901234567890123",
             "spring.datasource.password=local-http-db-password-0123456789"})
@ActiveProfiles("vehicle-local-test")
class LocalWorkflowHttpTests {
    static final String KEY="local-http-test-key-012345678901234567890123";
    @Autowired TestRestTemplate http;
    @Autowired DataSource source;
    ResponseEntity<Map> call(String actor,String operation,Map<String,Object> data) {
        HttpHeaders h=new HttpHeaders();h.setContentType(MediaType.APPLICATION_JSON);h.set("X-Vehicle-Local-Key",KEY);h.set("X-Vehicle-Local-Actor",actor);
        return http.exchange("http://127.0.0.1:18092/vehicleWorkflow/"+operation+".json",HttpMethod.POST,new HttpEntity<>(data,h),Map.class);
    }
    Map<String,Object> ok(String actor,String operation,Map<String,Object> data) {
        ResponseEntity<Map> response=call(actor,operation,data);
        assertEquals(200,response.getStatusCodeValue(),String.valueOf(response.getBody()));
        assertEquals("0",String.valueOf(response.getBody().get("code")));
        return (Map<String,Object>)response.getBody().get("data");
    }
    static Map<String,Object> map(Object...args){Map<String,Object> m=new LinkedHashMap<>();for(int i=0;i<args.length;i+=2)m.put(args[i].toString(),args[i+1]);return m;}
    static Map<String,Object> form(String vin,String plate){return map("enterpriseId","LVTEST_ENTERPRISE","businessType","local_ordinary_ysz_first",
        "plateNumber",plate,"vin",vin,"plateColor","蓝色","vehicleType","TEST-TRUCK","engineNumber","LOCAL-ENGINE","manufacturer","TEST",
        "model","TEST-MODEL","registrationDate","2026-01-01","remarks","Disposable local fixture");}
    static Map<String,Object> identity(Map<String,Object> row){return map("id",row.get("id"),"vehicleId",row.get("vehicleId"),"letterGid",row.get("letterGid"),"revision",row.get("revision"));}
    @Test void integratedDraftRestoreUpdateSubmitPreacceptAcceptAndReturn(){
        Map<String,Object> body=form("LVTEST12345678901","测B12345");
        Map<String,Object> draft=ok("applicant","holdRoadFirstIssueTemp",body);assertEquals(-1,draft.get("status"));
        assertTrue(draft.get("id") instanceof String);assertNotEquals(draft.get("vehicleId"),draft.get("letterGid"));
        Map<String,Object> restored=ok("applicant","getAcceptCarByLetterGid",identity(draft));assertEquals(body.get("vin"),restored.get("vin"));
        Map<String,Object> edited=new LinkedHashMap<>(body);edited.putAll(identity(restored));edited.put("remarks","Updated after restoration");
        Map<String,Object> updated=ok("applicant","holdRoadFirstIssueTemp",edited);assertEquals(draft.get("id"),updated.get("id"));
        assertEquals(409,call("applicant","holdRoadFirstIssueTemp",edited).getStatusCodeValue());
        Map<String,Object> submitted=ok("applicant","submit",identity(updated));assertEquals(3,submitted.get("status"));
        queueContains("preaccept","servicePreAccept/queryAllPreAccept",submitted,"待预受理");
        assertEquals(403,call("applicant","updateCarForecastAccept",identity(submitted)).getStatusCodeValue());
        Map<String,Object> ready=ok("preaccept","updateCarForecastAccept",identity(submitted));assertEquals(0,ready.get("status"));
        queueContains("accept","serviceAccept/queryAllAccept",ready,"待受理");
        Map<String,Object> decision=identity(ready);
        decision.putAll(map("acceptStatus","slhg","opinion","LOCAL fixture verified","permitNumber","LOCAL-TEST-PERMIT",
            "inspectionExpiry","2099-12-31","inspectionEvidence","Fictitious inspection evidence for disposable H2 only","manualVerified",false));
        assertEquals(422,call("accept","updateCarAccept",decision).getStatusCodeValue());
        decision.put("manualVerified",true);
        Map<String,Object> accepted=ok("accept","updateCarAccept",decision);assertEquals(2,accepted.get("status"));
        assertEquals(409,call("accept","updateCarAccept",decision).getStatusCodeValue());
        JdbcTemplate jdbc=new JdbcTemplate(source);
        assertEquals(1,jdbc.queryForObject("SELECT COUNT(*) FROM HY_CLB",Integer.class));
        assertEquals(1,jdbc.queryForObject("SELECT COUNT(*) FROM VEHICLE_WORKFLOW_AUDIT WHERE ACTION='ACCEPT_MANUAL'",Integer.class));
        assertEquals(accepted.get("formalId"),jdbc.queryForObject("SELECT CLB_ID FROM HY_CLB_TEMP WHERE ID=?",java.math.BigDecimal.class,accepted.get("id")).toBigInteger().toString());
        Map<String,Object> secondBody=form("LVTEST12345678902","测B12346");
        Map<String,Object> second=ok("applicant","holdRoadFirstIssueTemp",secondBody);
        second=ok("applicant","submit",identity(second));second=ok("preaccept","updateCarForecastAccept",identity(second));
        Map<String,Object> reject=identity(second);reject.putAll(map("acceptStatus","slth","opinion","LOCAL return test"));
        Map<String,Object> returned=ok("accept","updateCarAccept",reject);assertEquals(-3,returned.get("status"));
        Map<String,Object> fix=new LinkedHashMap<>(secondBody);fix.putAll(identity(returned));fix.put("remarks","Corrected fixture");
        Map<String,Object> saved=ok("applicant","holdRoadFirstIssueTemp",fix);assertEquals(-1,saved.get("status"));
        assertEquals(returned.get("id"),saved.get("id"));
        assertEquals(3,ok("applicant","submit",identity(saved)).get("status"));
    }
    void queueContains(String actor,String path,Map<String,Object> record,String state) {
        HttpHeaders headers=new HttpHeaders();headers.setContentType(MediaType.APPLICATION_JSON);headers.set("X-Vehicle-Local-Key",KEY);headers.set("X-Vehicle-Local-Actor",actor);
        ResponseEntity<Map> response=http.exchange("http://127.0.0.1:18092/"+path+".json",HttpMethod.POST,new HttpEntity<>(map("currentPage",1,"numberPage",10,"bjzt",state),headers),Map.class);
        assertEquals(200,response.getStatusCodeValue(),String.valueOf(response.getBody()));
        Map payload=(Map)response.getBody().get("data"), data=(Map)payload.get("data");
        List<Map> rows=(List<Map>)data.get("result");assertTrue(rows.stream().anyMatch(row->record.get("id").equals(row.get("id"))&&record.get("letterGid").equals(row.get("letterGid"))));
        assertFalse(rows.get(0).get("createDate").toString().isEmpty());
        if("accept".equals(actor))assertFalse(rows.get(0).get("yslsj").toString().isEmpty());
    }
    @Test void unkeyedAndAdministrativeRequestsFail(){
        assertEquals(403,http.getForEntity("http://127.0.0.1:18092/vehicleLocalTest/session.json",Map.class).getStatusCodeValue());
        HttpHeaders h=new HttpHeaders();h.set("X-Vehicle-Local-Key",KEY);h.set("X-Vehicle-Local-Actor","admin");
        assertEquals(403,http.exchange("http://127.0.0.1:18092/vehicleLocalTest/session.json",HttpMethod.GET,new HttpEntity<>(h),Map.class).getStatusCodeValue());
        h.set("X-Vehicle-Local-Actor","accept");
        assertEquals(403,http.exchange("http://127.0.0.1:18092/users/me",HttpMethod.GET,new HttpEntity<>(h),Map.class).getStatusCodeValue());
    }
}
