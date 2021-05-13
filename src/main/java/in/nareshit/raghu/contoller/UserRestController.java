package in.nareshit.raghu.contoller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.nareshit.raghu.model.User;
import in.nareshit.raghu.model.UserRequest;
import in.nareshit.raghu.model.UserResponse;
import in.nareshit.raghu.repo.UserRepository;
import in.nareshit.raghu.service.IUserservice;
import in.nareshit.raghu.util.JWTUtil;

@RestController
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	private IUserservice iuserservice;
	
	@Autowired
	private JWTUtil jwtutil;
	
	@Autowired
	private UserRepository userrepository;
	
	@Autowired
	private AuthenticationManager authenticationmanager;
	
	//@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping("/save")
	public ResponseEntity<String> saveUser(@RequestBody User user) {
		Integer id =iuserservice.SaveUser(user);
		String body ="user '"+id+"'saved";
		//return new ResponseEntity<String>(body,HttpStatus.OK);
		return ResponseEntity.ok(body);
		
}
	
	@PostMapping("/savewhole")
	public ResponseEntity<String> saveUserWhole(@RequestBody User user) {
		Integer id =iuserservice.SaveUser(user);
		String body ="user '"+id+"'saved";
		//return new ResponseEntity<String>(body,HttpStatus.OK);
		return ResponseEntity.ok(body);
		
}
	
	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest request){
		
		authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		
		String token=jwtutil.generateToken(request.getUsername());
		return ResponseEntity.ok(new UserResponse(token,"success"));
		
	}
	
	@PostMapping("/welcome")
	public ResponseEntity<String> accessdata(Principal p ){
		return ResponseEntity.ok("hello user" + p.getName());
	}
	

	@GetMapping("/all")
	public List<User> searchAllUsers() {
		//System.out.println("name is "+name);
		return userrepository.findAll();
		//return userservice.findbyfirstnameservice(name);
		
	}
	
	@RequestMapping(value="/userupdate/{id}", method =RequestMethod.PUT, consumes ="application/json")
	@CrossOrigin(allowedHeaders = "*")
	public ResponseEntity<Object> updateUser(@RequestBody User user,@PathVariable int id) {

		User useroptional = userrepository.findById(id);

		//if (!((Object) useroptional)id())
			//return ResponseEntity.notFound().build();
       //Student s1= studentOptional.get(0);
		useroptional.setId(user.getId());
		//useroptional.setId(user.getId());
		useroptional.setUsername(user.getUsername());
		useroptional.setName(user.getName());
		useroptional.setPassword(user.getPassword());
		useroptional.setAddress(user.getAddress());
		//useroptional.setFirstName(user.ge());
		//useroptional.setLastName(user.getLastName());
		useroptional.setPincode(user.getPincode());
		useroptional.setCity(user.getCity());
		useroptional.setEmail(user.getEmail());
		
		System.out.println("printingh studnet id "+id);
		userrepository.save(useroptional);

		//return ResponseEntity.noContent().build();
		return new  ResponseEntity<>("Data is updated successfully", HttpStatus.OK);
			}
	
	@DeleteMapping("/udelete/{id}")
	@CrossOrigin(allowedHeaders = "*")
	public ResponseEntity<Object> deletedbyid(@PathVariable int id) {
		userrepository.deleteById(id);
		 return new ResponseEntity<>("Data is deleted successsfully", HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@CrossOrigin(allowedHeaders = "*")
	public Optional<User> searchbyid(@PathVariable(value = "id") Integer id) {
	//	System.out.println("name is "+name);
		return userrepository.findById(id);
		//return userservice.findbyfirstnameservice(name);
		
	}
	
	
	 @GetMapping(value = "/orderbydoj")
	 @CrossOrigin(allowedHeaders = "*")
	    public List<User> getUsersbydoj() {

	        return iuserservice.findAllOrderByDojAsc();
	    }
	 
	 @GetMapping(value = "/orderbydob")
	 @CrossOrigin(allowedHeaders = "*")
	    public List<User> getUsersbydob() {

	        return iuserservice.findAllorderByJoindateAsc();
	    }
	 
	 @GetMapping("/pincode/{pinocde}")
		@CrossOrigin(allowedHeaders = "*")
		public List<User> searchbysurnamename(@PathVariable(value = "pinocde") String pincode) {
			return userrepository.findByPincode(pincode);
			
			
		}
	 
		/*
		 * @GetMapping("/firstname/{nameone}")
		 * 
		 * @CrossOrigin(allowedHeaders = "*") public List<User>
		 * searchbyname(@PathVariable(value = "nameone") String name) {
		 * System.out.println("name is "+name); return userrepository.findByName(name);
		 * //return userservice.findbyfirstnameservice(name);
		 * 
		 * }
		 */
	 
		/*
		 * @GetMapping("/city/{citye}")
		 * 
		 * @CrossOrigin(allowedHeaders = "*") public List<User>
		 * searchbysurname(@PathVariable(value = "citye") String surnamename) { return
		 * userrepository.findByCity(citye);
		 * 
		 * }
		 */
}
