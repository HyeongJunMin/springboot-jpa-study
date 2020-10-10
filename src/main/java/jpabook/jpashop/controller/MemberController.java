package jpabook.jpashop.controller;

import javax.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/members/new")
  public String createForm(Model model) {
    model.addAttribute("memberForm", new MemberForm());
    return "members/createMemberForm";
  }

  @PostMapping("/members/new")
  // 바로 엔티티를 받아와도 되는데 form에서 딱 필요한 데이터만 받는 DTO를 사용하는게 좋다
  // 애플리케이션 규모가 커지면서 엔티티가 지저분해질 수 있다.
  public String create(@Valid MemberForm form, BindingResult result) {
    if (result.hasErrors()) {
      // spring과 thymeleaf integration이 잘되어있음(javax.validation)
      // 그래서 spring이 result를 thymeleaf 뷰까지 끌고가준다.
      // 에러가 있는 특정 필드에 대해 뷰에서 특정 동작을 수행시킬 수 있음
      // 다른 form데이터들은 들어온 그대로 다시뿌려줌
      return "members/createMemberForm";
    }
    Address address = new Address(form.getCity(), form.getStreet(), form.getZipCode());
    Member member = new Member();
    member.setName(form.getName());
    member.setAddress(address);

    memberService.join(member);

    return "redirect:/";
  }

  @GetMapping("/members")
  public String list(Model model) {
    model.addAttribute("members", memberService.findMembers());
    return "members/memberList";
  }


}
