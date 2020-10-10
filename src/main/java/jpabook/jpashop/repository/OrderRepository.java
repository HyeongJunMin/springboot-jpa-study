package jpabook.jpashop.repository;

import java.util.List;
import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.QMember;
import jpabook.jpashop.domain.QOrder;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.util.StringUtils;

@Repository
public class OrderRepository extends GenericRepository<Order> {

  public OrderRepository(EntityManager em) {
    super(em, Order.class);
  }

  public List<Order> findAll(OrderSearch search) {
    JPAQuery query = new JPAQuery(em);
    QOrder order = QOrder.order;
    QMember member = QMember.member;
    query.from(order);
    query.join(order.member, member);

    if (search.getOrderStatus() != null) {
      query.where(order.status.eq(search.getOrderStatus()));
    }

    if (StringUtils.hasText(search.getMemberName())) {
      query.where(member.name.like(search.getMemberName()));
    }

    query.limit(1000);
    return query.fetch();
//    return null;
//    return em.createQuery("select o from Order o join o.member m "
//        + " where o.status = :status "
//        + " and m.name like :name", Order.class)
//    .setParameter("status", search.getOrderStatus())
//    .setParameter("name", search.getMemberName())
//    .setMaxResults(1000)
//    .getResultList();
  }

  // JPA criteria
  // 무슨 쿼리가 생성될 지 눈에 잘 보이지 않는다.
//  public List<Order> findAllByCriteria(OrderSearch search) {
//    CriteriaBuilder cb = em.getCriteriaBuilder();
//    CriteriaQuery<Order> cq = cb.createQuery(Order.class);
//    Root<Order> o = cq.from(Order.class);
//    Join<Object, Object> m = o.join("member", JoinType.INNER);
//
//    List<Predicate> criteria = new ArrayList<>();
//
//    if (search.getOrderStatus() != null) {
//      Predicate status = cb.equal(o.get("status", search.getOrderStatus()));
//      criteria.add(status);
//    }
//
//    if (StringUtils.hasText(search.getMemberName())) {
//      Predicate name = cb.like(m.get("name", "%" + search.getMemberName() + "%"));
//      criteria.add(name);
//    }
//
//    cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
//    TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
//    return query.getResultList();
//  }

}
