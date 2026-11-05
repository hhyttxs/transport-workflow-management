package com.domed.util.entity;


import com.domed.common.result.Result;

import java.util.Map;

public interface ControllerFunctionResult {
    public abstract Result computedResult() throws Exception;
}
