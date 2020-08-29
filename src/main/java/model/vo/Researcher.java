package model.vo;

import java.util.Date;

public class Researcher extends Person {
	private String institution;

	public Researcher(String name, Date birth, String genre, String document, Vaccine vaccine, String institution) {
		super(name, birth, genre, document, vaccine);
		this.institution = institution;
	}

	public Researcher() {
		super();
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}
	
}
