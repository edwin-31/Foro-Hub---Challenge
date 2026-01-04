package com.foro.hub.config.jwt;

import com.foro.hub.persistence.entity.UserEntity;
import com.foro.hub.persistence.repository.IUserRepository;
import com.foro.hub.util.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetails implements UserDetailsService {

	private final IUserRepository userRepository;

	@Autowired
	public CustomUserDetails(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

		return User.withUsername(userEntity.getEmail())
			.password(userEntity.getPassword())
			.roles("USER")
			.build();
	}
}
