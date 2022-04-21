package br.com.cerc.holerite.persistence.security.filter;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import br.com.cerc.holerite.persistence.model.ApplicationUser;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			ApplicationUser applicationUser = new ObjectMapper().readValue(request.getInputStream(), ApplicationUser.class);
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(applicationUser.getUsername(), applicationUser.getPassword());
			usernamePasswordAuthenticationToken.setDetails(applicationUser);
			return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		SignedJWT signedJWT = createSignedJWT(authResult);
		
		try {
			String encryptedToken = encryptToken(signedJWT);
			
			response.addHeader("Access-Control-Expose-Headers", "XSRF-TOKEN, " + Constants.HEADER_STRING);
			response.addHeader(Constants.HEADER_STRING, Constants.TOKEN_PREFIX + encryptedToken);
		} catch (JOSEException e) {
			e.printStackTrace();
		}
		
	}
	
	private SignedJWT createSignedJWT(Authentication auth) {
		ApplicationUser applicationUser = (ApplicationUser) auth.getPrincipal();
		
		JWTClaimsSet jwtClaimSet = createClaimSet(auth, applicationUser);
		
		KeyPair rsaKeys = generateKeyPair();
		
		JWK jwk = new RSAKey.Builder((RSAPublicKey) rsaKeys.getPublic()).keyID(UUID.randomUUID().toString()).build();
		
		SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256)
				.jwk(jwk)
				.type(JOSEObjectType.JWT)
				.build(), jwtClaimSet);
		
		RSASSASigner signer = new RSASSASigner(rsaKeys.getPrivate());
		
		try {
			signedJWT.sign(signer);
			System.out.println(String.format("Serialized token '%s'", signedJWT.serialize()));
		} catch (JOSEException e) {
			e.printStackTrace();
		}
		
		return signedJWT;
	}
	
	private JWTClaimsSet createClaimSet(Authentication auth, ApplicationUser applicationUser) {
		return new JWTClaimsSet.Builder()
				.subject(applicationUser.getUsername())
				.claim("authorities", auth.getAuthorities()
						.stream()
						.map(GrantedAuthority::getAuthority)
						.collect(Collectors.toList()))
				.claim("userId", applicationUser.getId())
				.issueTime(new Date())
				.expirationTime(new Date(System.currentTimeMillis() + Constants.expTime))
				.build();
	}
	
	private KeyPair generateKeyPair() {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			
			generator.initialize(2048);
			
			return generator.genKeyPair();
		
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	private String encryptToken(SignedJWT signedJWT) throws JOSEException {
		DirectEncrypter directEncrypter = new DirectEncrypter(Constants.SECRET_KEY.getBytes());
		
		JWEObject jweObject = new JWEObject(new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256)
				.contentType("JWT")
				.build(), new Payload(signedJWT));
		
		jweObject.encrypt(directEncrypter);
		
		return jweObject.serialize();
	}
}
