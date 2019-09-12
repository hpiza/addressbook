package addressbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import addressbook.model.WorkContact;

public interface WorkContactRepository extends JpaRepository<WorkContact, Integer> {

	 List<WorkContact> findByNameContaining(String text);
	 
}
