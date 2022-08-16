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

package org.jarvisframework.common.api;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import org.jarvisframework.common.api.model.BaseMessage;
import org.jarvisframework.common.enums.ResultCodeEnum;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.StringJoiner;

/**
 * 标准返回测试
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public class StandardResponseResultTest {

    private static String protocolStr;

    static {
        protocolStr = FileUtil.readString("META-INF/json/protocol.json", StandardCharsets.UTF_8);
    }

    static class ObjectMessage {

        @JSONField(name = "objectMessage")
        private List<ObjectMessageDTO> objectMessage;

        public List<ObjectMessageDTO> getObjectMessage() {
            return objectMessage;
        }

        public void setObjectMessage(List<ObjectMessageDTO> objectMessage) {
            this.objectMessage = objectMessage;
        }

        public static class ObjectMessageDTO {
            @JSONField(name = "id")
            private String id;
            @JSONField(name = "name")
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public String toString() {
                return new StringJoiner(", ", ObjectMessageDTO.class.getSimpleName() + "[", "]")
                        .add("id='" + id + "'")
                        .add("name='" + name + "'")
                        .toString();
            }
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", ObjectMessage.class.getSimpleName() + "[", "]")
                    .add("objectMessage=" + objectMessage)
                    .toString();
        }
    }

    @Test
    public void baseMessageTest() {
        StandardResponseResult<BaseMessage<String>> responseResult = JSON.parseObject(protocolStr, new TypeReference<StandardResponseResult<BaseMessage<String>>>() {
        });
        System.out.println("responseResult.getResultCode() = " + responseResult.getResultCode());
        System.out.println("responseResult.getResultInfo() = " + responseResult.getResultInfo());

        System.out.println("responseResult.getResult() = " + responseResult.getResult());
    }

    @Test
    public void objectMessageTest() {
        StandardResponseResult<BaseMessage<ObjectMessage>> parseObject = JSON.parseObject(protocolStr, new TypeReference<StandardResponseResult<BaseMessage<ObjectMessage>>>() {

        });
        System.out.println("parseObject = " + parseObject);

        ObjectMessage messageBody = parseObject.getResult().getMessageBody();
        List<ObjectMessage.ObjectMessageDTO> objectMessage = messageBody.getObjectMessage();
        System.out.println("objectMessage = " + objectMessage);
    }

    @Test
    public void objectMessageTest1() {
        BaseMessage<ObjectMessage> message = convert(protocolStr);
        System.out.println("message = " + message);

        ObjectMessage messageBody = message.getMessageBody();
        List<ObjectMessage.ObjectMessageDTO> objectMessage = messageBody.getObjectMessage();
        System.out.println("objectMessage = " + objectMessage);

    }

    @Test
    public void objectMessageTest2() {
        BaseMessage<ObjectMessage> message = convert(protocolStr, ObjectMessage.class);
        System.out.println("message = " + message);

        ObjectMessage messageBody = message.getMessageBody();
        List<ObjectMessage.ObjectMessageDTO> objectMessage = messageBody.getObjectMessage();
        System.out.println("objectMessage = " + objectMessage);

    }

    public <T> BaseMessage<T> convert(String str) {
        StandardResponseResult<BaseMessage<T>> responseResult = JSON.parseObject(str, new TypeReference<StandardResponseResult<BaseMessage<T>>>() {
        });
        if (ResultCodeEnum.PUB_SUCCESS.getResultCode().equals(responseResult.getResultCode())) {
            return responseResult.getResult();
        }
        throw new RuntimeException();
    }

    public <T> BaseMessage<T> convert(String str, Class<T> type) {
        StandardResponseResult<BaseMessage<T>> responseResult = JSON.parseObject(str, new TypeReference<StandardResponseResult<BaseMessage<T>>>(type) {
        });
        if (ResultCodeEnum.PUB_SUCCESS.getResultCode().equals(responseResult.getResultCode())) {
            return responseResult.getResult();
        }
        throw new RuntimeException();
    }
}