package model.vo;

import java.util.Date;

public class Vaccine {
	private String country;
	private String stage;
	private Date startDate;
	private Researcher researcher;
	private int evaluation;
	
	public Vaccine(String country, String stage, Date startDate, Researcher researcher, int evaluation) {
		super();
		this.country 	= country;
		this.stage 		= stage;
		this.startDate 	= startDate;
		this.researcher = researcher;
		this.evaluation = evaluation;
	}
	
	public Vaccine() {
		super();
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
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Researcher getResearcher() {
		return researcher;
	}
	
	public void setResearcher(Researcher researcher) {
		this.researcher = researcher;
	}

	public int getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(int evaluation) {
		this.evaluation = evaluation;
	}
	
}
