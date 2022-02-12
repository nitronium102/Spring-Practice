package ex1.emailsender.dto;

import ex1.emailsender.domain.Contact;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class ContactDto {

	@NotBlank
	private String writerEmail;

	@NotBlank
	private String content;

	public Contact toEntity(){
		return Contact.builder()
			.writerEmail(this.writerEmail)
			.content(this.content)
			.build();
	}
}
