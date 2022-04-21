package br.com.cerc.holerite.persistence.security.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.cerc.holerite.persistence.model.ApplicationUser;
import br.com.cerc.holerite.persistence.repository.ApplicationUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	private final ApplicationUserRepository applicationUserRepository;
	
	public CustomUserDetailsService(ApplicationUserRepository applicationUserRepository) {
		this.applicationUserRepository = applicationUserRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
		
		if(applicationUser == null) {
			throw new UsernameNotFoundException(String.format("ApplicationUser user '%s' not found", username));
		}
		
		return new CustomUserDetails(applicationUser);
	}
	
	
	private class CustomUserDetails extends ApplicationUser implements UserDetails {
		
		private CustomUserDetails(ApplicationUser applicationUser) {
			super(applicationUser);
		}
		
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_" + this.getRole());
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}
		
	}
}
