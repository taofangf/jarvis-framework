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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

/**
 * Swagger摘要信息配置类
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "jarvis.swagger")
@Component
public class SwaggerProperties {
    /**
     * 是否开启Swagger
     */
    private boolean enabled;

    /**
     * 标题(应用名称)
     */
    private String title;

    /**
     * Swagger扫描包
     */
    private String basePackage;

    /**
     * 版本号
     */
    private String version;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 联系人姓名
     */
    private String name;

    /**
     * 联系人地址
     */
    private String url;

    /**
     * 联系人邮箱
     */
    private String email;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SwaggerProperties.class.getSimpleName() + "[", "]")
                .add("enabled=" + enabled)
                .add("title='" + title + "'")
                .add("basePackage='" + basePackage + "'")
                .add("version='" + version + "'")
                .add("description='" + description + "'")
                .add("name='" + name + "'")
                .add("url='" + url + "'")
                .add("email='" + email + "'")
                .toString();
    }
}
