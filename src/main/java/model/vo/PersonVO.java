package model.vo;

import java.sql.Date;

public class PersonVO {
	private int id;
	private String name;
	private Date birth;
	private String genre;
	private String document;
	private VaccineVO vaccine;
	
	public PersonVO(int id, String name, Date birth, String genre, String document, VaccineVO vaccine) {
		super();
		this.id 		= id;
		this.name 		= name;
		this.birth 		= birth;
		this.genre 		= genre;
		this.document 	= document;
		this.vaccine 	= vaccine;
	}
	
	public PersonVO() {
		super();
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getBirth() {
		return birth;
	}
	
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getDocument() {
		return document;
	}
	
	public void setDocument(String document) {
		this.document = document;
	}

	public VaccineVO getVaccine() {
		return vaccine;
	}

	public void setVaccine(VaccineVO vaccine) {
		this.vaccine = vaccine;
	}
	
}
