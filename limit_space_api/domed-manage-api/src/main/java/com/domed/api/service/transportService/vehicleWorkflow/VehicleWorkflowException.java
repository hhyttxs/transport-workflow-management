package com.domed.api.service.transportService.vehicleWorkflow;

public class VehicleWorkflowException extends RuntimeException {
    private final int status;
    public VehicleWorkflowException(int status, String message) { super(message); this.status = status; }
    public int getStatus() { return status; }
    public static void require(boolean condition, int status, String message) {
        if (!condition) throw new VehicleWorkflowException(status, message);
    }
}
