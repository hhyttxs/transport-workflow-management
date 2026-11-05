package local.vehicle.testing;

import com.domed.api.service.transportService.vehicleWorkflow.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.mock.web.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.request.*;
import javax.servlet.*;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.junit.jupiter.api.Assertions.*;

class LocalGuardTests {
    static final String KEY="local-junit-key-012345678901234567890123456789";
    static MockEnvironment safe() {
        YamlPropertiesFactoryBean yaml=new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));
        MockEnvironment e=new MockEnvironment();
        yaml.getObject().forEach((k,v)->e.setProperty(k.toString(),v.toString()));
        e.setActiveProfiles("vehicle-local-test");
        e.setProperty("vehicle.local-test.enabled","true");
        e.setProperty("vehicle.local-test.key",KEY);
        e.setProperty("spring.datasource.password","local-junit-db-password-0123456789");
        return e;
    }
    @Test void explicitLocalConfigurationPasses(){assertDoesNotThrow(()->LocalProfileGuard.validate(safe()));}
    @ParameterizedTest @ValueSource(strings={"prod","production","out","dev","test","default","staging"})
    void otherProfilesCannotActivate(String profile){
        MockEnvironment e=safe();e.setActiveProfiles(profile);
        assertThrows(IllegalStateException.class,()->LocalProfileGuard.validate(e));
        e.setActiveProfiles("vehicle-local-test",profile);
        assertThrows(IllegalStateException.class,()->LocalProfileGuard.validate(e));
    }
    @Test void disabledAndMissingProfileFail(){
        MockEnvironment e=safe();e.setProperty("vehicle.local-test.enabled","false");
        assertThrows(IllegalStateException.class,()->LocalProfileGuard.validate(e));
        MockEnvironment noProfile=safe();noProfile.setActiveProfiles();
        assertThrows(IllegalStateException.class,()->LocalProfileGuard.validate(noProfile));
    }
    @ParameterizedTest @ValueSource(strings={"jdbc:oracle:thin:@localhost:1521:orcl","jdbc:h2:tcp://localhost/test","jdbc:h2:file:./production","jdbc:h2:mem:other","jdbc:h2:mem:vehicle_local_test;INIT=RUNSCRIPT FROM 'bad.sql'"})
    void alternateDatabaseCannotActivate(String url){
        MockEnvironment e=safe().withProperty("spring.datasource.url",url);
        assertThrows(IllegalStateException.class,()->LocalProfileGuard.validate(e));
    }
    @ParameterizedTest @ValueSource(strings={"0.0.0.0","::","192.168.1.2","localhost"})
    void nonPinnedBindRejected(String host){assertThrows(IllegalStateException.class,()->LocalProfileGuard.validate(safe().withProperty("server.address",host)));}
    @Test void guardRejectsAdminIdentityAndWeakSecrets(){
        assertThrows(IllegalStateException.class,()->LocalProfileGuard.validate(safe().withProperty("vehicle.local-test.applicant-id","admin")));
        assertThrows(IllegalStateException.class,()->LocalProfileGuard.validate(safe().withProperty("spring.datasource.username","SA")));
        assertThrows(IllegalStateException.class,()->LocalProfileGuard.validate(safe().withProperty("vehicle.local-test.key","")));
        assertThrows(IllegalStateException.class,()->LocalProfileGuard.validate(safe().withProperty("spring.datasource.password","")));
        assertThrows(IllegalStateException.class,()->LocalProfileGuard.validate(safe().withProperty("spring.jpa.hibernate.ddl-auto","update")));
        assertThrows(IllegalStateException.class,()->LocalProfileGuard.validate(safe().withProperty("spring.sql.init.mode","always")));
        assertThrows(IllegalStateException.class,()->LocalProfileGuard.validate(safe().withProperty("spring.h2.console.enabled","true")));
    }
    @Test void indirectPersistenceAndApplicationOverridesAreRejected(){
        assertThrows(IllegalStateException.class,()->LocalProfileGuard.validate(safe().withProperty("spring.jpa.properties.hibernate.connection.url","jdbc:h2:mem:other")));
        assertThrows(IllegalStateException.class,()->LocalProfileGuard.validate(safe().withProperty("spring.jpa.properties.hibernate.connection.provider_class","external.Provider")));
        assertThrows(IllegalStateException.class,()->LocalProfileGuard.validate(safe().withProperty("spring.main.sources","com.domed.api.ManageApplication")));
        assertThrows(IllegalStateException.class,()->LocalProfileGuard.validate(safe().withProperty("spring.jpa.database-platform","org.hibernate.dialect.Oracle10gDialect")));
        assertThrows(IllegalStateException.class,()->LocalProfileGuard.validate(safe().withProperty("hibernate.connection.url","jdbc:h2:mem:other")));
    }
    static MockHttpServletRequest request(){
        MockHttpServletRequest r=new MockHttpServletRequest("GET","/vehicleLocalTest/session.json");
        r.setRemoteAddr("127.0.0.1");r.setLocalAddr("127.0.0.1");r.addHeader("Host","127.0.0.1:18092");r.addHeader("X-Vehicle-Local-Key",KEY);return r;
    }
    static boolean filter(MockHttpServletRequest req,int expected) throws Exception{
        MockHttpServletResponse res=new MockHttpServletResponse();AtomicBoolean called=new AtomicBoolean();
        new LocalRequestFilter(KEY).doFilter(req,res,(a,b)->called.set(true));
        assertEquals(expected,res.getStatus());return called.get();
    }
    @Test void loopbackFixedRolesPassAndClientCannotSelectAdmin()throws Exception{
        MockHttpServletRequest r=request();r.addHeader("X-UserId","admin");assertTrue(filter(r,200));
        VehicleActor a=(VehicleActor)r.getAttribute(LocalTestActors.ATTRIBUTE);assertEquals("LVTEST_APPLICANT",a.id);assertFalse(a.accept);
        r=request();r.addHeader("X-Vehicle-Local-Actor","admin");assertFalse(filter(r,403));
        r=request();r.addHeader("X-Vehicle-Local-Actor","LVTEST_ACCEPT_REVIEWER");assertFalse(filter(r,403));
    }
    @Test void remoteHostOriginAndKeyDenied()throws Exception{
        MockHttpServletRequest r=request();r.setRemoteAddr("192.168.1.2");assertFalse(filter(r,403));
        r=request();r.setLocalAddr("0.0.0.0");assertFalse(filter(r,403));
        r=request();r.removeHeader("Host");r.addHeader("Host","evil.example:18092");assertFalse(filter(r,403));
        r=request();r.addHeader("Origin","https://evil.example");assertFalse(filter(r,403));
        r=request();r.removeHeader("X-Vehicle-Local-Key");assertFalse(filter(r,403));
    }
    @ParameterizedTest @ValueSource(strings={"Forwarded","X-Forwarded-For","X-Forwarded-Host","X-Forwarded-Proto"})
    void forwardedRequestsDenied(String header)throws Exception{MockHttpServletRequest r=request();r.addHeader(header,"127.0.0.1");assertFalse(filter(r,403));}
    @ParameterizedTest @ValueSource(strings={"/users/me","/yhwh/save.json","/actuator/env","/h2-console","/vehicleWorkflow/../../users"})
    void otherApisDenied(String path)throws Exception{MockHttpServletRequest r=request();r.setRequestURI(path);assertFalse(filter(r,403));}
    @Test void originalProductionPolicyIgnoresFixedLocalActor(){
        VehicleAccessPolicy original=new VehicleAccessPolicy();
        ReflectionTestUtils.setField(original,"publicKey","configured-public-key");
        ReflectionTestUtils.setField(original,"audience","production");
        MockHttpServletRequest request=request();request.setAttribute(LocalTestActors.ATTRIBUTE,LocalTestActors.actor("accept"));
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        try {VehicleWorkflowException e=assertThrows(VehicleWorkflowException.class,()->original.authenticate(null));assertEquals(401,e.getStatus());}
        finally{RequestContextHolder.resetRequestAttributes();}
    }
    @Test void localPolicyNeedsServerFilterAttribute(){
        RequestContextHolder.resetRequestAttributes();
        assertEquals(403,assertThrows(VehicleWorkflowException.class,()->new LocalFixedAccessPolicy().authenticate(null)).getStatus());
    }
}
