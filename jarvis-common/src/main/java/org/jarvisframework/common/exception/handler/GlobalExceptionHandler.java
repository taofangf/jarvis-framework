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

package org.jarvisframework.common.exception.handler;

import org.jarvisframework.common.domain.CommonResult;
import org.jarvisframework.common.enums.ResultCodeEnum;
import org.jarvisframework.common.exception.BaseException;
import org.jarvisframework.common.exception.BusinessException;
import org.jarvisframework.common.exception.DistributedLockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 分布式锁异常处理
     *
     * @param e {@link DistributedLockException}
     * @return {@link CommonResult}
     */
    @ExceptionHandler(DistributedLockException.class)
    public CommonResult distributedExceptionHandler(DistributedLockException e) {
        LOGGER.error(e.getResultInfo());
        return CommonResult.ofFail(e.getResultCode(), e.getResultInfo(), null);
    }

    /**
     * 业务异常处理
     *
     * @param e {@link BusinessException}
     * @return {@link CommonResult}
     */
    @ExceptionHandler(BusinessException.class)
    public CommonResult businessExceptionHandler(BusinessException e) {
        LOGGER.error(e.getResultInfo());
        return CommonResult.ofFail(e.getResultCode(), e.getResultInfo(), null);
    }

    /**
     * 基础运行时异常处理
     *
     * @param e {@link BaseException}
     * @return {@link CommonResult}
     */
    @ExceptionHandler(BaseException.class)
    public CommonResult baseExceptionHandler(BaseException e) {
        LOGGER.error(e.getResultInfo());
        return CommonResult.ofFail(ResultCodeEnum.SYSTEM_EXCEPTION);
    }

    /**
     * 兜底异常捕获处理
     *
     * @param e 未知异常
     * @return {@link CommonResult}
     */
    @ExceptionHandler(Exception.class)
    public CommonResult exceptionHandler(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return CommonResult.ofFail(ResultCodeEnum.SYSTEM_EXCEPTION);
    }
}
