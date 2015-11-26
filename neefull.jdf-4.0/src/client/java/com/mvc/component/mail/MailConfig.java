package com.mvc.component.mail;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

public class MailConfig implements Serializable{

	private String from;

	private String to;

	private String bcc;
	
	private String cc;
	
	private boolean receipt;
	
	private String receiptTo;
	
	private String subject;

	private String content;

	private String fileId;

	Map<String,byte[]> map ;

	private byte[][] fileContents;
	
	private File[] files; 

	private String[] fileNames;

	public String getFrom() {
    	return from;
    }

	public void setFrom(String from) {
    	this.from = from;
    }

	public String getTo() {
    	return to;
    }

	public void setTo(String to) {
    	this.to = to;
    }

	public String getSubject() {
    	return subject;
    }

	public void setSubject(String subject) {
    	this.subject = subject;
    }

	public String getContent() {
    	return content;
    }

	public void setContent(String content) {
    	this.content = content;
    }

	public String getFileId() {
    	return fileId;
    }

	public void setFileId(String fileId) {
    	this.fileId = fileId;
    }

	public Map<String, byte[]> getMap() {
    	return map;
    }

	public void setMap(Map<String, byte[]> map) {
    	this.map = map;
    }

	public byte[][] getFileContents() {
    	return fileContents;
    }

	public void setFileContents(byte[][] fileContents) {
    	this.fileContents = fileContents;
    }

	public String[] getFileNames() {
    	return fileNames;
    }

	public void setFileNames(String[] fileNames) {
    	this.fileNames = fileNames;
    }

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public boolean isReceipt() {
		return receipt;
	}

	public void setReceipt(boolean receipt) {
		this.receipt = receipt;
	}

	public File[] getFiles() {
		return files;
	}

	public void setFiles(File[] files) {
		this.files = files;
	}

	public String getReceiptTo() {
		return receiptTo;
	}

	public void setReceiptTo(String receiptTo) {
		this.receiptTo = receiptTo;
	}
}
