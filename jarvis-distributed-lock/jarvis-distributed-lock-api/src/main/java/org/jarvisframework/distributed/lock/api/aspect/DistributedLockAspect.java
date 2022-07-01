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

package org.jarvisframework.distributed.lock.api.aspect;

import cn.hutool.extra.spring.SpringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jarvisframework.common.constant.ResponseConstants;
import org.jarvisframework.common.exception.DistributedLockException;
import org.jarvisframework.distributed.lock.api.annotation.DistributedLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;

/**
 * 分布式锁切面
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
@Aspect
@Component
public class DistributedLockAspect {

    private static final Logger logger = LoggerFactory.getLogger(DistributedLock.class);

    /**
     * 注解切入点
     */
    @Pointcut("@annotation(org.jarvisframework.distributed.lock.api.annotation.DistributedLock)")
    public void distributedLock() {
    }

    /**
     * 环绕通知
     *
     * @param joinPoint {@link ProceedingJoinPoint}
     * @return Object
     * @throws Throwable
     */
    @Around("distributedLock()")
    public Object aroundDistributedLock(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        DistributedLock annotation = signature
                .getMethod()
                .getAnnotation(DistributedLock.class);

        LockRegistry lockRegistry = getLockRegistryByLockType(annotation.lockType());

        Lock lock = lockRegistry.obtain(annotation.lockKey());

        try {
            if (lock.tryLock(annotation.time(), annotation.timeUnit())) {
                return joinPoint.proceed();
            } else {
                logger.error("acquire distributed lock failed.");
                throw new DistributedLockException(ResponseConstants.DISTRIBUTED_LOCK_ERROR);
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 根据分布式锁类型获取实现
     *
     * @param lockType 分布式锁类型
     * @return {@link LockRegistry}
     */
    private LockRegistry getLockRegistryByLockType(String lockType) {
        return SpringUtil.getBean(lockType, LockRegistry.class);
    }
}
