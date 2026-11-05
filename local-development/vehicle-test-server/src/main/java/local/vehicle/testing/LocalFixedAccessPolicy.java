package local.vehicle.testing;

import com.domed.api.service.transportService.vehicleWorkflow.*;
import org.springframework.web.context.request.*;
/** Only constructed by the separately built local harness, never by production scanning. */
public class LocalFixedAccessPolicy extends VehicleAccessPolicy {
    @Override public VehicleActor authenticate(String ignoredAuthorization) {
        RequestAttributes request = RequestContextHolder.getRequestAttributes();
        Object actor = request == null ? null : request.getAttribute(LocalTestActors.ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        if (!(actor instanceof VehicleActor)) throw new VehicleWorkflowException(403, "Local request guard has not authenticated this request");
        return (VehicleActor) actor;
    }
}
