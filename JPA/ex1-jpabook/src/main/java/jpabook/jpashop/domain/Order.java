package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ORDERS")
@Getter @Setter
public class Order {

	@Id @GeneratedValue
	@Column(name="ORDER_ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems = new ArrayList<>();

	private LocalDateTime orderDate; // ORDER_DATE, order_date

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

//	public void addOrderItem(OrderItem orderItem) {
//		orderItems.add(orderItem);
//		orderItem.setOrder(this);
//	}
}
