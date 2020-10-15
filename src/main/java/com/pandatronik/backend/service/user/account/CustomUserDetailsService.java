package com.pandatronik.backend.service.user.account;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.repositories.user.account.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	private final UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//		UserEntity user = userService.findByUserName(username);
//		if (null == user) {
//			throw new UsernameNotFoundException("Username " + username + " not found");
//		}

		return userService.findByUserName(username);
	}

	@Transactional
	public UserEntity loadUserById(Long id) {

		UserEntity user = userRepository.getById(id);

		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		return user;
	}

}