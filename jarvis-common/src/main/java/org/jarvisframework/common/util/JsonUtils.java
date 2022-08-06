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

package org.jarvisframework.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

/**
 * JSON工具类
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public class JsonUtils {

    /**
     * JSON转换对象
     *
     * @param json JSON字符串
     * @param type 返回对象
     * @param <T>  type
     * @return JSON转换对象
     */
    public static <T> T parseToObject(String json, Class<T> type) {
        return JSON.parseObject(json, new TypeReference<T>(type) {
        });
    }

    /**
     * JSON转指定类型Map
     *
     * @param json      JSON字符串
     * @param keyType   key类型
     * @param valueType value类型
     * @param <K>       keyType
     * @param <V>       valueType
     * @return JSON转指定类型Map
     */
    public static <K, V> Map<K, V> parseToMap(String json, Class<K> keyType, Class<V> valueType) {
        return JSON.parseObject(json, new TypeReference<Map<K, V>>(keyType, valueType) {
        });
    }
}
