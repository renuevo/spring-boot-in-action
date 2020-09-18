package com.github.renuevo.producer.service;

import com.github.renuevo.model.DataModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Qualifier("dataModelKafkaTemplate")
    private final KafkaTemplate<String, DataModel> dataModelKafkaTemplate;

    public void sendMessage(String msg) {
        kafkaTemplate.send("kafka-test-topic", msg);
    }

    public void sendAsyncMessage(String mag) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("kafka-test-topic", mag);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Kafka Send Message Error : {} ", ex.getMessage(), ex);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Kafka Send Message : {} / Offset : {}", mag, result.getRecordMetadata().offset());
            }
        });
    }


    public void sendDataModelMessage(DataModel dataModel) {
        dataModelKafkaTemplate.send("kafka-data-model-topic", dataModel);
    }

}
