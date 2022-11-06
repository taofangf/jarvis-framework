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

package org.jarvisframework.common.desensitization.fastjson;

import cn.hutool.core.util.DesensitizedUtil;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import org.jarvisframework.common.desensitization.DesensitizedCustomizer;
import org.jarvisframework.common.desensitization.annotation.Desensitization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * FastJson Value处理过滤器
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public class FastJsonDesensitizedValueFilter implements ContextValueFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(FastJsonDesensitizedValueFilter.class);

    @Override
    public Object process(BeanContext context, Object object, String name, Object value) {
        Desensitization desensitization = context.getAnnation(Desensitization.class);
        if (Objects.nonNull(desensitization)) {
            if (desensitization.desensitizedUsing() != DesensitizedCustomizer.DefaultDesensitized.class) {
                try {
                    DesensitizedCustomizer desensitizedCustomizer = desensitization.desensitizedUsing().newInstance();
                    return desensitizedCustomizer.desensitized(value);
                } catch (Exception e) {
                    LOGGER.error("DesensitizedCustomizer initialize exception", e);
                }
            }
            DesensitizedUtil.DesensitizedType desensitizedType = desensitization.value();
            return DesensitizedUtil.desensitized((CharSequence) value, desensitizedType);
        }
        return value;
    }
}
