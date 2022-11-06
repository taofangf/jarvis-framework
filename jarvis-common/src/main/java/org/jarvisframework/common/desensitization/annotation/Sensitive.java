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

package org.jarvisframework.common.desensitization.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.jarvisframework.common.desensitization.DesensitizedCustomizer;
import org.jarvisframework.common.desensitization.enums.DesensitizedTypeEnum;
import org.jarvisframework.common.desensitization.jackson.JacksonDesensitizedValueSerializer;

import java.lang.annotation.*;

/**
 * 数据脱敏标记注解
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
@JacksonAnnotationsInside
@JsonSerialize(using = JacksonDesensitizedValueSerializer.class)
public @interface Sensitive {
    /**
     * 字段模糊化策略,优先级低于{@link #desensitizedUsing()}
     *
     * @return {@link DesensitizedTypeEnum}
     */
    DesensitizedTypeEnum strategy() default DesensitizedTypeEnum.NONE;

    /**
     * 自定义脱敏实现类,优先级最高。 有自定义实现优先使用自定义实现
     *
     * @return {@link DesensitizedCustomizer}的实现类
     * @see DesensitizedCustomizer
     */
    Class<?> desensitizedUsing() default Void.class;
}
