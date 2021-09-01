package com.example.queue.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "v1/kafka")
public class KafkaProducer {

  @Autowired(required = false)
  private KafkaTemplate<String, String> kafkaTemplate;

  @Value(value = "${kafka.topic}")
  private String kafkaTopic;

  @Value(value = "${kafka.enable}")
  private boolean kafkaEnable;

  @GetMapping("{message}")
  public void enviarMensagem(@PathVariable String message) {
    if (!kafkaEnable) {
      throw new IllegalArgumentException(
          "Pré condição para utilizar kafka não habilitado" +
              "kafka.enable is false" +
              "origin");
    }

    var future = kafkaTemplate.send(kafkaTopic, message);

    future.addCallback(new ListenableFutureCallback<>() {

      @Override
      public void onSuccess(SendResult<String, String> result) {
        log.info(
            "Enviado mensagem: " + result.getProducerRecord().toString() + "no topico: " + result
                .getRecordMetadata().topic());
      }

      @Override
      public void onFailure(Throwable ex) {
        log.error(
            "Erro ao enviar mensagem para o kafka: " + ex.getMessage() + " Messagem: " + message);
      }
    });
  }

}
