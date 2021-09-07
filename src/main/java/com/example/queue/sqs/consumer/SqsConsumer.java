package com.example.queue.sqs.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty(value = {"sqs.enable"}, havingValue = "true")
public class SqsConsumer {

  @SqsListener(value = {"${sqs.urlName}"}, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
  public void receiveMessage(String message) {
    log.info("Message received: " + message);
  }

}
