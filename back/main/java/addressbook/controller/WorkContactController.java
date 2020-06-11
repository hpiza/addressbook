package addressbook.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import addressbook.model.WorkContact;
import addressbook.repository.WorkContactRepository;

@Controller
@RequestMapping(path = "/work")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class WorkContactController {
	
	@Autowired
	private WorkContactRepository workContactRepository;

	@GetMapping("/search/{name}")
	public @ResponseBody Iterable<WorkContact> getFamilyContactsByName(@PathVariable String name) {
		Iterable<WorkContact> ite = workContactRepository.findByNameContaining(name);
		return ite;
	}
	
	@PostMapping("/add")
	public @ResponseBody WorkContact addFamilyContact(@RequestBody Map<String, String> body) {
		System.out.println(body);
		WorkContact wc = new WorkContact();
		wc.setAddress(body.get("address"));
		wc.setFirstLastName(body.get("firstLastName"));
		wc.setName(body.get("name"));
		wc.setPhone(body.get("phone"));
		wc.setSecondLastName(body.get("secondLastName"));
		wc.setBusinessSector(body.get("businessSector"));
		wc.setCompanyName(body.get("companyName"));
		wc.setArea(body.get("area"));
		return workContactRepository.save(wc);
	}
	
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Integer> delete(@PathVariable String id){
        int workContactId = Integer.parseInt(id);
        workContactRepository.deleteById(workContactId);
        return new ResponseEntity<>(workContactId, HttpStatus.OK);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<WorkContact> update(@PathVariable String id, @RequestBody Map<String, String> body){
        int workContactId = Integer.parseInt(id);
        Optional<WorkContact> optional = workContactRepository.findById(workContactId);
        WorkContact wc = optional.get();
		wc.setAddress(body.get("address"));
		wc.setFirstLastName(body.get("firstLastName"));
		wc.setName(body.get("name"));
		wc.setPhone(body.get("phone"));
		wc.setSecondLastName(body.get("secondLastName"));
		wc.setBusinessSector(body.get("businessSector"));
		wc.setCompanyName(body.get("companyName"));
		wc.setArea(body.get("area"));
		workContactRepository.save(wc);
		return new ResponseEntity<>(wc, HttpStatus.OK);
    }

}
