package com.example.queue.sqs.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.model.PurgeQueueRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Slf4j
@Configuration
@EnableSqs
@ConditionalOnProperty(value = {"sqs.enable"}, havingValue = "true")
public class AwsConfiguration {

  @Value(value = "${sqs.url}")
  public String sqsUrl;

  @Value(value = "${sqs.name}")
  public String queueName;

  @Primary
  @Bean
  public AmazonSQSAsync amazonSQSAsync() {
    var amazonSQSAsync = AmazonSQSAsyncClientBuilder.standard()
        .withCredentials(new DefaultAWSCredentialsProviderChain())
        .withEndpointConfiguration(
            new EndpointConfiguration(sqsUrl,
                Regions.US_EAST_1.getName()))
        .build();

    createQueues(amazonSQSAsync, queueName);
    return amazonSQSAsync;
  }

  private void createQueues(final AmazonSQSAsync amazonSQSAsync, final String queueName) {
    amazonSQSAsync.createQueue(queueName);
    var queueUrl = amazonSQSAsync.getQueueUrl(queueName).getQueueUrl();
    amazonSQSAsync.purgeQueueAsync(new PurgeQueueRequest(queueUrl));
  }
}