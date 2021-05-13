package in.nareshit.raghu.config;

import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import in.nareshit.raghu.filter.SecurityFilter;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	@Autowired
	private UserDetailsService userdetailservice;
	
	@Autowired
	private BCryptPasswordEncoder PasswordEncoder;
	
	@Autowired
	private InvalidUserAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	SecurityFilter securityFilter;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth
		.userDetailsService(userdetailservice)
		.passwordEncoder(PasswordEncoder);
		super.configure(auth);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/user/save","/user/login","/user/all").permitAll()
		//.antMatchers("/user/save").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(authenticationEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
		;
	}

}
