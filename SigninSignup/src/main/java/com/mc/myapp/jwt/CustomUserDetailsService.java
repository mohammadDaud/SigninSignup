package com.mc.myapp.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mc.myapp.entities.User;
import com.mc.myapp.entities.UserPrincipal;
import com.mc.myapp.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByFirstNameOrEmail(username,username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with this email : " + username));
		return UserPrincipal.userToUserPrincipal(user);
	}

}
