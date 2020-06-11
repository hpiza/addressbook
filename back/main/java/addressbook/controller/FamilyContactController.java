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
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import addressbook.model.FamilyContact;
import addressbook.repository.FamilyContactRepository;

@Controller
@RequestMapping(path = "/family")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class FamilyContactController {

	@Autowired
	private FamilyContactRepository familyContactRepository;

	@GetMapping("/search/{name}")
	public @ResponseBody Iterable<FamilyContact> getFamilyContactsByName(@PathVariable String name) {
		Iterable<FamilyContact> ite = familyContactRepository.findByNameContaining(name);
		return ite;
	}
	
	@PostMapping("/add")
	public @ResponseBody FamilyContact addFamilyContact(@RequestBody Map<String, String> body) {
		System.out.println(body);
		FamilyContact fc = new FamilyContact();
		fc.setAddress(body.get("address"));
		fc.setFirstLastName(body.get("firstLastName"));
		fc.setFriend(body.get("friend").equals("true"));
		fc.setName(body.get("name"));
		fc.setPhone(body.get("phone"));
		fc.setSecondLastName(body.get("secondLastName"));
		return familyContactRepository.save(fc);
	}
	
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Integer> delete(@PathVariable String id){
        int familyContactId = Integer.parseInt(id);
        familyContactRepository.deleteById(familyContactId);
        return new ResponseEntity<>(familyContactId, HttpStatus.OK);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<FamilyContact> update(@PathVariable String id, @RequestBody Map<String, String> body){
        int familyContactId = Integer.parseInt(id);
        Optional<FamilyContact> optional = familyContactRepository.findById(familyContactId);
        FamilyContact fc = optional.get();
		fc.setAddress(body.get("address"));
		fc.setFirstLastName(body.get("firstLastName"));
		fc.setFriend(body.get("friend").equals("true"));
		fc.setName(body.get("name"));
		fc.setPhone(body.get("phone"));
		fc.setSecondLastName(body.get("secondLastName"));
		familyContactRepository.save(fc);
		return new ResponseEntity<>(fc, HttpStatus.OK);
    }

}
