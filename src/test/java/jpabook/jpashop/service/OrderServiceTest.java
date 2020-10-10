package jpabook.jpashop.service;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.enums.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

  private int defaultStockQuantity = 10;
  private int defaultPrice = 10_000;
  private int orderCount = 2;

  @Autowired private EntityManager em;
  @Autowired private OrderService orderService;
  @Autowired private OrderRepository orderRepository;

  @Test
  public void 상품주문() throws Exception {

    //given
    Member member = createMember();
    Book book = createBook(defaultStockQuantity, defaultPrice);

    //when
    Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

    //then
    Order order = orderRepository.findOne(orderId);

    // assertEquals(message, expected, actual)
    assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, order.getStatus());
    assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, order.getOrderItems().size());
    assertEquals("주문 가격은 가격 * 수량이다.", defaultPrice * orderCount, order.getTotalPrice());
    assertEquals("주문 수량만큼 재고가 줄어야 한다.", defaultStockQuantity - orderCount, book.getStockQuantity());
  }

  private Book createBook(int defaultStockQuantity, int defaultPrice) {
    Book book = new Book();
    book.setName("JPA");
    book.setPrice(defaultPrice);
    book.setStockQuantity(defaultStockQuantity);
    em.persist(book);
    return book;
  }

  private Member createMember() {
    Member member = new Member();
    member.setName("회원1");
    member.setAddress(new Address("서울", "경기", "123-123"));
    em.persist(member);
    return member;
  }

  @Test(expected = NotEnoughStockException.class)
  public void 초과주문() throws Exception {
    //given
    Member member = createMember();
    Book book = createBook(defaultStockQuantity, defaultPrice);

    int exceededOrderCount = 11;

    //when
    orderService.order(member.getId(), book.getId(), exceededOrderCount);

    //then
    fail("재고부족 예외가 발생해랴 한다.");
  }

  @Test
  public void 주문취소() throws Exception {
    //given
    int orderCount = 2;
    Member member = createMember();
    Book book = createBook(defaultStockQuantity, defaultPrice);
    Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

    //when
    orderService.cancelOrder(orderId);

    //then
    Order order = orderRepository.findOne(orderId);

    assertEquals("주문 취소 시 상태는 CANCEL이어야 한다.", OrderStatus.CANCEL, order.getStatus());
    assertEquals("주문 취소된 상품은 재고가 원복되어야 한다.", defaultStockQuantity, book.getStockQuantity());
  }

}