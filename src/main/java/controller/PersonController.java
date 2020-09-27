package controller;

import java.util.ArrayList;

import model.dao.PersonDAO;

public class PersonController {
	public ArrayList<String> getGenre() {
		PersonDAO personDAO = new PersonDAO();
		
		return personDAO.getGenre();
	}
}
