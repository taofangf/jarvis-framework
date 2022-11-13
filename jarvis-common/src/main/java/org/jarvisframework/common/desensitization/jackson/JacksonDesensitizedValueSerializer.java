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

package org.jarvisframework.common.desensitization.jackson;

import cn.hutool.core.util.DesensitizedUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import org.jarvisframework.common.desensitization.DesensitizedCustomizer;
import org.jarvisframework.common.desensitization.annotation.Desensitization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

/**
 * Jackson实现字段值脱敏
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public class JacksonDesensitizedValueSerializer extends JsonSerializer implements ContextualSerializer {
    private static final Logger logger = LoggerFactory.getLogger(JacksonDesensitizedValueSerializer.class);

    /**
     * 脱敏标记注解
     * {@link Desensitization}
     */
    private Desensitization desensitization;

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (desensitization.desensitizedUsing() != DesensitizedCustomizer.DefaultDesensitized.class) {
            try {
                DesensitizedCustomizer desensitizedCustomizer = desensitization.desensitizedUsing().newInstance();
                gen.writeObject(desensitizedCustomizer.desensitized(value));
                return;
            } catch (Exception e) {
                logger.error("DesensitizedCustomizer initialize exception", e);
            }
        }
        gen.writeString(DesensitizedUtil.desensitized((CharSequence) value, desensitization.value()));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (Objects.nonNull(property)) {
            desensitization = property.getAnnotation(Desensitization.class);
            if (Objects.nonNull(desensitization)) {
                return this;
            }
            return prov.findValueSerializer(property.getType(), property);
        }
        return prov.findNullValueSerializer(null);
    }
}
