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

package org.jarvisframework.common.desensitization.enums;

/**
 * 脱敏类型枚举类
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public enum DesensitizedTypeEnum {
    /**
     * 用户ID
     */
    USER_ID,
    /**
     * 中文名
     */
    CHINESE_NAME,
    /**
     * 身份证号码
     */
    ID_CARD,
    /**
     * 座机号
     */
    FIXED_PHONE,
    /**
     * 手机号
     */
    MOBILE_PHONE,
    /**
     * 地址
     */
    ADDRESS,
    /**
     * 电子邮箱
     */
    EMAIL,
    /**
     * 密码
     */
    PASSWORD,
    /**
     * 中国大陆车牌，包含普通车辆、新能源车辆
     */
    CAR_LICENSE,
    /**
     * 银行卡号
     */
    BANK_CARD,
    /**
     * 无处理
     */
    NONE,
    ;
}
