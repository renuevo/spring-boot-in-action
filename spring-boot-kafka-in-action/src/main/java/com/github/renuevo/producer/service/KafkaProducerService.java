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

    /**
     * <pre>
     *  @methodName : sendMessage
     *  @author : Deokhwa.Kim
     *  @since : 2020-09-20 오후 11:22
     *  @summary : 카프카 메시지 프로듀서
     *  @param : [msg]
     *  @return : void
     * </pre>
     */
    public void sendMessage(String msg) {
        kafkaTemplate.send("kafka-test-topic", msg);
    }

    /**
     * <pre>
     *  @methodName : sendAsyncMessage
     *  @author : Deokhwa.Kim
     *  @since : 2020-09-20 오후 11:21
     *  @summary : 카프카 비동기 메시지 프로듀서
     *  @param : [mag]
     *  @return : void
     * </pre>
     */
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


    /**
     * <pre>
     *  @methodName : sendDataModel
     *  @author : Deokhwa.Kim
     *  @since : 2020-09-20 오후 11:22
     *  @summary : 데이터 모델 클래스 프로듀서
     *  @param : [dataModel]
     *  @return : void
     * </pre>
     */
    public void sendDataModel(DataModel dataModel) {
        dataModelKafkaTemplate.send("kafka-data-model-topic", dataModel);
    }

    /**
     * <pre>
     *  @methodName : sendAsyncDataModel
     *  @author : Deokhwa.Kim
     *  @since : 2020-09-20 오후 11:22
     *  @summary : 데이터 모델 클래스 비동기 프로듀서
     *  @param : [dataModel]
     *  @return : void
     * </pre>
     */
    public void sendAsyncDataModel(DataModel dataModel) {
        ListenableFuture<SendResult<String, DataModel>> future = dataModelKafkaTemplate.send("kafka-data-model-topic", dataModel);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Kafka Send Message Error : {} ", ex.getMessage(), ex);
            }

            @Override
            public void onSuccess(SendResult<String, DataModel> result) {
                log.info("Kafka Send Message : {} / Offset : {}", result.toString(), result.getRecordMetadata().offset());
            }
        });
    }

}
