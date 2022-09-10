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

package org.jarvisframework.web.common.filter.authentication;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 认证相关属性配置
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "jarvis.authentication")
@Component
public class AuthenticationProperties {
    /**
     * 是否启用身份验证过滤器
     */
    private boolean enable;

    /**
     * 过滤器顺序
     */
    private int order;

    /**
     * 忽略的url，多个用|分割
     */
    private String ignoreUrlPattern = "";

    /**
     * 时间戳超时阈值,单位:ms
     */
    private long timestampThreshold = 300000L;

    public boolean isEnable() {
        return enable;
    }

    public AuthenticationProperties setEnable(boolean enable) {
        this.enable = enable;
        return this;
    }

    public int getOrder() {
        return order;
    }

    public AuthenticationProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    public String getIgnoreUrlPattern() {
        return ignoreUrlPattern;
    }

    public AuthenticationProperties setIgnoreUrlPattern(String ignoreUrlPattern) {
        this.ignoreUrlPattern = ignoreUrlPattern;
        return this;
    }

    public long getTimestampThreshold() {
        return timestampThreshold;
    }

    public AuthenticationProperties setTimestampThreshold(long timestampThreshold) {
        this.timestampThreshold = timestampThreshold;
        return this;
    }
}
