package jpabook.jpashop.domain;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@AllArgsConstructor
public class Address {

  private String city;
  private String street;
  private String zipCode;

  protected Address() { }

}
