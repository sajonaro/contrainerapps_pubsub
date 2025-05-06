package com.service.controller;

import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.service.model.Order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Mono;

@RestController
public class OrderProcessingServiceController {

    private static final Logger logger = LoggerFactory.getLogger(OrderProcessingServiceController.class);

    @Topic(name = "testingtopic", pubsubName = "messagebus")
    @PostMapping(path = "/orders", consumes = MediaType.ALL_VALUE)
    public Mono<ResponseEntity<String>> getCheckout(@RequestBody(required = false) CloudEvent<Order> cloudEvent) {
        return Mono.fromSupplier(() -> {
            try {
                logger.info("Subscriber received: " + cloudEvent.getData());
                return ResponseEntity.ok("SUCCESS");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}

