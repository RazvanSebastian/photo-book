package com.repository.model;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.persistence.*;

@Entity
@Table(name="stats")
public class HomePageStats {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	
	@Column(name="name",unique=true)
	String name;
	
	@Column(name="value")
	Long number;
	 
	public HomePageStats() {
		super();
	}

	public HomePageStats( String name, Long number) {
		this.name = name;
		this.number = number;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}
}

