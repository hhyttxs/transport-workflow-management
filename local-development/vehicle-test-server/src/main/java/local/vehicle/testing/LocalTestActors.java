package local.vehicle.testing;

import com.domed.api.service.transportService.vehicleWorkflow.*;
import java.util.*;
public final class LocalTestActors {
    public static final String ATTRIBUTE = "local.vehicle.pinnedActor";
    private LocalTestActors() {}
    public static VehicleActor actor(String selected) {
        if (selected == null || selected.isEmpty() || "applicant".equals(selected))
            return new VehicleActor("LVTEST_APPLICANT", "申请人", "210202", "TEST", true, false, false);
        if ("preaccept".equals(selected))
            return new VehicleActor("LVTEST_PRE_REVIEWER", "预审员", "210202", "TEST", false, true, false);
        if ("accept".equals(selected))
            return new VehicleActor("LVTEST_ACCEPT_REVIEWER", "受理员", "210202", "TEST", false, false, true);
        if ("local-admin".equals(selected))
            return new VehicleActor("LVTEST_LOCAL_ADMIN", "系统管理员", "210202", "TEST", false, false, false);
        throw new VehicleWorkflowException(403, "Only applicant, preaccept or accept test roles are available");
    }
}
