package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.authcofig.JWTutil;
import com.example.demo.service.FlightUserDetails;
import com.example.demo.ui.AuthenticationRequest;
import com.example.demo.ui.AuthenticationResponse;



@RestController
@RequestMapping("/api/v1.0/common/flight")
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;

	private final JWTutil jwtTokenUtil;

	private final FlightUserDetails userDetailsService;
	


	@Autowired
	public AuthenticationController(AuthenticationManager authenticationManager, JWTutil jwtTokenUtil,
			FlightUserDetails userDetailsService) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userDetailsService = userDetailsService;
		
	}

	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authrequest) throws Exception {
		try {

			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authrequest.getUserEmail(), authrequest.getPassword()));

		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authrequest.getUserEmail());
		AuthenticationResponse authresponse = new AuthenticationResponse(jwtTokenUtil.generateToken(userDetails));

		return ResponseEntity.ok(authresponse);
	}
	
	

}

