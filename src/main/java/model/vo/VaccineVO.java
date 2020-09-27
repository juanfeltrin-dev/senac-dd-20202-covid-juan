package model.vo;

import java.sql.Date;
import java.time.LocalDate;

public class VaccineVO {
	private int id;
	private String country;
	private String stage;
	private LocalDate startDate;
	private ResearcherVO researcher;
	private int evaluation;
	
	public VaccineVO(int id, String country, String stage, LocalDate startDate, ResearcherVO researcher, int evaluation) {
		super();
		this.id 		= id;
		this.country 	= country;
		this.stage 		= stage;
		this.startDate 	= startDate;
		this.researcher = researcher;
		this.evaluation = evaluation;
	}
	
	public VaccineVO() {
		super();
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getStage() {
		return stage;
	}
	
	public void setStage(String stage) {
		this.stage = stage;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public ResearcherVO getResearcher() {
		return researcher;
	}
	
	public void setResearcher(ResearcherVO researcher) {
		this.researcher = researcher;
	}

	public int getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(int evaluation) {
		this.evaluation = evaluation;
	}
	
}
