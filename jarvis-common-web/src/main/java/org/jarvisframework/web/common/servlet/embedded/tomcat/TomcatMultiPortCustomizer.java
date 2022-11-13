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

package org.jarvisframework.web.common.servlet.embedded.tomcat;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * Embed Tomcat自定义多个http端口实现
 * <p>
 * {@link WebServerFactoryCustomizer} and
 * {@link org.springframework.boot.autoconfigure.web.embedded.TomcatWebServerFactoryCustomizer}
 * <p>
 * 其他拓展可参考{@link org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer} 、
 * {@link org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer}
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
@Component
public class TomcatMultiPortCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Resource
    private CustomizeWebServerProperties customizeWebServerProperties;


    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        if (Objects.nonNull(customizeWebServerProperties)) {
            Integer[] ports = customizeWebServerProperties.getPorts();
            for (int i = 0; i < ports.length; i++) {
                Connector connector = new Connector();
                connector.setPort(ports[i]);
                factory.addAdditionalTomcatConnectors(connector);
            }
        }
    }
}
