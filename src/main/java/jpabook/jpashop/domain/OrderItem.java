package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice; //주문 가격

    private int count; //주문 수량

    //== 생성 메서드==//
//    protected OrderItem(){
//        다른서비스에서 생성하는것 막기. OrderItem의 createOrderItem 메서드를 통해 도메인로직으로 생성바람.
//        @NoArgsConstructor(access = AccessLevel.PROTECTED) 와 같은 역할.
//    }
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }
    //==비즈니스 로직==//

    /**
     * 주문 취소
     */
    public void cancel() {
        // 아이템 재고 늘리기
        getItem().addStock(count);
    }

    //== 조회로직 --//
    /**
     * 주문상품 가격조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
