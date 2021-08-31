package com.example.queue.sqs.consumer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
@ConditionalOnProperty(value = {"cloud.aws.sqs.enable"}, havingValue = "true")
public class ConsumerSqsListener {

  @SqsListener(value = {
      "${cloud.aws.sqs.incoming-queue.name}"}, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
  public void receiveMessage(String message) {
    log.info("SQS - Mensagem payload: {} ", message);
  }

}
