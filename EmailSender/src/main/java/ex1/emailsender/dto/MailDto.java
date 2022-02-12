package ex1.emailsender.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MailDto {

	private String email;
	private String title;
	private String content;

	@Builder
	public MailDto(String email, String title, String content){
		this.email = email;
		this.title = title;
		this.content = content;
	}
}
