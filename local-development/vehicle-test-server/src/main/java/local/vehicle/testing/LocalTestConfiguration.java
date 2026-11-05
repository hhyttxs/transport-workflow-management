package local.vehicle.testing;

import com.domed.api.service.transportService.vehicleWorkflow.VehicleAccessPolicy;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

@Configuration
@Profile("vehicle-local-test")
public class LocalTestConfiguration {
    @Bean public DataSource dataSource(Environment env) throws Exception {
        LocalProfileGuard.validate(env);
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.h2.Driver"); ds.setUrl(LocalProfileGuard.URL);
        ds.setUsername(LocalProfileGuard.USER); ds.setPassword(env.getRequiredProperty("spring.datasource.password"));
        LocalTestFixture.initialize(ds);
        return ds;
    }
    @Bean public VehicleAccessPolicy vehicleAccessPolicy() { return new LocalFixedAccessPolicy(); }
    @Bean public FilterRegistrationBean<LocalRequestFilter> localRequestFilter(Environment env) {
        FilterRegistrationBean<LocalRequestFilter> bean = new FilterRegistrationBean<>(new LocalRequestFilter(env.getRequiredProperty("vehicle.local-test.key")));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE); bean.addUrlPatterns("/*"); return bean;
    }
}
