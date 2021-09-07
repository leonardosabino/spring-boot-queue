package com.example.queue.kafka.config;

import java.util.HashMap;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.stereotype.Component;

@EnableKafka
@Component
@ConditionalOnProperty(value = {"kafka.enable"}, havingValue = "true")
public class KafkaConsumerConfiguration {

  @Value(value = "${kafka.url}")
  private String kafkaUrl;

  @Value(value = "${kafka.groupId}")
  private String kafkaGroupId;

  @Bean
  public ConsumerFactory<String, String> consumerFactory() {
    // Enable when you need deserialize object from another service
    //var jsonDeserializer = new JsonDeserializer<ExampleDTO>(String.class);
    //jsonDeserializer.setRemoveTypeHeaders(false);
    //jsonDeserializer.addTrustedPackages("*");
    //jsonDeserializer.setUseTypeMapperForKey(true);

    var props = new HashMap<String, Object>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    return new DefaultKafkaConsumerFactory<>(props);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
    var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }
}


