package in.nareshit.raghu.service;

import java.util.List;
import java.util.Optional;

import in.nareshit.raghu.model.User;

public interface IUserservice {
	
	Integer SaveUser(User user);
	
	Optional<User> findByUsername(String username);

	List<User> findAllOrderByDojAsc();
	
	 List<User>  findAllorderByJoindateAsc();
	
	/*
	 * public List<User> findAllOrderByDojAsc() { return
	 * repo.findAllorderByJoindateAsc(); }
	 */

}
