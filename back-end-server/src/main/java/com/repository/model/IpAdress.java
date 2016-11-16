package com.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "client")
public class IpAdress {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(unique=true)
	private String ip;

	private String date;

	/**
	 * @param number
	 *            of email send today
	 */
	private int numb;

	public IpAdress() {
	}

	public IpAdress(String ip, String date, int numb) {
		super();
		this.ip = ip;
		this.date = date;
		this.numb = numb;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getNumb() {
		return numb;
	}

	public void setNumb(int numb) {
		this.numb = numb;
	}
	
	/**
	 * @param Return true and set values if the object is not null and false if object is null 
	 */
	 
	public void setValues(IpAdress client) {
			this.id = client.id;
			this.ip = client.ip;
			this.numb = client.numb;
			this.date = client.date;
	
	}

}
