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

package org.jarvisframework.common.constant;

import org.jarvisframework.common.domain.Response;

/**
 * 通用返回常量表
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public class ResponseConstants {
    /**
     * 公用返回前缀
     */
    public static final String PUBLIC_RESPONSE_PREFIX = "PUB-";

    /**
     * 通用成功返回对象
     */
    public static final Response PUB_SUCCESS = new Response(PUBLIC_RESPONSE_PREFIX + ResultCode.PUB_SUCCESS_CODE, ResultCode.PUB_SUCCESS_CODE_DOC);
}
