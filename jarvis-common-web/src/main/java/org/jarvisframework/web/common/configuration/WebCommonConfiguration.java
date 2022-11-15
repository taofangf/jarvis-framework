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

package org.jarvisframework.web.common.configuration;

import org.jarvisframework.web.common.converter.fastjson.FastJsonHttpMessageConverterConfiguration;
import org.jarvisframework.web.common.filter.authentication.AuthenticationFilter;
import org.jarvisframework.web.common.filter.authentication.AuthenticationProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * 基础框架Web配置类
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
@ComponentScan(basePackages = "org.jarvisframework.web.common")
public class WebCommonConfiguration {
    /**
     * FastJson解析器
     *
     * @return {@link HttpMessageConverters}
     */
    @Bean
    @ConditionalOnProperty(value = "jarvis.fastjson.enable", havingValue = "true")
    public HttpMessageConverters fastJsonHttpMessageConvert() {
        FastJsonHttpMessageConverterConfiguration messageConverterConfiguration =
                new FastJsonHttpMessageConverterConfiguration();
        return messageConverterConfiguration.fastJsonHttpMessageConverter();
    }

    @Bean
    @ConditionalOnProperty(value = "jarvis.authentication.enable", havingValue = "true")
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilterRegistrationBean(
            AuthenticationProperties authenticationProperties) {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setName("AuthenticationFilter");
        registrationBean.setFilter(new AuthenticationFilter(authenticationProperties));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(authenticationProperties.getOrder());
        return registrationBean;
    }
}
