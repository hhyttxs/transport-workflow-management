package local.vehicle.testing;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import javax.sql.DataSource;
import java.sql.*;
/** Disposable H2-only fixture; never a proposed Oracle HY_CLB_TEMP schema. */
public final class LocalTestFixture {
    private LocalTestFixture() {}
    public static void initialize(DataSource source) throws Exception {
        try (Connection c = source.getConnection()) {
            if (!"H2".equals(c.getMetaData().getDatabaseProductName()) ||
                !"jdbc:h2:mem:vehicle_local_test".equals(c.getMetaData().getURL()) ||
                !LocalProfileGuard.USER.equals(c.getMetaData().getUserName()))
                throw new IllegalStateException("Refusing fixture initialization outside the designated in-memory H2 database");
            ScriptUtils.executeSqlScript(c, new EncodedResource(new ClassPathResource("local-test/fixture.sql"), "UTF-8"));
        }
    }
}
