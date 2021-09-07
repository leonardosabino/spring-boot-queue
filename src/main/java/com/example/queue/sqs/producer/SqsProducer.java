package com.example.queue.sqs.producer;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "v1/sqs")
public class SqsProducer {

  @Autowired(required = false)
  private AmazonSQS sqsClient;

  @Value("${sqs.enable}")
  private boolean sqsEnable;

  @Value("${sqs.urlName}")
  private String queueUrl;

  @Value("${sqs.name}")
  private String queueName;

  @GetMapping("{message}")
  public void send(@PathVariable String message) {
    if (sqsClient == null && !sqsEnable) {
      throw new IllegalArgumentException(
          "Property to enable sqs is false. (sqs.enable is false)");
    }

    log.info("SQS - Sending message to queue: {}", queueName);

    try {
      // Enable when you need use a object
      // var body = objectMapper.writeValueAsString(message);

      SendMessageRequest request = new SqsMessageRequestBuilder()
          .withBody(message)
          .withQueueUrl(queueUrl)
          .build();

      sqsClient.sendMessage(request);

      log.info(message + " sent to queue!");

    } catch (Exception e) {
      throw new IllegalArgumentException("SQS - Failed to send to queue");
    }
  }
}
