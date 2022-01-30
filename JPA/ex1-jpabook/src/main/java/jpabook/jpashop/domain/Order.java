package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="ORDERS")
@Getter @Setter
public class Order {

	@Id @GeneratedValue
	@Column(name="ORDER_ID")
	private Long id;

	private Member member;

	private LocalDateTime orderDate; // ORDER_DATE, order_date
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
}
