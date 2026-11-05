package com.domed.util.entity;

import com.alibaba.fastjson.JSONObject;
import com.domed.common.json.JsonUtil;
import com.domed.common.result.Result;
import com.domed.common.result.ResultCode;
import com.domed.core.exception.ApplicationException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
public class ControllerResult {
    private ControllerFunctionResult controllerFunctionResult;

    public ControllerResult(ControllerFunctionResult controllerFunctionResult){
        this.controllerFunctionResult = controllerFunctionResult;
    }

    /**
     * @Title: 新框架controller返回
     * @Description:
     * @author 高杨
     * @date 2022/2/27 19:08
     * @return:
     */
    public <T> Result<T> computed(){
        JSONObject jo = new JSONObject();
        String code = "0";
        String msg = "操作成功";
        ResultCode rce = ResultCode.SUCCESS;
        Result<T> result = null;
        try {
            result = controllerFunctionResult.computedResult();
        }  catch (ApplicationException e) {
            code = e.getCode();
            msg = e.getMessage();
            e.printStackTrace();
        }catch (RetryableException e) {
            code = "-1";
            msg = "请求超时";
            log.error(e.getMessage());
        } catch (RuntimeException e) {
            code = "-1";
            msg = "操作异常:" + e.getMessage();
            log.error(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            code = "-1";
            msg = "操作异常:" + e.getMessage();
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return Result.result(code, msg, result == null ? null : result.getData());
    }
}
