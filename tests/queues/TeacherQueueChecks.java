import com.domed.api.service.transportService.serviceAccept.impl.ServiceAcceptServiceImpl;
import com.domed.api.service.transportService.servicePreAccept.impl.ServicePreAcceptServiceImpl;
import com.domed.common.result.Result;
import com.domed.core.exception.ApplicationException;
import com.domed.core.query.support.IPageList;
import com.domed.core.service.ICommonService;
import java.lang.reflect.*;
import java.util.*;

/** Test-only framework proxies. No substitute implementations enter application source. */
public class TeacherQueueChecks {
    private static String lastSql;
    private static Map lastParams;
    private static int calls;
    private static int checks;

    public static void main(String[] args) throws Exception {
        IPageList page = (IPageList) Proxy.newProxyInstance(IPageList.class.getClassLoader(), new Class[]{IPageList.class}, (p, m, a) -> {
            if ("getRowCount".equals(m.getName())) return 23;
            if ("getPages".equals(m.getName())) return 3;
            if ("getResult".equals(m.getName())) return Collections.emptyList();
            return null;
        });
        ICommonService common = (ICommonService) Proxy.newProxyInstance(ICommonService.class.getClassLoader(), new Class[]{ICommonService.class}, (p, m, a) -> {
            if ("getParam".equals(m.getName()) || "notNull".equals(m.getName())) {
                Object value = ((Map) a[0]).get(a[1]);
                String text = value == null ? "" : value.toString();
                if ("notNull".equals(m.getName()) && text.isEmpty()) throw new ApplicationException("Missing input");
                return text;
            }
            if ("pageQuery".equals(m.getName())) {
                calls++;
                lastSql=(String)a[0]; lastParams=(Map)a[3];
                if (!"2".equals(a[1]) || !"10".equals(a[2])) throw new AssertionError("Pagination was changed");
                return page;
            }
            throw new AssertionError("Unexpected framework call " + m.getName());
        });
        Object[] services={new ServiceAcceptServiceImpl(),new ServicePreAcceptServiceImpl()};
        for(Object service:services){Field field=service.getClass().getDeclaredField("commonService");field.setAccessible(true);field.set(service,common);}
        String injection="' OR 1=1 --";
        for(String name:new String[]{"queryAllAccept","queryAllExamine","queryAllPreAccept"}) {
            Object service=name.equals("queryAllPreAccept")?services[1]:services[0];
            Method method=service.getClass().getMethod(name,Map.class);
            Map<String,Object> input=inputs();
            input.put("xzqh",injection);
            input.put("dbjlx",injection);input.put("bjzt",injection);input.put("bjxl","1");
            input.put("startTime","2026-10-01T00:00:00.000Z");input.put("endTime","2026-10-11T23:59:59.000Z");
            Result result=(Result)method.invoke(service,input);
            assertThat(!lastSql.contains(injection),name+" never embeds submitted filter values in SQL");
            assertThat(injection.equals(lastParams.get("xzqh")),name+" passes region through bound parameters");
            assertThat(lastParams.containsKey("startTime")&&lastParams.containsKey("endTime"),name+" binds both date bounds");
            Map response=(Map)result.getData();
            assertThat(Integer.valueOf(23).equals(response.get("pageCount"))&&Integer.valueOf(3).equals(response.get("totalPage")),name+" preserves Result/data and real page count");
            Map<String,Object> invalid=inputs();invalid.put("numberPage","-1");
            expectApplicationError(method,service,invalid,name+" rejects invalid pagination");
            invalid=inputs();invalid.put("startTime","not-a-date");
            expectApplicationError(method,service,invalid,name+" rejects malformed date filters");
            if(!name.equals("queryAllExamine")) {
                invalid=inputs();invalid.put("bjxl","1 OR 1=1");
                expectApplicationError(method,service,invalid,name+" rejects invalid efficiency values");
            }
        }
        System.out.println("PASSED "+checks+" focused backend checks; no database connection executed.");
    }

    private static Map<String,Object> inputs(){Map<String,Object> input=new HashMap<>();input.put("userType","admin");input.put("xzjg","ORG");input.put("xzqh","210200");input.put("currentPage","2");input.put("numberPage","10");return input;}
    private static void assertThat(boolean value,String name){if(!value)throw new AssertionError(name);checks++;System.out.println("PASS "+name);}
    private static void expectApplicationError(Method method,Object service,Map input,String name)throws Exception{
        int before=calls;
        try{method.invoke(service,input);throw new AssertionError("Expected rejection: "+name);}catch(InvocationTargetException e){if(!(e.getCause() instanceof ApplicationException))throw e;}
        assertThat(calls==before,name);
    }
}
