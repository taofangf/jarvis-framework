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

package org.jarvisframework.web.common.filter.authentication;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import org.jarvisframework.common.domain.CommonResult;
import org.jarvisframework.common.enums.ResultCodeEnum;
import org.jarvisframework.common.util.SignatureUtils;
import org.jarvisframework.web.common.util.HttpResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 身份验证过滤器
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public class AuthenticationFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    /**
     * 白名单url数组
     */
    private String[] ignoreUrlPatternArray;

    /**
     * 认证配置参数
     */
    private AuthenticationProperties authenticationProperties;

    /**
     * 路径匹配器
     */
    private PathMatcher pathMatcher = new AntPathMatcher();

    /**
     * AppId对应SecretKey缓存
     */
    private final Map<String, String> APP_SECRET_KEY_CACHE_MAP = new ConcurrentHashMap<>(64);

    /**
     * 上次查询时间戳
     */
    private long lastQueryTimestamp = 0L;

    public AuthenticationFilter(AuthenticationProperties authenticationProperties) {
        this.authenticationProperties = authenticationProperties;
        this.ignoreUrlPatternArray = authenticationProperties.getIgnoreUrlPattern().split("\\|");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
            logger.error("illegal request.");
            return;
        }
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        for (String urlPattern : ignoreUrlPatternArray) {
            if (pathMatcher.match(urlPattern, request.getRequestURI())) {
                logger.info("url {} in whiteList,skip authenticate.", request.getRequestURI());
                filterChain.doFilter(request, response);
                return;
            }
        }
        authenticateRequest(request, response, filterChain);
    }

    /**
     * 验证请求
     *
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param chain    {@link FilterChain}
     */
    private void authenticateRequest(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String appId = request.getHeader("appId");
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");

        if (!StrUtil.isAllNotBlank(appId, timestamp, sign)) {
            logger.error("appId timestamp or sign is empty,appId = {},timestamp = {},sign = {}", appId, timestamp, sign);
            authenticateFailedResponse(response);
            return;
        }
        long localTimestamp = System.currentTimeMillis();

        try {
            long remoteTimeStamp = Long.parseLong(timestamp);
            if (Math.abs(localTimestamp - remoteTimeStamp) > authenticationProperties.getTimestampThreshold()) {
                logger.error("authenticate failed, timestamp invalid");
                authenticateFailedResponse(response);
                return;
            }
        } catch (NumberFormatException e) {
            logger.error("authenticate failed, parse timestamp exception");
            authenticateFailedResponse(response);
            return;
        }

        String secretKey = getSecretKey(appId, localTimestamp);

        if (StrUtil.isNotBlank(secretKey) && SignatureUtils.verifySignatureByHmacSha256(appId, timestamp, secretKey, sign)) {
            chain.doFilter(request, response);
        } else {
            authenticateFailedResponse(response);
        }

    }

    /**
     * 获取SecretKey
     *
     * @param appId          接入方appId
     * @param localTimestamp 当前时间
     * @return 秘钥
     */
    private String getSecretKey(String appId, long localTimestamp) {
        if (Math.abs(localTimestamp - lastQueryTimestamp) > authenticationProperties.getSecretKeyCachePeriod()) {
            String secretKey = StrUtil.EMPTY;
            try {
                SecretKeyService secretKeyService = SpringUtil.getBean(SecretKeyService.class);
                secretKey = secretKeyService.getSecretKeyByAppId(appId);
            } catch (Exception e) {
                logger.error("getSecretKeyByAppId Exception.[{}]", e.getMessage());
            } finally {
                APP_SECRET_KEY_CACHE_MAP.put(appId, secretKey);
                lastQueryTimestamp = localTimestamp;
            }
            return secretKey;
        } else {
            return APP_SECRET_KEY_CACHE_MAP.getOrDefault(appId, StrUtil.EMPTY);
        }

    }

    /**
     * 认证失败响应
     *
     * @param response {@link HttpResponseUtils}
     */
    private void authenticateFailedResponse(HttpServletResponse response) {
        HttpResponseUtils.responseJsonStr(response, JSON.toJSONString(CommonResult.ofFail(ResultCodeEnum.AUTHENTICATION_ERROR)));
    }
}
