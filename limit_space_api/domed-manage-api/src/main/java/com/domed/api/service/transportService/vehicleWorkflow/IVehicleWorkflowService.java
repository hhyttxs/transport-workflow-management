package com.domed.api.service.transportService.vehicleWorkflow;

import java.util.Map;
public interface IVehicleWorkflowService {
    Map<String, Object> options(VehicleActor actor);
    Map<String, Object> listDrafts(VehicleActor actor, Map<String, Object> input);
    Map<String, Object> getAcceptCarByLetterGid(VehicleActor actor, Map<String, Object> input);
    Map<String, Object> holdRoadFirstIssueTemp(VehicleActor actor, Map<String, Object> input);
    Map<String, Object> submit(VehicleActor actor, Map<String, Object> input);
    Map<String, Object> updateCarForecastAccept(VehicleActor actor, Map<String, Object> input);
    Map<String, Object> updateCarAccept(VehicleActor actor, Map<String, Object> input);
}
