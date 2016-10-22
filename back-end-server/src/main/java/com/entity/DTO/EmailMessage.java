package com.entity.DTO;

public class EmailMessage {
	private String email;
	private String name;
	private String company;
	private String message;
	
	
	
	public EmailMessage(String email, String name, String company, String message) {
		super();
		this.email = email;
		this.name = name;
		this.company = company;
		this.message = message;
	}

	public EmailMessage() {
		super();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
