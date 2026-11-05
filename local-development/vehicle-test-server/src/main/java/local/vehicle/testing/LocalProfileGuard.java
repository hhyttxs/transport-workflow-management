package local.vehicle.testing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.*;
import java.util.*;

public final class LocalProfileGuard implements EnvironmentPostProcessor, Ordered {
    public static final String PROFILE = "vehicle-local-test";
    public static final String URL = "jdbc:h2:mem:vehicle_local_test;MODE=Oracle;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
    public static final String USER = "VEHICLE_LOCAL_TEST";
    public static final String FRONTEND_ORIGIN = "http://127.0.0.1:18091";
    @Override public int getOrder() { return Ordered.LOWEST_PRECEDENCE; }
    @Override public void postProcessEnvironment(ConfigurableEnvironment env, SpringApplication application) { validate(env); }
    public static void validate(Environment env) {
        need(Arrays.equals(env.getActiveProfiles(), new String[]{PROFILE}), "Only the single vehicle-local-test profile is allowed");
        need("true".equals(env.getProperty("vehicle.local-test.enabled")), "Local test mode is disabled by default");
        need("vehicle-local-test-server".equals(env.getProperty("spring.application.name")), "Wrong application identity");
        need("127.0.0.1".equals(env.getProperty("server.address")) && "18092".equals(env.getProperty("server.port")), "Local server must bind 127.0.0.1:18092");
        need(URL.equals(env.getProperty("spring.datasource.url")) && USER.equals(env.getProperty("spring.datasource.username")), "Only the designated disposable H2 database is allowed");
        need("org.h2.Driver".equals(env.getProperty("spring.datasource.driver-class-name")) &&
             "org.hibernate.dialect.H2Dialect".equals(env.getProperty("spring.jpa.database-platform")), "Only the H2 driver/dialect is allowed");
        need(env.getProperty("spring.main.sources") == null && env.getProperty("spring.datasource.jndi-name") == null,
             "Additional application sources/JNDI are forbidden");
        if (env instanceof ConfigurableEnvironment) {
            for (PropertySource<?> source : ((ConfigurableEnvironment) env).getPropertySources()) {
                if (!(source instanceof EnumerablePropertySource)) continue;
                for (String name : ((EnumerablePropertySource<?>) source).getPropertyNames()) {
                    String canonical = name.toLowerCase(Locale.ROOT).replaceAll("[-_.]", "");
                    if (canonical.startsWith("springjpaproperties"))
                        need("springjpapropertieshibernatehbm2ddlauto".equals(canonical), "Extra JPA provider/connection/schema properties are forbidden");
                    need(!canonical.startsWith("hibernateconnection") && !canonical.startsWith("javaxpersistence") &&
                         !canonical.startsWith("jakartapersistence"), "External persistence overrides are forbidden");
                }
            }
        }
        need(length(env.getProperty("spring.datasource.password")) >= 24, "Configure a separate local database password");
        need(length(env.getProperty("vehicle.local-test.key")) >= 32, "Configure a local test access key (minimum 32 characters)");
        need("none".equals(env.getProperty("spring.jpa.hibernate.ddl-auto")) &&
             "none".equals(env.getProperty("spring.jpa.properties.hibernate.hbm2ddl.auto")) &&
             "false".equals(env.getProperty("spring.jpa.generate-ddl")) &&
             "never".equals(env.getProperty("spring.sql.init.mode")) &&
             "never".equals(env.getProperty("spring.datasource.initialization-mode")), "Automatic schema tools must remain disabled");
        need("false".equals(env.getProperty("spring.h2.console.enabled")) &&
             "false".equals(env.getProperty("spring.main.allow-bean-definition-overriding")), "Console/bean overrides must stay disabled");
        need("LVTEST_APPLICANT".equals(env.getProperty("vehicle.local-test.applicant-id")) &&
             "LVTEST_PRE_REVIEWER".equals(env.getProperty("vehicle.local-test.preaccept-id")) &&
             "LVTEST_ACCEPT_REVIEWER".equals(env.getProperty("vehicle.local-test.accept-id")), "Only the three pinned test identities are allowed");
        need("210202".equals(env.getProperty("vehicle.local-test.region")) &&
             "TEST".equals(env.getProperty("vehicle.local-test.department")), "Only the fixture test scope is allowed");
    }
    private static int length(String value) { return value == null ? 0 : value.length(); }
    private static void need(boolean valid, String reason) { if (!valid) throw new IllegalStateException("LOCAL TEST GUARD: " + reason); }
}
