package com.github.renuevo.producer.controller;

import com.github.renuevo.model.DataModel;
import com.github.renuevo.producer.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/kafka")
public class ProducerController {

    private final KafkaProducerService kafkaProducerService;

    @GetMapping("/push/msg")
    public void pushMassage(@RequestParam String msg) {
        this.kafkaProducerService.sendMessage(msg);
    }

    @GetMapping("/push_async/msg")
    public ResponseEntity<String> pushAsyncMassage(@RequestParam String msg) {
        this.kafkaProducerService.sendAsyncMessage(msg);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/push/data_model")
    public void pushDataModel(@ModelAttribute DataModel dataModel) {
        this.kafkaProducerService.sendDataModel(dataModel);
    }

    @GetMapping("/push_async/data_model")
    public void pushAsyncDataModel(@ModelAttribute DataModel dataModel) {
        this.kafkaProducerService.sendAsyncDataModel(dataModel);
    }
}
