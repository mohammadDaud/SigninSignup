package com.mc.myapp.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mc.myapp.entities.Roles;
import com.mc.myapp.entities.User;
import com.mc.myapp.models.UserDto;

@Service
public class UserMapper {

	public List<UserDto> userToUserDTOs(List<User> users) {
		return users.stream().filter(Objects::nonNull).map(this::userToUserDto).collect(Collectors.toList());
	}

	public UserDto userToUserDto(User user) {
		return new UserDto(user);
	}

	public List<User> userDTOsToUsers(List<UserDto> userDTOs) {
		return userDTOs.stream().filter(Objects::nonNull).map(this::userDtoToUser).collect(Collectors.toList());
	}

	public User userDtoToUser(UserDto userDTO) {
		if (userDTO == null) {
			return null;
		} else {
			User user = new User();
			user.setId(userDTO.getId());
			user.setFirstName(userDTO.getFirstName());
			user.setLastName(userDTO.getLastName());
			user.setEmail(userDTO.getEmail());
			Set<Roles> authorities = this.rolesFromStrings(userDTO.getRoles());
			user.setRoles(authorities);
			return user;
		}
	}

	private Set<Roles> rolesFromStrings(Set<String> rolesAsString) {
		Set<Roles> roles = new HashSet<>();
		if (rolesAsString != null) {
			roles = rolesAsString.stream().map(string -> {
				Roles auth = new Roles();
				auth.setName(string);
				return auth;
			}).collect(Collectors.toSet());
		}
		return roles;
	}
}
