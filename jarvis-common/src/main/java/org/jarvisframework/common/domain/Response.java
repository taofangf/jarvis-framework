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

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * 返回信息
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public class Response implements Serializable {

    private static final long serialVersionUID = -2477352648549065232L;
    /**
     * 返回码
     */
    private String resultCode;

    /**
     * 返回信息
     */
    private String resultInfo;

    public Response() {
    }

    public Response(String resultCode, String resultInfo) {
        this.resultCode = resultCode;
        this.resultInfo = resultInfo;
    }

    public String getResultCode() {
        return resultCode;
    }

    public Response setResultCode(String resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public Response setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Response.class.getSimpleName() + "[", "]")
                .add("resultCode='" + resultCode + "'")
                .add("resultInfo='" + resultInfo + "'")
                .toString();
    }
}
