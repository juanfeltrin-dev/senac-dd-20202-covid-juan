package model.vo;

import java.util.Date;

public class Person {
	private String name;
	private Date birth;
	private String genre;
	private String document;
	private Vaccine vaccine;
	
	public Person(String name, Date birth, String genre, String document, Vaccine vaccine) {
		super();
		this.name 		= name;
		this.birth 		= birth;
		this.genre 		= genre;
		this.document 	= document;
		this.vaccine 	= vaccine;
	}
	
	public Person() {
		super();
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

	public Vaccine getVaccine() {
		return vaccine;
	}

	public void setVaccine(Vaccine vaccine) {
		this.vaccine = vaccine;
	}
	
}
