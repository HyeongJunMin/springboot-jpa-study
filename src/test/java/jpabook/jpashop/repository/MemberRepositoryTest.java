package jpabook.jpashop.repository;

import static org.junit.Assert.*;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)  //jUnit에게 spring관련 테스트를 할거다 라고 알려줌
@SpringBootTest
public class MemberRepositoryTest {

  @Autowired
  private MemberRepository memberRepository;

  @Test
  @Transactional
  public void saveAndFind() {
    Member member = new Member();
    member.setName("memberA");
    memberRepository.save(member);
    Member foundMember = memberRepository.findOne(member.getId());
    Assertions.assertThat(foundMember.getId()).isEqualTo(member.getId());
    Assertions.assertThat(foundMember.getName()).isEqualTo(member.getName());
  }

}