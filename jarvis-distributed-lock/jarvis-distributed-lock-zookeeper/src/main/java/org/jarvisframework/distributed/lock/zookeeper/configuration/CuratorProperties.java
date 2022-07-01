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

package org.jarvisframework.distributed.lock.zookeeper.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

/**
 * Curator 属性配置类
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
@Component
@ConfigurationProperties(prefix = "jarvis.curator")
public class CuratorProperties {
    /**
     * zookeeper主机信息 ip1:port1,ip2:port2
     */
    private String connectString;

    /**
     * 重试连接zookeeper等待的时间(毫秒)
     * initial amount of time to wait between retries
     */
    private int baseSleepTimeMs = 1000;

    /**
     * 最大重试次数
     * max number of times to retry
     */
    private int maxRetries = 5;

    public String getConnectString() {
        return connectString;
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }

    public int getBaseSleepTimeMs() {
        return baseSleepTimeMs;
    }

    public void setBaseSleepTimeMs(int baseSleepTimeMs) {
        this.baseSleepTimeMs = baseSleepTimeMs;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CuratorProperties.class.getSimpleName() + "[", "]")
                .add("connectString='" + connectString + "'")
                .add("baseSleepTimeMs=" + baseSleepTimeMs)
                .add("maxRetries=" + maxRetries)
                .toString();
    }
}
