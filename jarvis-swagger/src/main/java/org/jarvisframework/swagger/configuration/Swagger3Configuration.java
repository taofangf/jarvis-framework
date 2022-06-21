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

package org.jarvisframework.swagger.configuration;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger3 配置类
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public class Swagger3Configuration {

    @Bean
    public DocketConfig docketConfig() {
        return new DocketConfig();
    }

    @Bean
    public Docket createRestApi(DocketConfig config) {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo(config))
                .enable(config.isEnabled())
                .select()
                .apis(RequestHandlerSelectors.basePackage(config.getBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(DocketConfig config) {
        return new ApiInfoBuilder()
                .title(config.getTitle())
                .contact(new Contact(config.getName(), config.getUrl(), config.getEmail()))
                .version(config.getVersion())
                .description(config.getDescription())
                .build();
    }
}
