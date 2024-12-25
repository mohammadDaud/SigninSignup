package com.mc.myapp.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mc.myapp.constant.RolesConstants;
import com.mc.myapp.entities.Roles;
import com.mc.myapp.entities.User;
import com.mc.myapp.exceptions.UserAlreadyExistException;
import com.mc.myapp.mapper.UserMapper;
import com.mc.myapp.models.UserDto;
import com.mc.myapp.repositories.RoleRepository;
import com.mc.myapp.repositories.UserRepository;

@Service
@Transactional
public class UserService {

	private final Logger log = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

	private final UserMapper mapper;

	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
			UserMapper mapper) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.mapper = mapper;
	}

	public User registerUser(UserDto userDTO, String password) {
		userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingUser -> {
			boolean exist = getExistUser(existingUser);
			if (exist) {
				throw new UserAlreadyExistException();
			}
		});
		User newUser = new User();
		String encryptedPassword = passwordEncoder.encode(password);
		newUser.setPassword(encryptedPassword);
		newUser.setFirstName(userDTO.getFirstName());
		newUser.setLastName(userDTO.getLastName());
		if (userDTO.getEmail() != null) {
			newUser.setEmail(userDTO.getEmail().toLowerCase());
		}
		newUser.setCreatedBy("SIGNINSIGNUP");

		Set<Roles> roles = new HashSet<>();
		List<Roles> findAll = roleRepository.findAll();
		for (Roles r : findAll) {
			if (RolesConstants.USER.equals(r.getName())) {
				roleRepository.findById(r.getId()).ifPresent(roles::add);
			}
		}
		newUser.setRoles(roles);
		save(newUser);
		log.debug("Created Information for User: {}", newUser);
		return newUser;
	}

	public List<UserDto> getUsers() {
		List<UserDto> list = new ArrayList<>();
		List<User> listUsers = userRepository.findAll();
		for (User u : listUsers) {
			UserDto userDto = mapper.userToUserDto(u);
			list.add(userDto);
			userDto = null;
		}
		return list;
	}

	public void updateToken(String email, Date tokenExpiryDate, String token) {
		User user = this.getUserByemail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		user.setTokenExpiryDate(tokenExpiryDate);
		user.setToken(token);
		save(user);
	}

	public void clearToken(String email) {
		User user = this.getUserByemail(email).orElseThrow(() -> new UsernameNotFoundException("User Not found!!!!!!"));
		user.setTokenExpiryDate(null);
		user.setToken(null);
		save(user);
	}

	public boolean hasToken(String email, String token) {
		User user = userRepository.findByEmailAndToken(email, token)
				.orElseThrow(() -> new UsernameNotFoundException("User Not found!!!!!"));
		return (user.getTokenExpiryDate() != null);
	}

	public Optional<User> getUserByemail(String email) {
		return userRepository.findOneByEmailIgnoreCase(email);
	}

	private void save(User user) {
		userRepository.save(user);
	}

	private boolean getExistUser(User existingUser) {
		if (existingUser == null) {
			return false;
		}
		boolean present = userRepository.findById(existingUser.getId()).isPresent();
		userRepository.flush();
		return present;
	}
}
