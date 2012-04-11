package com.guilhermelias.fixture.dumbster;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailMessageFixture {

	public static final String SMTP_SERVER_PORT = "9999";
	private final Message message = new MimeMessage(Session.getDefaultInstance(loadSmtpSettings()));

	private Properties loadSmtpSettings() {
		Properties smtpSettings = new Properties();
		smtpSettings.put("mail.smtp.host", "localhost");
		smtpSettings.put("mail.smtp.port", SMTP_SERVER_PORT); // fake port
		return smtpSettings;
	}

	public static MailMessageFixture get() {
		return new MailMessageFixture();
	}

	public MailMessageFixture fromAddress(String string) {
		try {
			this.message.setFrom(new InternetAddress(string));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public MailMessageFixture to(String string) {
		try {
			this.message.setRecipient(RecipientType.TO, new InternetAddress(string));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public MailMessageFixture withSubject(String string) {
		try {
			this.message.setSubject(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public MailMessageFixture withBody(String string) {
		try {
			this.message.setContent(string, "text/plain");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public Message build() {
		return this.message;
	}
}
