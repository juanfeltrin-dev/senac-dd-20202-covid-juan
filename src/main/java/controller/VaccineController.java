package controller;

import java.util.ArrayList;
import java.util.List;

import model.dao.ResearcherDAO;
import model.dao.VaccineDAO;
import model.vo.ResearcherVO;

public class VaccineController {
	public ArrayList<String> getStages() {
		VaccineDAO vaccineDAO = new VaccineDAO();
		
		return vaccineDAO.getStages();
	}
	
	public List<ResearcherVO> getReasearchers() {
		ResearcherDAO researcherDAO = new ResearcherDAO();
		return researcherDAO.all();
	}
}
