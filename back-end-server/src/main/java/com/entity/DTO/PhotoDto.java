package com.entity.DTO;

import java.sql.Blob;
import java.util.Date;


public class PhotoDto {
	
	private Long id;
	private String name;	
	private String description;	
	private String category;	
	private Long visualisations;
	private double rating;
	private Date date;
	private String image;
	
	public PhotoDto() {
		super();
	}
	
	public PhotoDto(String image) {
		super();
		this.image = image;
	}

	public PhotoDto(String name, String description, String category, Long visualisations, double rating, Date date,
			String image) {
		this.name = name;
		this.description = description;
		this.category = category;
		this.visualisations = visualisations;
		this.rating = rating;
		this.date = date;
		this.image = image;
	}



	public PhotoDto(Long id, String name, String description, String category, Long visualisations, double rating,
			Date date, String image) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.category = category;
		this.visualisations = visualisations;
		this.rating = rating;
		this.date = date;
		this.image = image;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getVisualisations() {
		return visualisations;
	}

	public void setVisualisations(Long visualisations) {
		this.visualisations = visualisations;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
