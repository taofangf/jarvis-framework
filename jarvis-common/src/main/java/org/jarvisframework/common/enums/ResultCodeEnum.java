/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jarvisframework.common.enums;

import org.jarvisframework.common.api.Result;

/**
 * 返回码枚举类
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public enum ResultCodeEnum implements Result {
    /**
     * 通用成功返回码
     */
    PUB_SUCCESS("000000", "Success"),
    /**
     * 通用失败返回码
     */
    PUB_ERROR("500000", "Error"),
    /**
     * 系统异常
     */
    SYSTEM_EXCEPTION("500001", "System Error"),
    /**
     * 分布式锁获取异常信息
     */
    DISTRIBUTED_LOCK_ERROR_CODE("500002", "Acquire distributed lock failed."),
    ;

    /**
     * 返回码
     */
    private String resultCode;

    /**
     * 返回信息
     */
    private String resultInfo;

    ResultCodeEnum(String resultCode, String resultInfo) {
        this.resultCode = resultCode;
        this.resultInfo = resultInfo;
    }

    @Override
    public String getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultInfo() {
        return resultInfo;
    }
}
