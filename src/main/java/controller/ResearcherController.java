package controller;

import model.vo.ResearcherVO;

public class ResearcherController {
	public String store(ResearcherVO researcher) {
		String validate = this.validate(researcher);
		
		if (!validate.isEmpty()) {
			return validate;
		}

		return "Sucesso!";
	}
	
	private String validate(ResearcherVO researcher) {
		if (researcher.getName().length() < 3) {
			return "Nome n�o pode ser menor que 3 caracteres";
		}
		
		if (researcher.getGenre().length() == 0) {
			return "O campo sexo � obrigat�rio";
		}
		
		if (researcher.getDocument().length() != 14) {
			return "CPF deve conter 11 d�gitos";
		}
		
		if (researcher.getInstitution().length() == 0) {
			return "O campo institui��o � obrigat�rio";
		}
		
		return "";
	}
}
