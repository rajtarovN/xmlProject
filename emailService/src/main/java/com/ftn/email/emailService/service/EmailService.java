package com.ftn.email.emailService.service;

import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ftn.email.emailService.model.Email;
import com.sun.istack.ByteArrayDataSource;

@Service
public class EmailService { 
 
	@Autowired
	private Environment env;

	@Autowired
	private JavaMailSender mailSender;

	public void sendMail(String txt) throws MessagingException, JAXBException {

		InputStream inputStream = new org.apache.cxf.io.ReaderInputStream(new StringReader(txt));

		JAXBContext context = JAXBContext.newInstance(Email.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Email request = (Email) unmarshaller.unmarshal(inputStream);
		
		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

		helper.setTo(request.getTo());
		helper.setFrom(env.getProperty("spring.mail.username"));
		helper.setSubject(request.getSubject());
		helper.setText(request.getContent());

		if (request.getPdf() != null) {
			ByteArrayDataSource bds = new ByteArrayDataSource((byte[]) request.getPdf(), "application/pdf");
			System.out.println(bds);
			helper.addAttachment("attachment.pdf", bds);
		}

		if (request.getXhtml() != null) {
			ByteArrayDataSource bds = new ByteArrayDataSource((byte[]) request.getXhtml(), "application/xhtml+xml");
			System.out.println(bds);
			helper.addAttachment("attachment.html", bds);
		}

		mailSender.send(message);

	}
}
