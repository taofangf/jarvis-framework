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

import org.junit.Assert;
import org.junit.Test;

/**
 * 签名工具类单元测试
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public class SignatureUtilsTest {

    /**
     * appId
     */
    private static String APP_ID = "Wechat";

    /**
     * 时间戳
     */
    private static String TIMESTAMP = "1662774807104";

    /**
     * 秘钥
     */
    private static String SECRET_KEY = "09ef5dd65d057d615162738633527fd4a9682088ca5f84928aad0c9efca53d88";

    @Test
    public void generateSecretKey() {
        String secretKey = SignatureUtils.generateSecretKey();
        System.out.println("secretKey = " + secretKey);
    }

    @Test
    public void generateSignatureByHmacSha256() {
        String signature = SignatureUtils.generateSignatureByHmacSha256(APP_ID, TIMESTAMP, SECRET_KEY);
        System.out.println("signature = " + signature);
        // dba6de1d19d1cf07f6a879322794eafdec734a0b04e8771b269bc87a7ab2dede
    }

    @Test
    public void verifySignatureByHmacSha256() {
        boolean verifyResult = SignatureUtils.verifySignatureByHmacSha256(APP_ID, TIMESTAMP, SECRET_KEY, "dba6de1d19d1cf07f6a879322794eafdec734a0b04e8771b269bc87a7ab2dede");
        Assert.assertEquals(verifyResult, true);
    }


}