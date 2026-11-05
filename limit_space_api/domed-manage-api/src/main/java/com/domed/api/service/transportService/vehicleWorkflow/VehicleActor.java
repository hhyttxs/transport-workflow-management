package com.domed.api.service.transportService.vehicleWorkflow;

public final class VehicleActor {
    public final String id, name, region, department;
    public final boolean applicant, preAccept, accept;
    public VehicleActor(String id, String name, String region, String department,
                        boolean applicant, boolean preAccept, boolean accept) {
        this.id = id; this.name = name; this.region = region; this.department = department;
        this.applicant = applicant; this.preAccept = preAccept; this.accept = accept;
    }
}
