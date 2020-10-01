package jpabook.jpashop.service;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

  @Autowired private MemberService memberService;
  @Autowired private MemberRepository memberRepository;

  @Test
  public void join() throws Exception {
    //given
    Member member = new Member();
    member.setName("mhj");

    //when
    Long joinedId = memberService.join(member);

    //then
    assertEquals(member, memberRepository.findOne(joinedId));

    // 영속성 컨텍스트는 ID로 엔티티를 관리한다.
    // ID가 A인 엔티티가 이미 영속성 컨텍스트에 있을 때, ID가 A인 엔티티를 찾아달라 하면 갖고있던걸 주고, 비교할 때도 갖고있는거로 비교함
  }

  @Test(expected = IllegalStateException.class)
  public void duplicatedMember() throws Exception {
    //given
    Member member = new Member();
    member.setName("mhj");

    Member member2 = new Member();
    member2.setName("mhj");

    //when
    memberService.join(member);
    memberService.join(member2);

    //then
    fail("need IllegalStateException");
  }

}