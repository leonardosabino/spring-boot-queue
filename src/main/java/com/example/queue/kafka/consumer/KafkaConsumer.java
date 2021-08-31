package com.example.queue.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty(value = {"kafka.enable"}, havingValue = "true")
public class KafkaConsumer {

  @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.groupId}")
  public void consumirMensagem(String mensagem) {
    log.info("Mensagem Recebida: " + mensagem);
  }

}
