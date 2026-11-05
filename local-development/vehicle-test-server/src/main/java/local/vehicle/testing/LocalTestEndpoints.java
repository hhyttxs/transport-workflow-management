package local.vehicle.testing;

import com.domed.api.service.transportService.vehicleWorkflow.*;
import com.domed.common.result.Result;
import com.domed.domain.transportDomain.transportTemp.HyClbTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.persistence.*;
import java.util.*;

@RestController
@RequestMapping("/vehicleLocalTest")
public class LocalTestEndpoints {
    @Autowired private VehicleAccessPolicy access;
    @PersistenceContext private EntityManager em;
    @GetMapping("/session.json")
    public Result<Map<String,Object>> session() {
        VehicleActor actor = access.authenticate(null);
        Map<String,Object> data = new LinkedHashMap<>();
        data.put("database", "DISPOSABLE H2 / NOT ORACLE"); data.put("id",actor.id); data.put("name",actor.name);
        data.put("region",actor.region); data.put("applicant",actor.applicant); data.put("preaccept",actor.preAccept); data.put("accept",actor.accept);
        return Result.success(data);
    }
    @PostMapping("/inbox.json") @Transactional(readOnly=true)
    public Result<Map<String,Object>> inbox() {
        VehicleActor actor = access.authenticate(null);
        if (!actor.applicant && !actor.preAccept && !actor.accept) { Map<String,Object> empty=new LinkedHashMap<>();empty.put("rows",Collections.emptyList());return Result.success(empty); }
        String condition = actor.applicant ? "c.ownerId = :scope AND c.status IN (-1,-3,3,0,2)" : "c.region = :scope AND c.status = :state";
        TypedQuery<HyClbTemp> q = em.createQuery("SELECT c FROM HyClbTemp c WHERE " + condition + " ORDER BY c.updatedAt DESC,c.id DESC", HyClbTemp.class);
        q.setParameter("scope",actor.applicant ? actor.id : actor.region);
        if (!actor.applicant) q.setParameter("state",actor.preAccept ? 3 : 0);
        List<Map<String,Object>> rows=new ArrayList<>();
        for (HyClbTemp c:q.setMaxResults(100).getResultList()) {
            Map<String,Object> row=new LinkedHashMap<>();row.put("id",c.getId().toString());row.put("letterGid",c.getLetterGid());
            row.put("plateNumber",c.getPlateNumber());row.put("status",c.getStatus());rows.add(row);
        }
        Map<String,Object> data=new LinkedHashMap<>();data.put("rows",rows);return Result.success(data);
    }
}
