package com.domed.api.service.hy;

import com.domed.common.result.Result;

import java.util.Map;

public interface IDlhwjyxkService {

    Result saveOrUpdateDlhwjyxk(Map infoMap);
    Result queryDraftList(Map infoMap);
    Result getDlhwjyxkInfoByGid(Map infoMap);

}
