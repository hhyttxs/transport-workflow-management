package local.vehicle.testing;

import com.domed.api.controller.transportController.vehicleWorkflow.*;
import com.domed.api.service.transportService.vehicleWorkflow.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@EnableAutoConfiguration
@EntityScan(basePackages = "com.domed.domain.transportDomain")
@Import({LocalTestConfiguration.class, LocalTestEndpoints.class, LocalSystemEndpoints.class, LocalSystemErrors.class, VehicleWorkflowController.class,
        VehicleWorkflowErrorHandler.class, VehicleStore.class, VehicleWorkflowService.class})
public class VehicleLocalTestApplication {
    public static void main(String[] args) { SpringApplication.run(VehicleLocalTestApplication.class, args); }
}
