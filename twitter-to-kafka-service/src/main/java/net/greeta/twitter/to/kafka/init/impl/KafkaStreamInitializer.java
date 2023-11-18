package net.greeta.twitter.to.kafka.init.impl;

import net.greeta.twitter.kafka.admin.client.KafkaAdminClient;
import net.greeta.twitter.kafka.config.KafkaConfigData;
import net.greeta.twitter.to.kafka.init.StreamInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class KafkaStreamInitializer implements StreamInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaStreamInitializer.class);

    private final KafkaConfigData kafkaConfigData;

    private final KafkaAdminClient kafkaAdminClient;

    public KafkaStreamInitializer(KafkaConfigData configData, KafkaAdminClient adminClient) {
        this.kafkaConfigData = configData;
        this.kafkaAdminClient = adminClient;
    }

    @Override
    public void init() {
        LOG.info("Start Creating Topics with name {}", Arrays.toString(kafkaConfigData.getTopicNamesToCreate().toArray(new String[] {})));
        kafkaAdminClient.createTopics();
        kafkaAdminClient.checkSchemaRegistry();
        LOG.info("Topics with name {} is ready for operations!", Arrays.toString(kafkaConfigData.getTopicNamesToCreate().toArray(new String[] {})));
    }
}
