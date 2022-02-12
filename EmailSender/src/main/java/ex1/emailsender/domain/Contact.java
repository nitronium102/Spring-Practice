package ex1.emailsender.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long contactId;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String writerEmail;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	@Builder
	public Contact(Long contactId, String writerEmail, String content){
		this.contactId = contactId;
		this.writerEmail = writerEmail;
		this.content = content;
	}
}
