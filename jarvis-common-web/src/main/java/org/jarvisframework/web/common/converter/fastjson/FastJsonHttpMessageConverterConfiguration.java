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

package org.jarvisframework.web.common.converter.fastjson;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.jarvisframework.common.desensitization.fastjson.FastJsonDesensitizedValueFilter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

/**
 * Fastjson消息转换器配置
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public class FastJsonHttpMessageConverterConfiguration {
    /**
     * Fastjson消息准换配置实例化
     *
     * @return {@link HttpMessageConverters}
     */
    public HttpMessageConverters fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.PrettyFormat);
        config.setSerializeFilters(new FastJsonDesensitizedValueFilter());

        // TODO: 2022/11/13 外放接口自定义修改配置 
        converter.setFastJsonConfig(config);

        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        mediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        mediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        mediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        mediaTypes.add(MediaType.APPLICATION_PDF);
        mediaTypes.add(MediaType.APPLICATION_RSS_XML);
        mediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        mediaTypes.add(MediaType.APPLICATION_XML);
        mediaTypes.add(MediaType.IMAGE_GIF);
        mediaTypes.add(MediaType.IMAGE_JPEG);
        mediaTypes.add(MediaType.IMAGE_PNG);
        mediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        mediaTypes.add(MediaType.TEXT_HTML);
        mediaTypes.add(MediaType.TEXT_MARKDOWN);
        mediaTypes.add(MediaType.TEXT_PLAIN);
        mediaTypes.add(MediaType.TEXT_XML);
        converter.setSupportedMediaTypes(mediaTypes);

        return new HttpMessageConverters(converter);
    }
}