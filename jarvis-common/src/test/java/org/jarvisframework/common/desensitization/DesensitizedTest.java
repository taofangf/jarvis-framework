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

package org.jarvisframework.common.desensitization;

import com.alibaba.fastjson.JSONObject;
import org.jarvisframework.common.desensitization.fastjson.FastJsonDesensitizedValueFilter;
import org.jarvisframework.common.desensitization.model.UserInfo;
import org.junit.Test;

/**
 * 数据脱敏测试
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public class DesensitizedTest {
    @Test
    public void desensitizedUserInfo() {
        userInfo();
    }

    private void userInfo() {
        UserInfo userInfo = new UserInfo().setUsername("贾维斯").setPassword("jarvis@123456")
                .setPhoneNumber("13834567987");
        System.out.println("userInfo = " + userInfo);
        String jsonString = JSONObject.toJSONString(userInfo, new FastJsonDesensitizedValueFilter());
        System.out.println("jsonString = " + jsonString);
    }

    @Test
    public void desensitizedCustomizer() {
        userInfo();
    }

    public static class HideDesensitized implements DesensitizedCustomizer {

        /**
         * 自定义实现脱敏
         *
         * @param object 原始数据
         * @return 脱敏后的数据
         */
        @Override
        public Object desensitized(Object object) {
            return "@@@@@@@@@@@@";
        }
    }
}
