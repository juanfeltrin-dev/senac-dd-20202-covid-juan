package model.vo;

import java.util.Date;

public class GeneralPublic extends Person {
	private boolean volunteers;

	public GeneralPublic(String name, Date birth, String genre, String document, Vaccine vaccine, boolean volunteers) {
		super(name, birth, genre, document, vaccine);
		this.volunteers = volunteers;
	}

	public GeneralPublic() {
		super();
	}

	public boolean isVolunteers() {
		return volunteers;
	}

	public void setVolunteers(boolean volunteers) {
		this.volunteers = volunteers;
	}
	
}
