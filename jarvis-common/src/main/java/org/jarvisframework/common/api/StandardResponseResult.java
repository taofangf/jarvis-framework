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

package org.jarvisframework.common.api;

import java.util.StringJoiner;

/**
 * 标准结果返回
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public class StandardResponseResult<T> implements ResponseResult {
    /**
     * 返回码
     */
    private String resultCode;

    /**
     * 返回信息
     */
    private String resultInfo;

    /**
     * 返回内容
     */
    private T result;

    public String getResultCode() {
        return resultCode;
    }

    public StandardResponseResult<T> setResultCode(String resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public StandardResponseResult<T> setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
        return this;
    }

    public T getResult() {
        return result;
    }

    public StandardResponseResult<T> setResult(T result) {
        this.result = result;
        return this;
    }

    /**
     * 响应码
     *
     * @return 响应码
     */
    @Override
    public String responseCode() {
        return getResultCode();
    }

    /**
     * 响应消息
     *
     * @return 响应消息
     */
    @Override
    public String responseMessage() {
        return getResultInfo();
    }

    /**
     * 响应内容
     *
     * @return T
     */
    @Override
    public Object responseBody() {
        return getResult();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", StandardResponseResult.class.getSimpleName() + "[", "]")
                .add("resultCode='" + resultCode + "'")
                .add("resultInfo='" + resultInfo + "'")
                .add("result=" + result)
                .toString();
    }
}
