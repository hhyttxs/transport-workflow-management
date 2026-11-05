package com.domed.util.entity;


import com.domed.common.result.Result;

import java.util.Map;

public interface ControllerFunctionWrapper {
    public abstract Map computed() throws Exception;
}
