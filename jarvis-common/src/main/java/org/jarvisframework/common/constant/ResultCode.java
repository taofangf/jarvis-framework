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

/**
 * 返回码常量定义
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public final class ResultCode {
    /**
     * 通用成功返回码
     */
    public static final String PUB_SUCCESS_CODE = "000000";
    /**
     * 通用成功返回码信息
     */
    public static final String PUB_SUCCESS_CODE_DOC = "success";
    /**
     * 分布式锁异常返回码
     */
    public static final String DISTRIBUTED_LOCK_ERROR_CODE = "100000";
    /**
     * 分布式锁获取异常信息
     */
    public static final String DISTRIBUTED_LOCK_ERROR_CODE_DOC = "acquire distributed lock failed";
}
