package com.github.renuevo.consumer.service;

import com.github.renuevo.model.DataModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "kafka-test-topic")
    public void listenWithHeaders(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info("Received Message : {} / From Partition : {}", message, partition);
    }

    @KafkaListener(
            topics = "kafka-data-model-topic",
            containerFactory = "kafkaListenerContainerDataModelFactory")
    public void dataModelListener(DataModel dataModel) {
        log.info("Received DataModel : {} ", dataModel.toString());
    }
}
