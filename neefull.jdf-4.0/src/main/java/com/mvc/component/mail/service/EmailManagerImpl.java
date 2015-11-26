package com.mvc.component.mail.service;

import java.io.File;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.mvc.component.mail.EmailManager;
import com.mvc.component.mail.MailConfig;

@Service("emailManager")
public class EmailManagerImpl implements EmailManager {
	
	private static final Logger LOGGER = Logger.getLogger(EmailManagerImpl.class);
	
	@Autowired
	@Qualifier("javaMailSender")
	private JavaMailSenderImpl mailSender;

	@Value("${mail.send}")
	private boolean sendMail;

	@Value("${mail.dev.alias}")
	private String mailDevAlias;

	@Value("${mail.from}")
	private String mailFrom;

	@Value("${mail.from.name}")
	private String mailFromName;

	public static void main(String[] args) {
		EmailManager emailManager = new EmailManagerImpl();
		emailManager.sendEmail("aa", "448700174", "test");
	}

	@Override
	public int sendEmail(MailConfig mailConfig) {
		try {
			if (sendMail) {
				String alias = mailDevAlias;
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
				if (StringUtils.isBlank(alias)) {
					helper.setTo(mailConfig.getTo());
					if (mailConfig.getCc() != null && mailConfig.getCc().length() > 0) {
						helper.setCc(mailConfig.getCc());
					}
					if (mailConfig.getBcc() != null && mailConfig.getBcc().length() > 0) {
						helper.setBcc(mailConfig.getBcc());
					}
				} else {
					if (alias.indexOf(",") != -1) {
						helper.setTo(alias.split(","));
					} else {
						helper.setTo(alias);
					}
				}
				InternetAddress from = new InternetAddress();
				from.setAddress(mailFrom);
				from.setPersonal(mailFromName);
				if (mailConfig.getFileNames() != null) {
					for (int i = 0; i < mailConfig.getFileNames().length; i++) {
						addAttachFile(helper.getMimeMultipart(), mailConfig.getFiles()[i], mailConfig.getFileNames()[i]);
					}
				}
				helper.setFrom(from);
				helper.setSubject(mailConfig.getSubject());
				helper.setText(mailConfig.getContent() == null?"":mailConfig.getContent(), true);

				if (mailConfig.isReceipt()) {
					if (StringUtils.isBlank(mailConfig.getReceiptTo())) {
						message.setHeader("Disposition-Notification-To", mailConfig.getReceiptTo());
					}
				}
				mailSender.send(message);
			} else {
				LOGGER.error("won't send email, because the send.mail=false");
			}
		} catch (Exception e) {
			LOGGER.error("sending email error log", e);
		}
		return 0;
	}
	
	private void addAttachFile(Multipart multipart, File file, String fileName) throws MessagingException {
		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setFileName(fileName);
		FileDataSource fds = new FileDataSource(file);
		mimeBodyPart.setDataHandler(new DataHandler(fds));
		mimeBodyPart.setHeader("Content-ID", "<"+ fileName+">");
		mimeBodyPart.setHeader("Content-Location", fileName);
		multipart.addBodyPart(mimeBodyPart);
	}

	@Override
	public int sendEmail(String sender, String receiver, String subject) {
		MailConfig mailConfig = new MailConfig();
		mailConfig.setFrom(sender);
		mailConfig.setTo(receiver);
		mailConfig.setSubject(subject);
		return sendEmail(mailConfig);
	}

}
