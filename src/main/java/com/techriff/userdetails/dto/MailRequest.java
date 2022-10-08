package com.techriff.userdetails.dto;

public class MailRequest {
	private String name;
	private String to;
	private String from;
	private String subject;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public MailRequest(String name, String to, String from, String subject) {
		super();
		this.name = name;
		this.to = to;
		this.from = from;
		this.subject = subject;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	@Override
	public String toString() {
		return "MailRequest [name=" + name + ", to=" + to + ", from=" + from + ", subject=" + subject + ", getName()="
				+ getName() + ", getTo()=" + getTo() + ", getFrom()=" + getFrom() + ", getSubject()=" + getSubject()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	public MailRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
