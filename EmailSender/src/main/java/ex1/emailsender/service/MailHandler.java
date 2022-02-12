package ex1.emailsender.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

public class MailHandler {

	private JavaMailSender sender;
	private MimeMessage message;
	private MimeMessageHelper messageHelper;

	public MailHandler(JavaMailSender jsender) throws MessagingException {
		this.sender = jsender;
		message = jsender.createMimeMessage();
		messageHelper = new MimeMessageHelper(message, true, "UTF-8");
	}

	public void setFrom(String fromAddress) throws MessagingException {
		messageHelper.setFrom(fromAddress);
	}

	public void setTo(String email) throws MessagingException {
		messageHelper.setTo(email);
	}

	public void setSubject(String subject) throws MessagingException {
		messageHelper.setSubject(subject);
	}

	public void setText(String text, boolean useHtml) throws MessagingException {
		messageHelper.setText(text, useHtml);
	}

	// 첨부 파일
//	public void setAttach(String displayFileName, String pathToAttachment) throws MessagingException, IOException {
//		File file = new ClassPathResource(pathToAttachment).getFile();
//		FileSystemResource fsr = new FileSystemResource(file);
//
//		messageHelper.addAttachment(displayFileName, fsr); // 메일에 노출된 파일 이름, 파일의 경로
//	}

	// 이미지 삽입
//	public void setInline(String contentId, String pathToInline) throws MessagingException, IOException {
//		File file = new ClassPathResource(pathToInline).getFile();
//		FileSystemResource fsr = new FileSystemResource(file);
//
//		messageHelper.addInline(contentId, fsr); // 삽입된 이미지의 id attribute, 파일의 경로
//	}

	public void send() {
		try {
			sender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
