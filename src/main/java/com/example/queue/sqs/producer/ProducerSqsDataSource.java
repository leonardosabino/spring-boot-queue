package com.example.queue.sqs.producer;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.example.queue.sqs.SqsMessageRequestBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProducerSqsDataSource {

  @Autowired(required = false)
  private AmazonSQS sqsClient;

  @Autowired
  private ObjectMapper objectMapper;

  @Value(value = "${cloud.aws.sqs.enable}")
  private Boolean sqsEnable;

  public <T> void send(T outgoing) {
    if (sqsClient == null && !sqsEnable) {
      throw new IllegalArgumentException(
          "Pré condição para utilizar sqs não habilitado. cloud.aws.sqs.enable is false");
    }

    log.info("SQS - Enviando mensagem para fila: {}", "QueueName");

    try {
      var body = objectMapper.writeValueAsString(outgoing);

      SendMessageRequest request = new SqsMessageRequestBuilder()
          .withBody(body)
          .withQueueUrl("QueueUrl")
          .build();

      Optional.ofNullable(sqsClient).ifPresent(amazonSQS -> sqsClient.sendMessage(request));

      log.info(body + " " + "enviada para fila!");

    } catch (Exception e) {
      throw new IllegalArgumentException("SQS - Falha ao enviar para fila ");
    }
  }
}
