package com.example.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@Slf4j
public class QueueApplication {

  public static void main(String[] args) {
    SpringApplication.run(QueueApplication.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  @ConditionalOnProperty(value = {"kafka.enable"}, havingValue = "false")
  public void printLogIfKafkaIsDisabled() {
    log.warn(
        "Para utilizar o Kafka certifique-se de estar habilitado nas properties do projeto (kafka.enable = true)");
  }

}
