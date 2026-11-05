package com.domed.api.controller.transportController.vehicleWorkflow;

import com.domed.api.service.transportService.vehicleWorkflow.VehicleWorkflowException;
import com.domed.common.result.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataAccessException;
import javax.persistence.PersistenceException;

@RestControllerAdvice(assignableTypes = VehicleWorkflowController.class)
public class VehicleWorkflowErrorHandler {
    @ExceptionHandler(VehicleWorkflowException.class)
    public ResponseEntity<Result<Object>> workflow(VehicleWorkflowException e) {
        return ResponseEntity.status(e.getStatus()).body(Result.result("V" + e.getStatus(), e.getMessage(), null));
    }
    @ExceptionHandler({PersistenceException.class, DataAccessException.class})
    public ResponseEntity<Result<Object>> database(RuntimeException e) {
        return ResponseEntity.status(503).body(Result.result("V503", "数据库或车辆业务结构不可用，请联系管理员核对部署要求", null));
    }
}
