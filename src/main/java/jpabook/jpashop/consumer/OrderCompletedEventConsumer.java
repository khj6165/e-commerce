package jpabook.jpashop.consumer;

import jpabook.jpashop.domain.OrderCompletedMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderCompletedEventConsumer {

    @KafkaListener(
            topics="order-completed-message-v1",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "orderMessageKafkaListenerContainerFactory"
    )
    public void listen(@Payload OrderCompletedMessage orderCompletedMessage) {
        log.info("*************************** 하위 도메인 컨슈머 ****************************");
        log.info("<<< 주문 완료 메세지 수신, {}", orderCompletedMessage);
        log.info("************************************************************************");
    }
}