package in.nareshit.raghu.service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.nareshit.raghu.model.User;
import in.nareshit.raghu.repo.UserRepository;
import in.nareshit.raghu.service.IUserservice;


@Service
public class UserServiceImpl  implements IUserservice,UserDetailsService{

	@Autowired
	private UserRepository repo;
	
	@Autowired
	BCryptPasswordEncoder pwEncoder;
	
	
	@Override
	public Integer SaveUser(User user) {
		// TODO Auto-generated method stub
		
		user.setPassword(pwEncoder.encode(user.getPassword()));
		return	repo.save(user).getId();
		
	}


	@Override
	public Optional<User> findByUsername(String username) {
		// TODO Auto-generated method stub
		return repo.findByUsername(username);
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> opt =findByUsername(username);
		
		if(opt.isEmpty()) {
			throw new UsernameNotFoundException("User not found");
		}
		User user=opt.get();
		
		
		return new org.springframework.security.core.userdetails.User(
				username,
				user.getPassword(),
				user.getRoles().stream()
				.map((role->new SimpleGrantedAuthority(role)))
				.collect(Collectors.toList())
				);
	}
	
	public List<User> findAllOrderByDojAsc() {
        return repo.findAllorderByJoindateAsc();
    }


	@Override
	public List<User> findAllorderByJoindateAsc() {
		// TODO Auto-generated method stub
		return repo.findAllorderByJoindateAsc();
	}
	
	
	

}
