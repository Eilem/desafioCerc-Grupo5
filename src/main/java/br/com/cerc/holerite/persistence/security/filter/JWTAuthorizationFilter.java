package br.com.cerc.holerite.persistence.security.filter;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;

import br.com.cerc.holerite.persistence.security.service.CustomUserDetailsService;
import br.com.cerc.holerite.persistence.security.util.SecurityContextUtil;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	private final CustomUserDetailsService customUserDetailsService;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService) {
		super(authenticationManager);
		this.customUserDetailsService = customUserDetailsService;
	}
	
	
	private SignedJWT decryptValidating(String encryptedToken) {
		try {
			String signedToken = decryptToken(encryptedToken);
			validateTokenSignature(signedToken);
			return SignedJWT.parse(signedToken);
		} catch (ParseException | JOSEException e) {
			throw new RuntimeException(e);
		}
	
	}
	
	private String decryptToken(String encryptedToken) throws ParseException, JOSEException {
		JWEObject jweObject = JWEObject.parse(encryptedToken);
		
		DirectDecrypter directEncrypter = new DirectDecrypter(Constants.SECRET_KEY.getBytes());
		
		jweObject.decrypt(directEncrypter);
		
		return jweObject.getPayload().toSignedJWT().serialize();
	}
	
	private void validateTokenSignature(String signedToken) throws ParseException, JOSEException {
		SignedJWT signedJWT = SignedJWT.parse(signedToken);
		
		RSAKey publicKey = RSAKey.parse(signedJWT.getHeader().getJWK().toJSONObject());
		
		if(!signedJWT.verify(new RSASSAVerifier(publicKey))) {
			throw new AccessDeniedException("Invalid token signature!");
		}
	}

}


