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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import org.jarvisframework.common.desensitization.DesensitizedCustomizer;
import org.jarvisframework.common.desensitization.annotation.Sensitive;
import org.jarvisframework.common.desensitization.util.DesensitizedUtils;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonDesensitizedValueSerializer.class);

    /**
     * 脱敏标记注解
     * {@link Sensitive}
     */
    private Sensitive sensitive;

    /**
     * Method that can be called to ask implementation to serialize
     * values of type this serializer handles.
     *
     * @param value       Value to serialize; can <b>not</b> be null.
     * @param gen         Generator used to output resulting Json content
     * @param serializers Provider that can be used to get serializers for
     */
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        /**
         * 优先使用自定义实现脱敏
         */
        if (sensitive.desensitizedUsing() != Void.class) {
            try {
                DesensitizedCustomizer desensitizedCustomizer =
                        (DesensitizedCustomizer) sensitive.desensitizedUsing().newInstance();
                gen.writeObject(desensitizedCustomizer.desensitized(value));
                return;
            } catch (Exception e) {
                LOGGER.error("DesensitizedCustomizer initialize exception", e);
            }
        }
        gen.writeString(DesensitizedUtils.desensitized((CharSequence) value, sensitive.strategy()));
    }

    /**
     * Method called to see if a different (or differently configured) serializer
     * is needed to serialize values of specified property.
     * Note that instance that this method is called on is typically shared one and
     * as a result method should <b>NOT</b> modify this instance but rather construct
     * and return a new instance. This instance should only be returned as-is, in case
     * it is already suitable for use.
     *
     * @param prov     Serializer provider to use for accessing config, other serializers
     * @param property Method or field that represents the property
     *                 (and is used to access value to serialize).
     *                 Should be available; but there may be cases where caller cannot provide it and
     *                 null is passed instead (in which case impls usually pass 'this' serializer as is)
     * @return Serializer to use for serializing values of specified property;
     * may be this instance or a new instance.
     * @throws JsonMappingException
     */
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (Objects.nonNull(property)) {
            sensitive = property.getAnnotation(Sensitive.class);
            if (Objects.nonNull(sensitive)) {
                return this;
            }
            return prov.findValueSerializer(property.getType(), property);
        }
        return prov.findNullValueSerializer(null);
    }
}
