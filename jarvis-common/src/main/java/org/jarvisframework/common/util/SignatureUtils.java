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

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;

/**
 * 签名工具类
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public class SignatureUtils {

    /**
     * 生成secretKey
     *
     * @return 秘钥
     */
    public static String generateSecretKey() {
        return HexUtil.encodeHexStr(RandomUtil.randomBytes(32));
    }

    /**
     * 通过HmacSHA256算法生成签名信息
     *
     * @param appId     接入方Id
     * @param timestamp 时间戳 毫秒
     * @param secretKey 分配给接入方的secretKey
     * @return 签名信息
     */
    public static String generateSignatureByHmacSha256(String appId, String timestamp, String secretKey) {
        HMac mac = new HMac(HmacAlgorithm.HmacSHA256, HexUtil.decodeHex(secretKey));
        return mac.digestHex(appId + timestamp + secretKey);
    }

    /**
     * 通过HmacSHA256算法验证签名信息
     *
     * @param appId          接入方Id
     * @param timestamp      时间戳 毫秒
     * @param secretKey      分配给接入方的secretKey
     * @param inputSignature 接入方传入的签名信息 Request Header sign
     * @return 签名验证结果 true or false
     */
    public static boolean verifySignatureByHmacSha256(String appId, String timestamp, String secretKey, String inputSignature) {
        String signature = generateSignatureByHmacSha256(appId, timestamp, secretKey);
        return inputSignature.equals(signature);
    }
}
