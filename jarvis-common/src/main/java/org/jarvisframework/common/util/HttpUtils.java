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

package org.jarvisframework.common.util;

import cn.hutool.core.util.StrUtil;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Http工具类
 * <p>
 * 参考JFinal实现
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 2022/11/26
 */
public class HttpUtils {
    /**
     * Http Get请求
     */
    private static final String GET = "GET";
    /**
     * Http Post请求
     */
    private static final String POST = "POST";
    /**
     * 默认字符集
     */
    private static String CHARSET_UTF_8 = "UTF-8";

    /**
     * 连接超时，单位毫秒
     */
    private static int connectTimeout = 19000;
    /**
     * 读取超时，单位毫秒
     */
    private static int readTimeout = 19000;

    private static final SSLSocketFactory SSL_SOCKET_FACTORY = initSslSocketFactory();

    private static final TrustAnyHostnameVerifier TRUST_ANY_HOSTNAME_VERIFIER = new HttpUtils.TrustAnyHostnameVerifier();

    private HttpUtils() {
    }

    private static SSLSocketFactory initSslSocketFactory() {
        try {
            TrustManager[] tm = {new HttpUtils.TrustAnyTrustManager()};
            // ("TLS", "SunJSSE");
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tm, new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置默认字符集
     *
     * @param charSet 字符集
     */
    public static void setCharSet(String charSet) {
        if (StrUtil.isBlank(charSet)) {
            throw new IllegalArgumentException("charSet can not be blank.");
        }
        HttpUtils.CHARSET_UTF_8 = charSet;
    }

    /**
     * 设置连接超时时间
     *
     * @param connectTimeout 连接超时时间
     */
    public static void setConnectTimeout(int connectTimeout) {
        HttpUtils.connectTimeout = connectTimeout;
    }

    /**
     * 设置读取超时时间
     *
     * @param readTimeout 读取超时时间
     */
    public static void setReadTimeout(int readTimeout) {
        HttpUtils.readTimeout = readTimeout;
    }

    /**
     * https 域名校验
     */
    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * https 证书管理
     */
    private static class TrustAnyTrustManager implements X509TrustManager {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }
    }


    /**
     * 获取HttpURLConnection
     *
     * @param url     请求地址
     * @param method  请求方法
     * @param headers 请求头
     * @return {@link  HttpURLConnection}
     */
    private static HttpURLConnection getHttpConnection(String url, String method, Map<String, String> headers) throws IOException {
        URL ur = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) ur.openConnection();
        if (conn instanceof HttpsURLConnection) {
            ((HttpsURLConnection) conn).setSSLSocketFactory(SSL_SOCKET_FACTORY);
            ((HttpsURLConnection) conn).setHostnameVerifier(TRUST_ANY_HOSTNAME_VERIFIER);
        }

        conn.setRequestMethod(method);
        conn.setDoOutput(true);
        conn.setDoInput(true);

        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);

        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");

        if (headers != null && !headers.isEmpty()) {
            for (Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        return conn;
    }

    /**
     * 发送Http Get请求
     *
     * @param url              请求地址
     * @param queryParas       url参数
     * @param headers          请求头
     * @param requestEncoding  请求字符集编码
     * @param responseEncoding 响应字符集编码
     * @return 响应内容
     */
    public static String get(String url, Map<String, String> queryParas, Map<String, String> headers, String requestEncoding, String responseEncoding) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas, requestEncoding), GET, headers);
            conn.connect();
            return readResponseString(conn, responseEncoding);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * 发送Http Get请求
     *
     * @param url        请求地址
     * @param queryParas url参数
     * @return 响应内容
     */
    public static String get(String url, Map<String, String> queryParas) {
        return get(url, queryParas, null, CHARSET_UTF_8, CHARSET_UTF_8);
    }

    /**
     * 发送Http Get请求
     *
     * @param url 请求地址
     * @return 响应内容
     */
    public static String get(String url) {
        return get(url, null, null, CHARSET_UTF_8, CHARSET_UTF_8);
    }

    /**
     * 发送Http Post请求
     *
     * @param url              请求地址
     * @param queryParas       url参数
     * @param data             请求Body内容
     * @param headers          请求头
     * @param requestEncoding  请求字符集编码
     * @param responseEncoding 响应字符集编码
     * @return 响应内容
     */
    public static String post(String url, Map<String, String> queryParas, String data, Map<String, String> headers, String requestEncoding, String responseEncoding) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas, requestEncoding), POST, headers);
            conn.connect();

            if (data != null) {
                OutputStream out = conn.getOutputStream();
                out.write(data.getBytes(requestEncoding));
                out.flush();
                out.close();
            }

            return readResponseString(conn, responseEncoding);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * 发送Http Post请求
     *
     * @param url        请求地址
     * @param queryParas url参数
     * @param data       请求Body内容
     * @return 响应内容
     */
    public static String post(String url, Map<String, String> queryParas, String data) {
        return post(url, queryParas, data, null, CHARSET_UTF_8, CHARSET_UTF_8);
    }

    /**
     * 发送Http Post请求
     *
     * @param url     请求地址
     * @param data    请求Body内容
     * @param headers 请求头
     * @return 响应内容
     */
    public static String post(String url, String data, Map<String, String> headers) {
        return post(url, null, data, headers, CHARSET_UTF_8, CHARSET_UTF_8);
    }

    /**
     * 发送Http Post请求
     *
     * @param url  请求地址
     * @param data 请求Body内容
     * @return 响应内容
     */
    public static String post(String url, String data) {
        return post(url, null, data, null, CHARSET_UTF_8, CHARSET_UTF_8);
    }

    /**
     * 读取响应内容
     *
     * @param conn             {@link  HttpURLConnection}
     * @param responseEncoding 读取响应内容字符集
     * @return 响应内容
     */
    private static String readResponseString(HttpURLConnection conn, String responseEncoding) {
        try (InputStreamReader isr = new InputStreamReader(conn.getInputStream(), responseEncoding)) {
            StringBuilder ret = new StringBuilder();
            char[] buf = new char[1024];
            for (int num; (num = isr.read(buf, 0, buf.length)) != -1; ) {
                ret.append(buf, 0, num);
            }
            return ret.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 构建请求url
     *
     * @param url             请求地址
     * @param queryParas      url参数
     * @param requestEncoding url请求参数字符集编码
     * @return 请求url
     */
    private static String buildUrlWithQueryString(String url, Map<String, String> queryParas, String requestEncoding) {
        if (queryParas == null || queryParas.isEmpty()) {
            return url;
        }

        StringBuilder sb = new StringBuilder(url);
        boolean isFirst;
        if (url.indexOf('?') == -1) {
            isFirst = true;
            sb.append('?');
        } else {
            isFirst = false;
        }

        for (Entry<String, String> entry : queryParas.entrySet()) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append('&');
            }

            String key = entry.getKey();
            String value = entry.getValue();
            if (StrUtil.isNotBlank(value)) {
                try {
                    value = URLEncoder.encode(value, requestEncoding);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
            sb.append(key).append('=').append(value);
        }
        return sb.toString();
    }


    /**
     * 检测是否为 https 请求
     * <p>
     * nginx 代理实现 https 的场景，需要对 nginx 进行如下配置：
     * proxy_set_header X-Forwarded-Proto https;
     * 或者配置:
     * proxy_set_header X-Forwarded-Proto $scheme;
     */
    public static boolean isHttps(HttpServletRequest request) {
        return "https".equalsIgnoreCase(request.getHeader("X-Forwarded-Proto")) || "https".equalsIgnoreCase(request.getScheme());
    }
}
