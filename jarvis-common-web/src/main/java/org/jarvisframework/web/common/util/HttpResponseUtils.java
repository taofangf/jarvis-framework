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

package org.jarvisframework.web.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * HttpResponse工具类
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public class HttpResponseUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpResponseUtils.class);

    /**
     * 响应JSON字符串
     *
     * @param response {@link HttpServletResponse}
     * @param json     json字符串
     */
    public static void responseJsonStr(HttpServletResponse response, String json) {
        PrintWriter printWriter = null;
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            printWriter = response.getWriter();
            printWriter.write(json);
            printWriter.flush();
        } catch (IOException e) {
            LOGGER.error("responseJsonStr exception", e);
        } finally {
            if (Objects.nonNull(printWriter)) {
                printWriter.close();
            }
        }
    }
}
