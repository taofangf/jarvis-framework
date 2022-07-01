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

package org.jarvisframework.distributed.lock.redis.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

import javax.annotation.Resource;

/**
 * redis distributed lock
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public class RedisLockConfiguration {

    /**
     * Redis实现分布式锁注册的Key值根节点
     */
    public static final String REDIS_DISTRIBUTED_LOCK_REGISTRY_KEY = "redis-distributed-lock";

    /**
     * TODO: 待实现Jedis RedisTemplate
     */
    @Resource
    RedisConnectionFactory connectionFactory;

    @Bean
    public RedisLockRegistry redisLockRegistry() {
        return new RedisLockRegistry(connectionFactory, REDIS_DISTRIBUTED_LOCK_REGISTRY_KEY);
    }

}
