package com.guilhermelias.sample.dumbster;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.mail.Message;
import javax.mail.Transport;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dumbster.smtp.SimpleSmtpServer;
import com.guilhermelias.fixture.dumbster.MailMessageFixture;

public class EmailTestingSample {

	private static final int SMTP_SERVER_PORT = Integer.parseInt(MailMessageFixture.SMTP_SERVER_PORT);
	private SimpleSmtpServer fakeSmtpServer;

	@Before
	public void setUp() throws Exception {
		givenThatSmtpServerIsRunning();
	}

	@Test
	public void shouldLoadAFakeSmtpServerAndSendAnEmail() {

		try {
			Message message = MailMessageFixture.get().fromAddress("from@empresa.com").to("to-mail@empresa.com").withSubject("Message sent!").withBody("Woolla").build();
			Transport.send(message);
			checkEmailSentSuccessfully();
		} catch (Exception e) {
			e.printStackTrace();
			fail("An unexpectec exception occured while sending mail: " + e);
		}
	}

	private void checkEmailSentSuccessfully() {
		assertTrue("The e-mail was not sent!!", this.fakeSmtpServer.getReceivedEmailSize() == 1);
	}

	@After
	public void tearDown() throws Exception {
		thenStopSmtpServer();
	}

	private void thenStopSmtpServer() throws InterruptedException {
		int times = 0;
		this.fakeSmtpServer.stop();
		while (!fakeSmtpServer.isStopped()) {
			if (times > 10)
				fail("server does not stopped!!");
			Thread.sleep(3000);
			times++;
		}

	}

	private void givenThatSmtpServerIsRunning() throws InterruptedException {
		int times = 0;
		this.fakeSmtpServer = SimpleSmtpServer.start(SMTP_SERVER_PORT);
		while (fakeSmtpServer.isStopped()) {
			if (times > 10)
				break;
			Thread.sleep(3000);
			times++;
		}
	}

}
