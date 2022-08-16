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

package org.jarvisframework.common.api.model;

import java.util.StringJoiner;

/**
 * 基础消息
 *
 * @author <a href="mailto:taofangf@gmail.com">fangtao</a>
 * @since 1.0.0
 */
public class BaseMessage<T> {
    /**
     * 消息头
     */
    private MessageHead messageHead;

    /**
     * 消息Body
     */
    private T messageBody;

    public MessageHead getMessageHead() {
        return messageHead;
    }

    public BaseMessage<T> setMessageHead(MessageHead messageHead) {
        this.messageHead = messageHead;
        return this;
    }

    public T getMessageBody() {
        return messageBody;
    }

    public BaseMessage<T> setMessageBody(T messageBody) {
        this.messageBody = messageBody;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BaseMessage.class.getSimpleName() + "[", "]")
                .add("messageHead=" + messageHead)
                .add("messageBody=" + messageBody)
                .toString();
    }
}
