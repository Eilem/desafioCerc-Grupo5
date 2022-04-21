package br.com.cerc.holerite.persistence.security.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import br.com.cerc.holerite.persistence.model.ApplicationUser;

@Service
public class SecurityContextUtil {
	private SecurityContextUtil() {
		
	}
	
	public static void setSecurityContext(SignedJWT signedJWT) {
		try {
			JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
			String username = claims.getSubject();
			if(username == null)
				throw new JOSEException("Username missing from JWT");
			
			List<String> authorities = claims.getStringListClaim("authorities");
			ApplicationUser applicationUser = new ApplicationUser.Builder()
					.id(claims.getLongClaim("userId"))
					.username(username)
					.role(String.join( ",", authorities))
					.build();
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(applicationUser, null, createAuthorities(authorities));
			auth.setDetails(signedJWT.serialize());
			SecurityContextHolder.getContext().setAuthentication(auth);
			
		} catch(Exception e) {
			e.printStackTrace();
			SecurityContextHolder.clearContext();
		}
	}
	
	private static List<SimpleGrantedAuthority> createAuthorities(List<String> authorities) {
		return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}
	
}
