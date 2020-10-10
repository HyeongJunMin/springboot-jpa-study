package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final MemberRepository memberRepository;
  private final ItemRepository itemRepository;

  // 주문
  @Transactional
  public Long order(Long memberId, Long itemId, int count) {
    Member member = memberRepository.findOne(memberId);
    Item item = itemRepository.findOne(itemId);

    Delivery delivery = new Delivery();
    delivery.setAddress(member.getAddress());

    OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

    Order order = Order.createOrder(member, delivery, orderItem);
    // cascade = All이기 때문에 order가 영속상태가 될 때 delivery, orderItem도 함께 영속상태가 된다.
    // 어떤 엔티티 B가 어떤 엔티티 A에서만 private하게 사용될때만 cascadeType을 all로 잡아야한다.
    // B를 다른데서도 갖다쓰면? A에 영향이 갈 수도 있으니까
    orderRepository.save(order);
    return order.getId();
  }

  // 취소
  @Transactional
  public void cancelOrder(Long orderId) {
    Order order = orderRepository.findOne(orderId);
    // 더티체킹으로 update 쿼리 날아감
    order.cancel();
  }

  // 검색
  public List<Order> findOrders(OrderSearch orderSearch) {
    return orderRepository.findAll(orderSearch);
  }
}
