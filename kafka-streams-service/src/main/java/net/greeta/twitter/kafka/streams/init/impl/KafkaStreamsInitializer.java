package net.greeta.twitter.kafka.streams.init.impl;

import net.greeta.twitter.kafka.admin.client.KafkaAdminClient;
import net.greeta.twitter.kafka.config.KafkaConfigData;
import net.greeta.twitter.kafka.streams.init.StreamsInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class KafkaStreamsInitializer implements StreamsInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaStreamsInitializer.class);

    private final KafkaConfigData kafkaConfigData;

    private final KafkaAdminClient kafkaAdminClient;

    public KafkaStreamsInitializer(KafkaConfigData configData, KafkaAdminClient adminClient) {
        this.kafkaConfigData = configData;
        this.kafkaAdminClient = adminClient;
    }

    @Override
    public void init() {
        kafkaAdminClient.checkTopicsCreated();
        kafkaAdminClient.checkSchemaRegistry();
        LOG.info("Topics with name {} is ready for operations!", kafkaConfigData.getTopicNamesToCreate().toArray());
    }
}
