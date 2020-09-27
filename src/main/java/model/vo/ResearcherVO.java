package model.vo;

import java.time.LocalDate;

public class ResearcherVO extends PersonVO {
	private String institution;

	public ResearcherVO(int id, String name, LocalDate birth, String genre, String document, VaccineVO vaccine, String institution) {
		super(id, name, birth, genre, document, vaccine);
		this.institution = institution;
	}

	public ResearcherVO() {
		super();
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}
	
}
