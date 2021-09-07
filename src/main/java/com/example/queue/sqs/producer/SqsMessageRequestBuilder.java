package com.example.queue.sqs.producer;

import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import java.util.HashMap;
import java.util.Map;

public class SqsMessageRequestBuilder {

  private final SendMessageRequest request;

  public SqsMessageRequestBuilder() {
    request = new SendMessageRequest();
    // enable group id when the queue is FIFO
    //request.setMessageGroupId(UUID.randomUUID().toString());

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
