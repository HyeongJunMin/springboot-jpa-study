package jpabook.jpashop.repository;

import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository extends GenericRepository<Order> {

  public OrderRepository(EntityManager em) {
    super(em, Order.class);
  }

//  public List<Order> findAll(OrderSearch search) {}
}
