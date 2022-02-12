package ex1.emailsender.service;

import ex1.emailsender.dto.ContactDto;
import ex1.emailsender.dto.MailDto;
import ex1.emailsender.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Component
public class ContactService {

	private final ContactRepository contactRepository;

	@Autowired
	private JavaMailSender mailSender; // MIME을 지원, JavaMailSender의 구현체인 JavaMailSenderImple Bean이 DI됨

	private static final String TO_ADDRESS = "받는 사람 이메일";

	public void sendMail(MailDto mailDto) {
		try {
			MailHandler mailHandler = new MailHandler(mailSender);
			mailHandler.setTo(ContactService.TO_ADDRESS);
			mailHandler.setFrom(mailDto.getEmail()); // application.yml에서 @Value로 가져와도 된다!(기본 setting)
			mailHandler.setSubject("제목 설정");
			// html layout
			String htmlContent = "<p>" + mailDto.getContent() + "<p>" + mailDto.getEmail() + "<p>";
			mailHandler.setText(htmlContent, true);
			// 첨부파일
			//mailHandler.setAttach("test.txt", "static/test.txt");
			// 이미지 삽입
			//mailHandler.setInline("test-img", "static/test.jpg");
			mailHandler.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveContact(ContactDto contactDto) {
		contactRepository.save(contactDto.toEntity());
	}

}
