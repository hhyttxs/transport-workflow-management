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
public class ControllerWrapper {
    private Logger logger;

    private HttpServletRequest request;

    private HttpServletResponse response;

    private ControllerFunctionWrapper controllerFunctionWrapper;

    public ControllerWrapper( Logger logger, HttpServletRequest request,HttpServletResponse response,ControllerFunctionWrapper controllerFunctionWrapper){
        this.logger = logger;
        this.request = request;
        this.response = response;
        this.controllerFunctionWrapper = controllerFunctionWrapper;
    }

    public ControllerWrapper(ControllerFunctionWrapper controllerFunctionWrapper){
        this.controllerFunctionWrapper = controllerFunctionWrapper;
    }

    public void computed(){
        {
            JSONObject jo = new JSONObject();
            String code = "0";
            String msg = "操作成功";
            Map map= null;
            try {
                map = controllerFunctionWrapper.computed();
            }  catch (ApplicationException e) {
                code = e.getCode();
                msg = e.getMessage();
                e.printStackTrace();
            }catch (RetryableException e) {
                code = "-1";
                msg = "请求超时";
                logger.error(e.getMessage());
            } catch (RuntimeException e) {
                code = "-1";
                msg = "服务繁忙请稍后再试";
                logger.error(e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                code = "-1";
                msg = "操作异常";
                logger.error(e.getMessage());
                e.printStackTrace();
            } finally {
                jo.put("code", code);
                jo.put("msg", msg);
                jo.put("data", map);
                JsonUtil.output(request, response, jo);
            }
        }
    }
}
