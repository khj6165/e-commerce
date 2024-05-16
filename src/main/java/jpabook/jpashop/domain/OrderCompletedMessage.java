package jpabook.jpashop.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCompletedMessage {
    public String txId;
    public String orderId;
    public String memberId;
    public String completedAt;
    public String version;
}