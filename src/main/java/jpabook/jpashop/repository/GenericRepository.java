package jpabook.jpashop.repository;

import java.util.List;
import javax.persistence.EntityManager;
import jpabook.jpashop.domain.AbstractEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GenericRepository<T extends AbstractEntity> {

  protected EntityManager em;

  private final Class<T> entityClass;


  public void save(T entity) {
    if (entity.getId() == null) {
      em.persist(entity);
    } else {
      em.merge(entity);
    }
  }

  public T findOne(Long id) {
    return em.find(entityClass, id);
  }

  public List<T> findAll() {
    return this.em.createQuery("select obj from " + this.entityClass.getName() + " obj").getResultList();
  }

}
