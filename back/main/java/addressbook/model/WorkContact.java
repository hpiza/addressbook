package addressbook.model;

import javax.persistence.Entity;

@Entity
public class WorkContact extends Contact {

	private String companyName, area, businessSector;
	
	public WorkContact() {
		
	}
	
	public WorkContact(String name, String firstLastName, String secondLastName, String phone, String address,
			           String companyName, String area, String businessSector) {
		super(name, firstLastName, secondLastName, phone, address);
		this.companyName = companyName;
		this.area = area;
		this.businessSector = businessSector;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public String getArea() {
		return this.area;
	}

	public String getBusinessSector() {
		return this.businessSector;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setBusinessSector(String businessSector) {
		this.businessSector = businessSector;
	}

	@Override
	public String toString() {
		return String.format("%s, companyName= %s", super.toString(), companyName);
	}	

}
