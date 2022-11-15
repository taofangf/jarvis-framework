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

import org.jarvisframework.common.api.Result;

/**
 * 公用基础异常
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = -3030358614350723410L;
    /**
     * 返回码
     */
    private final String resultCode;

    /**
     * 返回信息
     */
    private final String resultInfo;

    /**
     * 公用异常构造方法
     *
     * @param result {@link Result}
     */
    public BaseException(Result result) {
        super(result.getResultInfo());
        this.resultCode = result.getResultCode();
        this.resultInfo = result.getResultInfo();
    }

    /**
     * 公用异常构造方法
     *
     * @param resultCode {@link #resultCode}
     * @param resultInfo {@link #resultInfo}
     */
    public BaseException(String resultCode, String resultInfo) {
        super(resultInfo);
        this.resultCode = resultCode;
        this.resultInfo = resultInfo;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultInfo() {
        return resultInfo;
    }
}
