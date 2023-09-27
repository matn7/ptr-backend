package com.pandatronik.backend.service.user.account;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.repositories.user.account.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

		Optional<UserEntity> user = userRepository.findById(id);

		if (user.isEmpty()) {
			throw new UsernameNotFoundException("User not found");
		}

		return user.get();
	}

}