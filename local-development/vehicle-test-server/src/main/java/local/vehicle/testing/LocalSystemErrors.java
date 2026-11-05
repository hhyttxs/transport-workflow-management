package local.vehicle.testing;
import com.domed.common.result.Result;
import com.domed.api.service.transportService.vehicleWorkflow.VehicleWorkflowException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataAccessException;
@RestControllerAdvice(assignableTypes=LocalSystemEndpoints.class)
public class LocalSystemErrors {
    @ExceptionHandler(VehicleWorkflowException.class)
    public ResponseEntity<Result<Object>> validation(VehicleWorkflowException e){return ResponseEntity.status(e.getStatus()).body(Result.result("L"+e.getStatus(),e.getMessage(),null));}
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Result<Object>> database(DataAccessException e){return ResponseEntity.status(409).body(Result.result("L409","数据校验未通过，本次操作未保存",null));}
}
