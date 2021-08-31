package com.example.queue.sqs;

import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Helper class to generate {@link SendMessageRequest} with default values:
 * <li>
 *   <ul>MessageGroupId - Random UUID </ul>
 *   <ul>contentType - application/json </ul>
 * <li/>
 */
public class SqsMessageRequestBuilder {

  private final SendMessageRequest request;

  public SqsMessageRequestBuilder() {
    request = new SendMessageRequest();
    request.setMessageGroupId(UUID.randomUUID().toString());

    //Set default message contentType application/json
    MessageAttributeValue messageAttributeValue = new MessageAttributeValue();
    messageAttributeValue.setDataType("String");
    messageAttributeValue.setStringValue("application/json");
    Map<String, MessageAttributeValue> attributeValueMap = new HashMap<>();
    attributeValueMap.put("contentType", messageAttributeValue);
    request.setMessageAttributes(attributeValueMap);
  }

  public SqsMessageRequestBuilder withBody(String body) {
    request.setMessageBody(body);
    return this;
  }

  public SqsMessageRequestBuilder withQueueUrl(String queueUrl) {
    request.setQueueUrl(queueUrl);
    return this;
  }

  public SqsMessageRequestBuilder withMessageAttributes(
      Map<String, MessageAttributeValue> attributeValueMap) {
    request.setMessageAttributes(attributeValueMap);
    return this;
  }

  public SendMessageRequest build() {
    return request;
  }

}
