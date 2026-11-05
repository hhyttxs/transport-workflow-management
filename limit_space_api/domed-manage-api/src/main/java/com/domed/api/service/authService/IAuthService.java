package com.domed.api.service.authService;

import com.domed.api.util.entity.DataAuthDTO;
import com.domed.api.util.entity.DataAuthModelDTO;
import com.domed.common.result.Result;
import com.domed.domain.DataAuth;
import com.domed.util.UserDTO;

import java.util.List;
import java.util.Map;

public interface IAuthService {
    public List getStaffFuntionModelList(String staff_id, String menu_id);

    public Map getStaffFuntionModelList(Map infoMap);

    public DataAuthDTO getStaffDataAuthMap(String staff_id, String functionModel_id);

    public void addDataAuth(DataAuthModelDTO dataAuthModelDTO);

    public void setDataAuth(DataAuth dataAuth, String staff_id);

    public Map browseAuthList(Map infoMap);

    public Map getTopDepartment(String staff_id);

    public Map formatStaffDepartmentMap(List<String> levelTops);

    public List<Map> getStaffDepartmentList(String staff_id);

    public List<Map> getStaffDepartmentListBylevelTops(String levelTops);

    public Map getStaffFuntionDepartmentList(Map infoMap);

}
