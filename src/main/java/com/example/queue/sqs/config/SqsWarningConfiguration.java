package com.example.queue.sqs.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(value = {"sqs.enable"}, havingValue = "false")
public class SqsWarningConfiguration {

  @EventListener(ApplicationReadyEvent.class)
  public void printLogIfSqsIsDisabled() {
    log.warn("To use SQS make sure the property 'sqs.enable' is true");
  }
}
