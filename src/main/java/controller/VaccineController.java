package controller;

import java.util.ArrayList;

import model.dao.ResearcherDAO;
import model.dao.VaccineDAO;
import model.vo.VaccineVO;

public class VaccineController {
	public ArrayList<String> getStages() {
		VaccineDAO vaccineDAO = new VaccineDAO();
		
		return vaccineDAO.getStages();
	}
	
	public ArrayList<String> getReasearchers() {
		ResearcherDAO researcherDAO = new ResearcherDAO();
		
		return researcherDAO.allByName();
	}
	
	public String store(VaccineVO vaccine) {
		String validate = this.validate(vaccine);
		
		if (!validate.isEmpty()) {
			return validate;
		}

		return "Sucesso!";
	}
	
	private String validate(VaccineVO vaccine) {
		if (vaccine.getCountry().length() < 3) {
			return "Pa�s de origem deve conter no m�nimo 3 caracteres";
		}
		
		if (vaccine.getStartDate() == null) {
			return "Data de in�cio da pesquisa � obrigat�rio";
		}
		
		return "";
	}
}
