package addressbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import addressbook.model.FamilyContact;

public interface FamilyContactRepository extends JpaRepository<FamilyContact, Integer> {

	 List<FamilyContact> findByNameContaining(String text);
	 
}
