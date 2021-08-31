package com.example.queue.sqs.config;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.QueueAttributeName;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

@ConditionalOnProperty(value = {
    "cloud.aws.sqs.enable"}, havingValue = "true", matchIfMissing = false)
@Configuration
public class SqsProducerConfiguration {

  @Autowired
  private AmazonSQSAsync amazonSQSAsync;

  @PostConstruct
  public void createQueues() {
    createQueue();
  }

  private String createQueue() {
    ListQueuesResult queueList = amazonSQSAsync.listQueues("QueueName");
    if (!CollectionUtils.isEmpty(queueList.getQueueUrls())) {
      return queueList.getQueueUrls().get(0);
    }

    Map<String, String> queueAttributes = new HashMap<>();
    queueAttributes.put(QueueAttributeName.FifoQueue.toString(), "QueueFifo");

    CreateQueueRequest createFifoQueueRequest = new CreateQueueRequest("QueueName")
        .withAttributes(queueAttributes);

    return amazonSQSAsync.createQueue(createFifoQueueRequest).getQueueUrl();
  }
}
