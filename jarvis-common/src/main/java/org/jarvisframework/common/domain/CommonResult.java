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

package org.jarvisframework.common.domain;

import com.alibaba.fastjson.annotation.JSONField;
import org.jarvisframework.common.api.Result;
import org.jarvisframework.common.enums.ResultCodeEnum;
import org.jarvisframework.common.exception.BaseException;

import java.util.StringJoiner;

/**
 * 通用返回
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public class CommonResult<T> {
    /**
     * 返回码
     */
    private String resultCode;

    /**
     * 返回信息
     */
    private String resultInfo;

    /**
     * 返回数据泛型
     */
    private T data;

    /**
     * 是否成功
     */
    @JSONField(serialize = false)
    private boolean success;

    /**
     * 默认构造
     *
     * @param result  {@link Result}
     * @param data    {@link #data}
     * @param success {@link #success}
     */
    public CommonResult(Result result, T data, boolean success) {
        this.resultCode = result.getResultCode();
        this.resultInfo = result.getResultInfo();
        this.data = data;
        this.success = success;
    }

    /**
     * 默认构造
     *
     * @param resultCode {@link #resultCode}
     * @param resultInfo {@link #resultInfo}
     * @param data       {@link #data}
     * @param success    {@link #success}
     */
    public CommonResult(String resultCode, String resultInfo, T data, boolean success) {
        this.resultCode = resultCode;
        this.resultInfo = resultInfo;
        this.data = data;
        this.success = success;
    }

    /**
     * 无响应体的成功响应
     *
     * @return 无响应体的成功响应
     */
    public static CommonResult ofSuccess() {
        return ofSuccess(null);
    }

    /**
     * 响应成功
     *
     * @param data 返回对象
     * @param <T>  data
     * @return 响应成功
     */
    public static <T> CommonResult<T> ofSuccess(T data) {
        return new CommonResult(ResultCodeEnum.PUB_SUCCESS, data, true);
    }

    /**
     * 响应成功
     *
     * @param result {@link Result}
     * @param data   返回对象
     * @param <T>    data
     * @return 响应成功
     */
    public static <T> CommonResult<T> ofSuccess(Result result, T data) {
        return new CommonResult(result, data, true);
    }

    /**
     * 指定返回码、返回信息的成功响应
     *
     * @param resultCode 返回成功码
     * @param resultInfo 返回成功信息
     * @param data       返回对象
     * @param <T>        data
     * @return 指定返回码、返回信息的成功响应
     */
    public static <T> CommonResult<T> ofSuccess(String resultCode, String resultInfo, T data) {
        return new CommonResult(resultCode, resultInfo, data, true);
    }

    /**
     * 无响应体的失败响应
     *
     * @return 无响应体的失败响应
     */
    public static CommonResult ofFail() {
        return ofFail(null);
    }

    /**
     * 响应失败
     *
     * @param data 返回对象
     * @param <T>  data
     * @return 响应失败
     */
    public static <T> CommonResult<T> ofFail(T data) {
        return new CommonResult(ResultCodeEnum.PUB_ERROR, data, false);
    }

    /**
     * 响应失败
     *
     * @param result {@link Result}
     * @param data   返回对象
     * @param <T>    data
     * @return 响应失败
     */
    public static <T> CommonResult<T> ofFail(Result result, T data) {
        return new CommonResult(result, data, false);
    }

    /**
     * 失败响应
     *
     * @param result {@link Result}
     * @return {@link CommonResult}
     */
    public static CommonResult ofFail(Result result) {
        return new CommonResult(result.getResultCode(), result.getResultInfo(), null, false);
    }

    /**
     * 指定返回码、返回信息的失败响应
     *
     * @param resultCode 返回失败码
     * @param resultInfo 返回失败信息
     * @param data       返回对象
     * @param <T>        data
     * @return 指定返回码、返回信息的失败响应
     */
    public static <T> CommonResult<T> ofFail(String resultCode, String resultInfo, T data) {
        return new CommonResult(resultCode, resultInfo, data, false);
    }

    /**
     * 异常信息返回
     *
     * @param data 异常
     * @param <T>  data
     * @return 异常信息返回
     */
    public static <T extends BaseException> CommonResult<T> ofException(T data) {
        return new CommonResult(data.getResultCode(), data.getResultInfo(), null, false);
    }

    public String getResultCode() {
        return resultCode;
    }

    public CommonResult<T> setResultCode(String resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public CommonResult<T> setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
        return this;
    }

    public T getData() {
        return data;
    }

    public CommonResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public CommonResult<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CommonResult.class.getSimpleName() + "[", "]")
                .add("resultCode='" + resultCode + "'")
                .add("resultInfo='" + resultInfo + "'")
                .add("data=" + data)
                .add("success=" + success)
                .toString();
    }
}