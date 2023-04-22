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

package org.jarvisframework.util.okhttp.configuration;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.jarvisframework.util.okhttp.OkHttpUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * OkHttp外部化配置类
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
@Configuration
@ComponentScan("org.jarvisframework.util.okhttp")
public class OkHttpConfiguration {
    /**
     * OkHttp客户端实例
     *
     * @param properties {@link OkHttpProperties}
     * @return {@link OkHttpClient}
     */
    @Bean
    public OkHttpClient okHttpClient(OkHttpProperties properties) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().retryOnConnectionFailure(false)
                .connectionPool(connectionPool(properties))
                .connectTimeout(properties.getConnectTimeout(), TimeUnit.MINUTES)
                .readTimeout(properties.getReadTimeout(), TimeUnit.MINUTES)
                .writeTimeout(properties.getWriteTimeout(), TimeUnit.MINUTES).build();
        OkHttpUtils.setOkHttpClient(okHttpClient);
        return okHttpClient;
    }

    /**
     * OkHttp连接池
     *
     * @param properties {@link OkHttpProperties}
     * @return {@link ConnectionPool}
     */
    @Bean
    public ConnectionPool connectionPool(OkHttpProperties properties) {
        return new ConnectionPool(properties.getConnectionPool().getMaxIdleConnections(),
                properties.getConnectionPool().getKeepAliveDuration(), TimeUnit.SECONDS);
    }
}
