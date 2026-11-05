package com.domed.api.controller.hy;

import com.domed.api.service.hy.IDlhwjyxkService;
import com.domed.common.result.Result;
import com.domed.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RefreshScope
@RestController
@CrossOrigin
@RequestMapping("/dlhwjyxk")
public class DlhwjyxkController {

    @Autowired
    private IDlhwjyxkService dlhwjyxkService;

    /**
     * 保存道路货物运输经营许可信息
     */

    @RequestMapping(value = "saveOrUpdateDlhwjyxk.json", method = {RequestMethod.POST}, produces = "application/json")
    public Result<Map> saveOrUpdateDlhwjyxk(@RequestBody Map infoMap) {
        return CommonUtil.controllerResult(() -> dlhwjyxkService.saveOrUpdateDlhwjyxk(infoMap));
    }

    @RequestMapping(value = "queryDraftList.json", method = {RequestMethod.POST}, produces = "application/json")
    public Result<Map> queryDraftList(@RequestBody Map infoMap) {
        return CommonUtil.controllerResult(() -> dlhwjyxkService.queryDraftList(infoMap));
    }
    @RequestMapping(value = "getDlhwjyxkInfoByGid.json", method = {RequestMethod.POST}, produces = "application/json"
    )
    public Result<Map> getDlhwjyxkInfoByGid(@RequestBody Map infoMap) {
        return CommonUtil.controllerResult(() -> dlhwjyxkService.getDlhwjyxkInfoByGid(infoMap));
    }

}
