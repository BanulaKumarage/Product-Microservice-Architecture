package com.springprojects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationService {
    public static void main(String[] args) {
        SpringApplication.run(NotificationService.class, args);
    }

    @KafkaListener(topics = "notification-topic")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
        log.info("Notification sent for order number: " + orderPlacedEvent.getOrderNumber());
    }
}