package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

	@Id @GeneratedValue
	private Long id;

	@OneToOne(mappedBy = "delivery")
	private Order order;

	private String city;
	private String street;
	private String zipcode;

	@Enumerated(EnumType.STRING)
	private DeliveryStatus status;
}
