package jpabook.jpashop.common;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DirtyCheckingAndMerge {

  @PersistenceContext
  private EntityManager em;

  @Test
  public void updateTest() throws Exception {
    // JPA는영속상태의 엔티티의 변경사항을 감지해서 업데이트쿼리를 만들어서 flush할 때 날려준다
    // 변경감지 == 더티체킹
    Member member = new Member();
    member.setName("testMem");

    em.persist(member);
    em.flush();

    Member mem = em.find(Member.class, member.getId());
    assertThat(mem).isNotNull();
  }

  @Test
  public void 준영속() {
    // 준영속 객체는 뭔가?
    // JPA영속상태였다가 DB에 들어간 적이 있어서 JPA가 식별할 수 있는 ID를 갖고있는데,
    // 외부에서 받은 ID로 생성됐기 때문에 현재는 영속성 컨텍스트가 관리하지 않는 객체
    // 준영속 객체의 문제 : 더티체킹 대상이 아님 -> 쿼리 안만들어줌
    // 준영속 엔티티 수정 방법
    // 수정방법 1. 더티체킹 사용 : ItemService.updateItem(Long itemId, Book param)
    // 수정방법 2. 병합 사용 : merge는 준영속 상태의 엔티티를 영속상태로 바꾸는 작업
    //  - merge는 id를 찾아서 모든 데이터를 다 바꿔줌
    //  - ItemService.updateItem(Long itemId, Book param)와 동일하게 작동하지만 다른게 있음
    //  - 작동 방식 :
    //    > ID(식별자)로 1차캐시에서 조회
    //    > 만약 1차 캐시에 엔티티가 없으면? DB에서 엔티티를 조회하고 1차캐시에 저장한 다음 그 엔티티에다가 값을 채워넣음
    //    > 찾아낸 그 엔티티는 영속상태로 되고 그 엔티티를 반환함
    //    > 파라미터로 넘어온 엔티티는 껍데기값이라 값만 전달하고 영속상태는 아님

    // 변경감지는 원하는 속성만 골라서 바꿀 수 있는데(안바꾸는 애는 그대로 유지)
    // 머지는 죄 바꿔버림(안바꾸는 애는 null)

    // 그래서 변경감지를 통해 변경하는게 좋음
  }

}
