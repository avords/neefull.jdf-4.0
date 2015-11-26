package com.mvc.component.mail;

public interface EmailManager {
	public int sendEmail(MailConfig mailConifg);
	/**
	 * @param sender
	 * @param receiver More than one receiver split with semicolon
	 * @param subject
	 * @return
	 */
	public int sendEmail(String sender,String receiver,String subject);
}
