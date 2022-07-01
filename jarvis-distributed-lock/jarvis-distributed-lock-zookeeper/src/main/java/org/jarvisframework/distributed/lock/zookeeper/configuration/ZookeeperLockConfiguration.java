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

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.zookeeper.config.CuratorFrameworkFactoryBean;
import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;

/**
 * Curator implements zookeeper distributedLock auto configuration
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
@EnableConfigurationProperties(CuratorProperties.class)
public class ZookeeperLockConfiguration {

    /**
     * Curator 配置信息注入
     *
     * @return {@link CuratorProperties}
     */
    @Bean
    @ConditionalOnMissingBean(CuratorProperties.class)
    public CuratorProperties curatorProperties() {
        return new CuratorProperties();
    }

    /**
     * 重试策略实现注入
     * 防止Zookeeper假死导致分布式锁的失效
     *
     * @param curatorProperties {@link CuratorProperties}
     * @return {@link RetryPolicy}
     */
    @Bean
    @ConditionalOnMissingBean(RetryPolicy.class)
    public RetryPolicy retryPolicy(CuratorProperties curatorProperties) {
        return new ExponentialBackoffRetry(curatorProperties.getBaseSleepTimeMs(), curatorProperties.getMaxRetries());
    }

    /**
     * Curator Client 注入
     *
     * @param curatorProperties {@link CuratorProperties}
     * @param retryPolicy       {@link RetryPolicy}
     * @return {@link CuratorFrameworkFactoryBean}
     */
    @Bean
    @ConditionalOnMissingBean(CuratorFrameworkFactoryBean.class)
    public CuratorFrameworkFactoryBean curatorFrameworkFactoryBean(CuratorProperties curatorProperties, RetryPolicy retryPolicy) {
        return new CuratorFrameworkFactoryBean(curatorProperties.getConnectString(), retryPolicy);
    }

    /**
     * ZookeeperLock 实例注入
     *
     * @param curatorFramework {@link CuratorFramework}
     * @return {@link ZookeeperLockRegistry}
     */
    @Bean
    @ConditionalOnMissingBean(ZookeeperLockRegistry.class)
    public ZookeeperLockRegistry zookeeperLockRegistry(CuratorFramework curatorFramework) {
        return new ZookeeperLockRegistry(curatorFramework);
    }
}
