package com.domed.api.service.commonService;

import com.domed.common.result.Result;

import java.util.Map;

public interface ICommonDepartmentService {

    public Result<Map> departmentLevelList();
    public Result<Map> queryAuthLevelDepartment(Map infoMap);
    public Result<Map> companyDepartmetList();
}
