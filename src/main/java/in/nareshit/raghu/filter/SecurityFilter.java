package in.nareshit.raghu.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import in.nareshit.raghu.util.JWTUtil;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	
	@Autowired
	private JWTUtil jwtutil;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String token = request.getHeader("Authorization");
		
		if(token !=null) {
		String username	=jwtutil.getUsername(token);
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails usr=userDetailsService.loadUserByUsername(username);
			
			boolean isvalid=jwtutil.validatetoken(token, usr.getUsername());
			
			if(isvalid) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, usr.getPassword(),usr.getAuthorities());
			    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			    
			    SecurityContextHolder.getContext().setAuthentication(authToken);
			
			}
			
		}
		}
		filterChain.doFilter(request, response);
		
	}

}
