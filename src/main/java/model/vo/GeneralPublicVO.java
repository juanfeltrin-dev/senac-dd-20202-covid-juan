package model.vo;

import java.time.LocalDate;

public class GeneralPublicVO extends PersonVO {
	private boolean volunteers;

	public GeneralPublicVO(int id, String name, LocalDate birth, String genre, String document, VaccineVO vaccine, boolean volunteers) {
		super(id, name, birth, genre, document, vaccine);
		this.volunteers = volunteers;
	}

	public GeneralPublicVO() {
		super();
	}

	public boolean isVolunteers() {
		return volunteers;
	}

	public void setVolunteers(boolean volunteers) {
		this.volunteers = volunteers;
	}
	
}
