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

package org.jarvisframework.web.common.webserver;

import cn.hutool.core.util.StrUtil;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * Tomcat多端口实现
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
@Service
public class TomcatMultiPortCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Resource
    private TomcatMultiPortProperties tomcatMultiPortProperties;

    /**
     * Customize the specified {@link WebServerFactory}.
     *
     * @param factory the web server factory to customize
     */
    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        if (Objects.nonNull(tomcatMultiPortProperties) && StrUtil.isNotBlank(tomcatMultiPortProperties.getPorts())) {
            String[] ports = tomcatMultiPortProperties.getPorts().split(StrUtil.COMMA);
            for (int i = 0; i < ports.length; i++) {
                Connector connector = new Connector();
                connector.setPort(Integer.parseInt(ports[i]));
                factory.addAdditionalTomcatConnectors(connector);
            }
        }
    }
}
