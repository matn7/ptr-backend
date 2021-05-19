package com.pandatronik.backend.service.user.account;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.repositories.user.account.UserRepository;
import com.pandatronik.security.LoginAttemptService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.pandatronik.utils.UserConstants.FOUND_USER_BY_USERNAME;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	private final UserRepository userRepository;
	private final LoginAttemptService loginAttemptService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username " + username + " no found"));

		// Validate for User
		validateLoginAttempt(user);
		user.setLastLoginDateDisplay(user.getLastLoginDate());
		user.setLastLoginDate(new Date());
		userRepository.save(user);
		LOGGER.info(FOUND_USER_BY_USERNAME + username);
		return user;

	}

	@Transactional
	public UserEntity loadUserById(Long id) {

		UserEntity user = userRepository.getById(id);

		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		return user;
	}

	private void validateLoginAttempt(UserEntity user) {
		if (user.isNotLocked()) {
			if (loginAttemptService.isBlocked(user.getUsername())) {
				user.setNotLocked(false);
			} else {
				user.setNotLocked(true);
			}
		} else {
			loginAttemptService.loginSucceeded(user.getUsername());
		}
	}

}