package in.nareshit.raghu.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByUsername(String username);
	
	User findById(int id);
	
	@Query(value = "select * from usertab  ORDER BY  joindate ASC ", nativeQuery = true)
    List<User>  findAllorderByJoindateAsc();
	
	
	@Query(value = "select * from user  ORDER BY  dob ASC ", nativeQuery = true)
    List<User>  findAllorderByDobAsc();

	
	List<User> findByPincode(String pincode);
	List<User> findByName(String name);
	//finList<User> findByfirstName(String firstname);
	//List<User> findBylastName(String surnamename);
	//List<User> findByCity(String city);
}
