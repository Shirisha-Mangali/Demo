package com.cognine.utils;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class EmailSender {

	@Value("${hrMail}")
	private String hrMail;
	@Value("${pmoMail}")
	private String pmoMail;

	@Value("${spring.mail.username}")
	private String senderMail;

	@Value("${mailTemplateWithCurrentReportingHead}")
	private String mailTemplateWithCurrentReportingHead;

	@Value("${mailTemplateWithoutCurrentReportingHead}")
	private String mailTemplateWithoutCurrentReportingHead;

	@Value("${reminderMailTemplateWithCurrentReportingHead}")
	private String reminderMailTemplateWithCurrentReportingHead;

	@Value("${reminderMailTemplateWithoutCurrentReportingHead}")
	private String reminderMailTemplateWithoutCurrentReportingHead;

	@Value("${subjectMail}")
	private String subjectMail;

	@Value("${reminderSubjectMail}")
	private String reminderSubjectMail;

	@Autowired
	private JavaMailSender javaMailSender;

	@Async
	public void sendEmail(String currentLoginUserName, int employeeId, String employeeName, String currentReportingHead,
			String newReportingHead) {
		if (!newReportingHead.equals(currentReportingHead)) {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = null;
			String body = mailTemplateReplace(mailTemplateWithCurrentReportingHead, currentLoginUserName, employeeId,
					employeeName, newReportingHead);

			body = body.replace("{{currentReportingHead}}", currentReportingHead);
			mimeMessage = templateSetting(mimeMessage, body, mimeMessageHelper);
			javaMailSender.send(mimeMessage);
		}
	}

	@Async
	public void sendEmailWithoutCurrentReportingHead(String currentLoginUserName, int employeeId, String employeeName,
			String newReportingHead) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = null;
		String body = mailTemplateReplace(mailTemplateWithoutCurrentReportingHead, currentLoginUserName, employeeId,
				employeeName, newReportingHead);
		mimeMessage = templateSetting(mimeMessage, body, mimeMessageHelper);
		javaMailSender.send(mimeMessage);

	}

	public String mailTemplateReplace(String mailTemplateWithCurrentReportingHead, String currentLoginUserName,
			int employeeId, String employeeName, String newReportingHead) {
		String body = mailTemplateWithCurrentReportingHead.replace("{{currentLoginUserName}}", currentLoginUserName);
		body = body.replace("{{employeeName}}", employeeName);

		body = body.replace("{{employeeId}}", "" + employeeId);
		body = body.replace("{{newReportingHead}}", newReportingHead);
		return body;

	}

	public String remainderMailTemplateReplace(String mailTemplateWithCurrentReportingHead, int employeeId,
			String employeeName, String newReportingHead) {
		String body = mailTemplateWithCurrentReportingHead.replace("{{employeeName}}", employeeName);

		body = body.replace("{{employeeId}}", "" + employeeId);
		body = body.replace("{{newReportingHead}}", newReportingHead);
		return body;

	}

	public MimeMessage templateSetting(MimeMessage mimeMessage, String body, MimeMessageHelper mimeMessageHelper) {
		try {

			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(senderMail);
			mimeMessageHelper.setTo(InternetAddress.parse(hrMail + "," + pmoMail));
			mimeMessageHelper.setText(body);
			mimeMessage.setContent(body, "text/html");
			mimeMessageHelper.setSubject(subjectMail);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return mimeMessage;
	}

	public MimeMessage remainderTemplateSetting(MimeMessage mimeMessage, String body,
			MimeMessageHelper mimeMessageHelper) {
		try {

			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(senderMail);
			mimeMessageHelper.setTo(InternetAddress.parse(hrMail));
			mimeMessageHelper.setText(body);
			mimeMessage.setContent(body, "text/html");
			mimeMessageHelper.setSubject(reminderSubjectMail);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return mimeMessage;
	}

	public void sendScheduledEmailToChangeReportingHead(int employeeId, String employeeName,
			String currentReportingHead, String newReportingHead) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = null;
		String body = null;
		if (currentReportingHead == null) {
			body = remainderMailTemplateReplace(reminderMailTemplateWithoutCurrentReportingHead, employeeId,
					employeeName, newReportingHead);
		} else {
			body = remainderMailTemplateReplace(reminderMailTemplateWithCurrentReportingHead, employeeId, employeeName,
					newReportingHead);
			body = body.replace("{{currentReportingHead}}", currentReportingHead);
		}
		mimeMessage = remainderTemplateSetting(mimeMessage, body, mimeMessageHelper);
		javaMailSender.send(mimeMessage);

	}
}
