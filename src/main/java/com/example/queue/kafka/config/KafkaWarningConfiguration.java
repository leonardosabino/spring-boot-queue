package com.example.queue.kafka.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(value = {"kafka.enable"}, havingValue = "false")
public class KafkaWarningConfiguration {

  @EventListener(ApplicationReadyEvent.class)
  public void printLogIfKafkaIsDisabled() {
    log.warn("To use Kafka make sure the property 'kafka.enable' is true");
  }
}
