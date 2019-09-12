package addressbook.model;

import javax.persistence.Entity;

@Entity
public class FamilyContact extends Contact {

	private boolean isFriend;
	
	public FamilyContact() {}
	
	public FamilyContact(String name, String firstLastName, String secondLastName, String phone, String address, boolean isFriend) {
		super(name, firstLastName, secondLastName, phone, address);
		this.isFriend = isFriend;
	}

	public boolean isFriend() {
		return isFriend;
	}

	public void setFriend(boolean isFriend) {
		this.isFriend = isFriend;
	}

	@Override
	public String toString() {
		return String.format("%s, %s", super.toString(), this.isFriend? "friend" : "family");
	}
		

}
