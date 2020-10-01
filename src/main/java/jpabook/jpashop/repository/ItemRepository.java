package jpabook.jpashop.repository;

import java.util.List;
import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository extends GenericRepository<Item> {

  public ItemRepository(EntityManager em) {
    super(em, Item.class);
  }

}
