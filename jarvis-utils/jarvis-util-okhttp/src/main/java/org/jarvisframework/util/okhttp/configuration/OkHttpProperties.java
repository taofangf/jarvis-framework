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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * OkHttp外部化配置参数
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "jarvis.okhttp")
@Component
public class OkHttpProperties {
    /**
     * 连接超时时间，单位（秒）默认15秒
     */
    private Integer connectTimeout = 15;
    /**
     * 读取超时时间，单位（秒）默认15秒
     */
    private Integer readTimeout = 15;
    /**
     * 写超时时间，单位（秒）默认15秒
     */
    private Integer writeTimeout = 15;
    /**
     * 连接池属性配置
     */
    private ConnectionPoolProperties connectionPool;

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public OkHttpProperties setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public OkHttpProperties setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public Integer getWriteTimeout() {
        return writeTimeout;
    }

    public OkHttpProperties setWriteTimeout(Integer writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public ConnectionPoolProperties getConnectionPool() {
        return connectionPool;
    }

    public OkHttpProperties setConnectionPool(ConnectionPoolProperties connectionPool) {
        this.connectionPool = connectionPool;
        return this;
    }

    /**
     * OkHttp连接池配置
     *
     * @since 1.0.0
     */
    public static class ConnectionPoolProperties {

        /**
         * 最大空闲连接数 默认5
         */
        private Integer maxIdleConnections = 5;
        /**
         * 保活时间 单位（秒）默认5秒
         */
        private Integer keepAliveDuration = 5;

        public Integer getMaxIdleConnections() {
            return maxIdleConnections;
        }

        public ConnectionPoolProperties setMaxIdleConnections(Integer maxIdleConnections) {
            this.maxIdleConnections = maxIdleConnections;
            return this;
        }

        public Integer getKeepAliveDuration() {
            return keepAliveDuration;
        }

        public ConnectionPoolProperties setKeepAliveDuration(Integer keepAliveDuration) {
            this.keepAliveDuration = keepAliveDuration;
            return this;
        }
    }
}
