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

package org.jarvisframework.distributed.lock.api.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁注解
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DistributedLock {
    /**
     * 分布式锁Key值
     *
     * @return 分布式锁Key值
     */
    String lockKey();

    /**
     * 分布式锁类型
     * <p>
     * 支持多种实现(Redis、Zookeeper)
     *
     * @return 分布式锁类型
     */
    String lockType() default "";

    /**
     * 尝试获取锁的等待时间,时间单位{@link DistributedLock#timeUnit()}
     *
     * @return 尝试获取锁的等待时间
     */
    long time() default 1L;

    /**
     * 时间单位,默认秒
     *
     * @return {@link TimeUnit}
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}

