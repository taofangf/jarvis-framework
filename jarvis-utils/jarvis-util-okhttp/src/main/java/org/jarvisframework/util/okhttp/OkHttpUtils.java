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

package org.jarvisframework.util.okhttp;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * OkHttp工具类
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public class OkHttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(OkHttpUtils.class);

    /**
     * OkHttpClient客户端
     * {@link OkHttpClient}
     */
    public static OkHttpClient okHttpClient;

    public static void setOkHttpClient(OkHttpClient okHttpClient) {
        OkHttpUtils.okHttpClient = okHttpClient;
    }

    /**
     * Http Get请求
     *
     * @param baseUrl 请求url
     * @return Get请求返回
     */
    public static String get(String baseUrl) {
        return get(baseUrl, null, null);
    }

    /**
     * Http Get请求
     *
     * @param baseUrl    请求url
     * @param queryParas url参数
     * @param headers    请求头
     * @return Get请求返回
     */
    public static String get(String baseUrl, Map<String, String> queryParas, Map<String, String> headers) {
        Request request;
        String url = buildUrlWithQueryString(baseUrl, queryParas);
        if (logger.isDebugEnabled()) {
            logger.debug("http get  url:{}", url);
        }
        if (MapUtil.isEmpty(headers)) {
            request = new Request.Builder()
                    .url(url)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .headers(Headers.of(headers))
                    .build();
        }
        try {
            return okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            logger.error("Http request get error", e);
        }
        return null;
    }

    /**
     * Http Post请求
     *
     * @param baseUrl     请求url
     * @param queryParas  url参数
     * @param requestBody 请求body
     * @param headers     请求头
     * @return Post请求返回
     */
    public static String post(String baseUrl, Map<String, String> queryParas, String requestBody, Map<String, String> headers) {
        Request request;
        String url = buildUrlWithQueryString(baseUrl, queryParas);
//        RequestBody.create();
        return null;
    }

    /**
     * 构建请求url
     *
     * @param url        请求url
     * @param queryParas url参数
     * @return 拼接后的url
     */
    private static String buildUrlWithQueryString(String url, Map<String, String> queryParas) {
        if (MapUtil.isEmpty(queryParas)) {
            return url;
        }
        StringBuilder sb = new StringBuilder(url);
        boolean isFirst;
        if (url.indexOf('?') == -1) {
            isFirst = true;
            sb.append('?');
        } else {
            isFirst = false;
        }
        for (Map.Entry<String, String> entry : queryParas.entrySet()) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append('&');
            }
            String key = entry.getKey();
            String value = entry.getValue();
            if (StrUtil.isNotBlank(value)) {
                try {
                    value = URLEncoder.encode(value, CharsetUtil.UTF_8);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
            sb.append(key).append('=').append(value);
        }
        return sb.toString();
    }
}
