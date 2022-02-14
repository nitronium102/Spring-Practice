package ex1.emailsender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;

@SpringBootApplication
public class EmailSenderApplication {

	@Bean
	public JavaMailSenderImpl javaMailSender(){
		return new JavaMailSenderImpl();
	}

	public static void main(String[] args) {
		SpringApplication.run(EmailSenderApplication.class, args);
	}

}
