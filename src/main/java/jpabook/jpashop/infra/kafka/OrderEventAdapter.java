package jpabook.jpashop.infra.kafka;

import jpabook.jpashop.domain.OrderCompletedMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventAdapter {

    private final KafkaTemplate orderMessageKafkaTemplate;

    private String TOPIC = "order-completed-message-v1";
    private String ORDER_MESSAGE_KEY = "order-message-key";

    public void send(OrderCompletedMessage orderCompletedMessage) {
        ListenableFuture<SendResult<String, OrderCompletedMessage>> future = (ListenableFuture<SendResult<String, OrderCompletedMessage>>) orderMessageKafkaTemplate.send(TOPIC, ORDER_MESSAGE_KEY, orderCompletedMessage);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, OrderCompletedMessage> result) {
                log.info(String.format(">>> 주문 완료 메시지 발행(%s): key = %-10s value = %s", TOPIC, ORDER_MESSAGE_KEY, orderCompletedMessage));
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error(String.format(">>> Message sending is Failed, {}", ex.getMessage()));
            }
        });
    }
}