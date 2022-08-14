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

package org.jarvisframework.common.exception;

import org.jarvisframework.common.domain.Response;

/**
 * 业务异常
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public class BusinessException extends BaseException {

    /**
     * 公用异常构造方法
     *
     * @param response {@link Response}
     */
    public BusinessException(Response response) {
        super(response);
    }

    /**
     * 公用异常构造方法
     *
     * @param resultCode 返回码
     * @param resultInfo 返回信息
     */
    public BusinessException(String resultCode, String resultInfo) {
        super(resultCode, resultInfo);
    }
}
