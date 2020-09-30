package jpabook.jpashop;

import static org.junit.Assert.*;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)  //jUnit에게 spring관련 테스트를 할거다 라고 알려줌
@SpringBootTest
public class MemberRepositoryTest {

  @Autowired
  private MemberRepository memberRepository;

  @Test
  @Transactional
  @Rollback(false)
  public void saveAndFind() {
    Member member = new Member();
    member.setUsername("memberA");
    Long id = memberRepository.save(member);
    Member foundMember = memberRepository.find(id);
    Assertions.assertThat(foundMember.getId()).isEqualTo(member.getId());
    Assertions.assertThat(foundMember.getUsername()).isEqualTo(member.getUsername());
  }

}