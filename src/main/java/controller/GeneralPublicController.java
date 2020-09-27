package controller;

import model.vo.GeneralPublicVO;

public class GeneralPublicController {
	public String store(GeneralPublicVO generalPublic) {
		return "salvo";
	}
	
	private String validate(GeneralPublicVO generalPublic) {
		return "alguma coisa";
	}
}
