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

package org.jarvisframework.common.desensitization.util;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import org.jarvisframework.common.desensitization.enums.DesensitizedTypeEnum;

/**
 * 脱敏工具类
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public class DesensitizedUtils {

    /**
     * 脱敏
     *
     * @param str              原始字符串
     * @param desensitizedType {@link DesensitizedTypeEnum} 脱敏类型
     * @return 脱敏后的字符串
     */
    public static String desensitized(CharSequence str, DesensitizedTypeEnum desensitizedType) {
        if (StrUtil.isBlank(str)) {
            return StrUtil.EMPTY;
        }
        String newStr = String.valueOf(str);
        switch (desensitizedType) {
            case CHINESE_NAME:
                newStr = DesensitizedUtils.chineseName(String.valueOf(str));
                break;
            case ID_CARD:
                newStr = DesensitizedUtils.idCardNum(String.valueOf(str), 1, 2);
                break;
            case FIXED_PHONE:
                newStr = DesensitizedUtils.fixedPhone(String.valueOf(str));
                break;
            case MOBILE_PHONE:
                newStr = DesensitizedUtils.mobilePhone(String.valueOf(str));
                break;
            case ADDRESS:
                newStr = DesensitizedUtils.address(String.valueOf(str), 8);
                break;
            case EMAIL:
                newStr = DesensitizedUtils.email(String.valueOf(str));
                break;
            case PASSWORD:
                newStr = DesensitizedUtils.password(String.valueOf(str));
                break;
            case CAR_LICENSE:
                newStr = DesensitizedUtils.carLicense(String.valueOf(str));
                break;
            case BANK_CARD:
                newStr = DesensitizedUtils.bankCard(String.valueOf(str));
                break;
            default:
        }
        return newStr;
    }

    /**
     * 【中文姓名】只显示第一个汉字，其他隐藏为2个星号，比如：李**
     *
     * @param fullName 姓名
     * @return 脱敏后的姓名
     */
    public static String chineseName(String fullName) {
        if (StrUtil.isBlank(fullName)) {
            return StrUtil.EMPTY;
        }
        return StrUtil.hide(fullName, 1, fullName.length());
    }

    /**
     * 【身份证号】前1位 和后2位
     *
     * @param idCardNum 身份证
     * @param front     保留：前面的front位数；从1开始
     * @param end       保留：后面的end位数；从1开始
     * @return 脱敏后的身份证
     */
    public static String idCardNum(String idCardNum, int front, int end) {
        // 身份证不能为空
        if (StrUtil.isBlank(idCardNum)) {
            return StrUtil.EMPTY;
        }
        // 需要截取的长度不能大于身份证号长度
        if ((front + end) > idCardNum.length()) {
            return StrUtil.EMPTY;
        }
        // 需要截取的不能小于0
        if (front < 0 || end < 0) {
            return StrUtil.EMPTY;
        }
        return StrUtil.hide(idCardNum, front, idCardNum.length() - end);
    }

    /**
     * 【固定电话 前四位，后两位
     *
     * @param num 固定电话
     * @return 脱敏后的固定电话；
     */
    public static String fixedPhone(String num) {
        if (StrUtil.isBlank(num)) {
            return StrUtil.EMPTY;
        }
        return StrUtil.hide(num, 4, num.length() - 2);
    }

    /**
     * 【手机号码】前三位，后4位，其他隐藏，比如135****2210
     *
     * @param num 移动电话；
     * @return 脱敏后的移动电话；
     */
    public static String mobilePhone(String num) {
        if (StrUtil.isBlank(num)) {
            return StrUtil.EMPTY;
        }
        return StrUtil.hide(num, 3, num.length() - 4);
    }

    /**
     * 【地址】只显示到地区，不显示详细地址，比如：北京市海淀区****
     *
     * @param address       家庭住址
     * @param sensitiveSize 敏感信息长度
     * @return 脱敏后的家庭地址
     */
    public static String address(String address, int sensitiveSize) {
        if (StrUtil.isBlank(address)) {
            return StrUtil.EMPTY;
        }
        int length = address.length();
        return StrUtil.hide(address, length - sensitiveSize, length);
    }

    /**
     * 【电子邮箱】邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示，比如：d**@126.com
     *
     * @param email 邮箱
     * @return 脱敏后的邮箱
     */
    public static String email(String email) {
        if (StrUtil.isBlank(email)) {
            return StrUtil.EMPTY;
        }
        int index = StrUtil.indexOf(email, '@');
        if (index <= 1) {
            return email;
        }
        return StrUtil.hide(email, 1, index);
    }

    /**
     * 【密码】密码的全部字符都用*代替，比如：******
     *
     * @param password 密码
     * @return 脱敏后的密码
     */
    public static String password(String password) {
        if (StrUtil.isBlank(password)) {
            return StrUtil.EMPTY;
        }
        return StrUtil.repeat('*', password.length());
    }

    /**
     * 【中国车牌】车牌中间用*代替
     * eg1：null       -》 ""
     * eg1：""         -》 ""
     * eg3：苏D40000   -》 苏D4***0
     * eg4：陕A12345D  -》 陕A1****D
     * eg5：京A123     -》 京A123     如果是错误的车牌，不处理
     *
     * @param carLicense 完整的车牌号
     * @return 脱敏后的车牌
     */
    public static String carLicense(String carLicense) {
        if (StrUtil.isBlank(carLicense)) {
            return StrUtil.EMPTY;
        }
        // 普通车牌
        if (carLicense.length() == 7) {
            carLicense = StrUtil.hide(carLicense, 3, 6);
        } else if (carLicense.length() == 8) {
            // 新能源车牌
            carLicense = StrUtil.hide(carLicense, 3, 7);
        }
        return carLicense;
    }

    /**
     * 银行卡号脱敏
     * eg: 1101 **** **** **** 3256
     *
     * @param bankCardNo 银行卡号
     * @return 脱敏之后的银行卡号
     * @since 5.6.3
     */
    public static String bankCard(String bankCardNo) {
        if (StrUtil.isBlank(bankCardNo)) {
            return bankCardNo;
        }
        bankCardNo = StrUtil.trim(bankCardNo);
        if (bankCardNo.length() < 9) {
            return bankCardNo;
        }

        final int length = bankCardNo.length();
        final int midLength = length - 8;
        final StringBuilder buf = new StringBuilder();

        buf.append(bankCardNo, 0, 4);
        for (int i = 0; i < midLength; ++i) {
            if (i % 4 == 0) {
                buf.append(CharUtil.SPACE);
            }
            buf.append('*');
        }
        buf.append(CharUtil.SPACE).append(bankCardNo, length - 4, length);
        return buf.toString();
    }
}
