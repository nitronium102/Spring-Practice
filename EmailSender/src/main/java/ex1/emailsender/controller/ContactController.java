package ex1.emailsender.controller;

import ex1.emailsender.dto.ContactDto;
import ex1.emailsender.dto.MailDto;
import ex1.emailsender.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ContactController {

	public final ContactService contactService;

	@PostMapping("/contact")
	public ResponseEntity<Object> sendMail(@Valid @RequestBody ContactDto contactDto){
		MailDto mailDto = MailDto.builder()
			.email(contactDto.getWriterEmail())
			.title("[이메일 제목]")
			.content(contactDto.getContent())
			.build();

		contactService.sendMail(mailDto);
		contactService.saveContact(contactDto);

		return ResponseEntity.ok(201);
	}
}
