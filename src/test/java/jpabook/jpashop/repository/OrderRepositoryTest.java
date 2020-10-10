package jpabook.jpashop.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;
import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderRepositoryTest {

  @Autowired private EntityManager em;
  @Autowired private OrderService orderService;
  @Autowired private OrderRepository orderRepository;

  @Test
  public void 주문_검색() throws Exception {
    //given
    OrderSearch orderSearch = new OrderSearch();
    orderSearch.setMemberName("m");
    Book book = createBook(10, 1000);
    Member m = createMember("m");
    Long orderId = orderService.order(m.getId(), book.getId(), 5);
    Member n = createMember("n");
    Long orderId2 = orderService.order(n.getId(), book.getId(), 3);

    //when
    List<Order> all = orderRepository.findAll(orderSearch);

    //then
    assertEquals("멤버이름 m을 검색했을 때 결과는 1건", 1, all.size());
  }

  private Book createBook(int defaultStockQuantity, int defaultPrice) {
    Book book = new Book();
    book.setName("JPA");
    book.setPrice(defaultPrice);
    book.setStockQuantity(defaultStockQuantity);
    em.persist(book);
    return book;
  }

  private Member createMember(String name) {
    Member member = new Member();
    member.setName(name);
    member.setAddress(new Address("서울", "경기", "123-123"));
    em.persist(member);
    return member;
  }

}