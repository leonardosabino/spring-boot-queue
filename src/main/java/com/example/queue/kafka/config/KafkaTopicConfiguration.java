package com.example.queue.kafka.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = {"kafka.enable"}, havingValue = "true")
public class KafkaTopicConfiguration {

  @Value(value = "${kafka.url}")
  private String kafkaUrl;

  @Value(value = "${kafka.topic}")
  private String kafkaTopic;

  @Bean
  public KafkaAdmin kafkaAdmin() {
    Map<String, Object> props = new HashMap<>();
    props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
    return new KafkaAdmin(props);
  }

  @Bean
  public NewTopic topic1() {
    return new NewTopic(kafkaTopic, 1, (short) 1);
  }

}
