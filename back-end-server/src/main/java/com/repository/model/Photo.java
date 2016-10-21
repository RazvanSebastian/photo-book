package com.repository.model;

import java.sql.Blob;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="photo")
public class Photo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="album")
	private PhotoAlbum album;
	
	@NotNull
	private String name;
	
	private String description;
	
	@NotNull
	private String category;
	
	@NotNull
	private Long visualisations;
	
	@NotNull
	private double rating;

	@NotNull
	private Date date;
	
	@NotNull
	private Blob image;
	
	public Photo(){
		
	}
	
	

	public Photo(String name, String description, String category, Long visualisations, double rating, Date date,
			Blob image) {
		this.name = name;
		this.description = description;
		this.category = category;
		this.visualisations = visualisations;
		this.rating = rating;
		this.date = date;
		this.image = image;
	}



	public Photo(Long id, String name, String description, String category, Long visualisations, double rating,
			Date date, Blob image) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.category = category;
		this.visualisations = visualisations;
		this.rating = rating;
		this.date = date;
		this.image = image;
	}
	
	
	
	
	public Photo( PhotoAlbum album, String name, String description, String category, Long visualisations,
			double rating, Date date, Blob image) {
		this.album = album;
		this.name = name;
		this.description = description;
		this.category = category;
		this.visualisations = visualisations;
		this.rating = rating;
		this.date = date;
		this.image = image;
	}




	public PhotoAlbum getAlbum() {
		return album;
	}

	public void setAlbum(PhotoAlbum album) {
		this.album = album;
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

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}
}
