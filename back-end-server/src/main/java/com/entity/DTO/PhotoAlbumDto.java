package com.entity.DTO;

import java.util.Date;


public class PhotoAlbumDto {

	private Long id;
	private String name;
	private String description;
	private String category;
	private Date date;
	private String coverImage;
	
	public PhotoAlbumDto() {
	}
	
	public PhotoAlbumDto(String name, String description, String category, Date date, String coverImage) {
		this.name = name;
		this.description = description;
		this.category = category;
		this.date = date;
		this.coverImage = coverImage;
	}
	
	public PhotoAlbumDto(Long id, String name, String description, String category, Date date, String coverImage) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.category = category;
		this.date = date;
		this.coverImage = coverImage;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}	
}
