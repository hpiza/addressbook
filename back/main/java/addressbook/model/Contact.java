package addressbook.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Contact {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
	private String name, firstLastName, secondLastName;
	private String phone, address;
	
	public Contact() {}
	
	public Contact(String name, String firstLastName, String secondLastName,
			String phone, String address) {
		this.name = name;
		this.firstLastName = firstLastName;
		this.secondLastName = secondLastName;
		this.phone = phone;
		this.address = address;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstLastName() {
		return firstLastName;
	}

	public void setFirstLastName(String firstLastName) {
		this.firstLastName = firstLastName;
	}

	public String getSecondLastName() {
		return secondLastName;
	}

	public void setSecondLastName(String secondLastName) {
		this.secondLastName = secondLastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return String.format("<name=%s, firstLastName=%s, secondLastName=%s", this.name, this.firstLastName, this.secondLastName);
	}	
		
}
