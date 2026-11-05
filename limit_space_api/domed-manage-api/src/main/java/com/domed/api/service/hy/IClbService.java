package com.domed.api.service.hy;

import com.domed.common.result.Result;

import java.util.Map;

public interface IClbService {

    Result saveOrUpdateClb(Map infoMap);

    Result queryClbList(Map infoMap);

    Result queryDraftList(Map infoMap);

    Result getClbInfoByGid(Map infoMap);


}
