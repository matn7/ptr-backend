package com.pandatronik.backend.service.user.account;

import com.google.common.base.Preconditions;
import com.pandatronik.backend.persistence.domain.PasswordResetToken;
import com.pandatronik.backend.persistence.domain.Plan;
import com.pandatronik.backend.persistence.domain.TokenEntity;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.UserRole;
import com.pandatronik.backend.persistence.repositories.user.account.PasswordResetTokenRepository;
import com.pandatronik.backend.persistence.repositories.user.account.PlanRepository;
import com.pandatronik.backend.persistence.repositories.user.account.RoleRepository;
import com.pandatronik.backend.persistence.repositories.user.account.TokenRepository;
import com.pandatronik.backend.persistence.repositories.user.account.UserRepository;
import com.pandatronik.enums.PlansEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static com.pandatronik.utils.Authority.USER_AUTHORITIES;

@Service
@Transactional(readOnly = true)
public class UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PlanRepository planRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	private TokenRepository tokenRepository;

	@Transactional
	public UserEntity createUser(UserEntity user, PlansEnum plansEnum, Set<UserRole> userRoles) {

		UserEntity localUser = userRepository.findByEmail(user.getEmail());

		if (localUser != null) {
			LOG.info("User with username {} and email {} already exist. Nothing will be done. ",
					user.getUsername(), user.getEmail());
		} else {

			String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(encryptedPassword);

			Plan plan = new Plan(plansEnum);
			// It makes sure the plans exist in the database
			if (!planRepository.existsById(plansEnum.getId())) {
				plan = planRepository.save(plan);
			}
			user.setPlan(plan);
			for (UserRole ur : userRoles) {
				roleRepository.save(ur.getRole());
			}
			user.getUserRoles().addAll(userRoles);

			user.setAuthorities(USER_AUTHORITIES);
			localUser = userRepository.save(user);
		}

		return localUser;

	}
	
	public UserEntity findByUserName(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username " + username + " no found"));
	}

	public UserEntity findByEmail(String email) {
		Preconditions.checkNotNull(email, "email must not be null");
		return userRepository.findByEmail(email);
	}
	
	public UserEntity save(UserEntity user) {
		Preconditions.checkNotNull(user, "user must not be null");
		return userRepository.save(user);
	}

	public Iterable<UserEntity> findAll() {
		return userRepository.findAll();
	}

	@Transactional
	public void deleteUser(String username, long userId) {
		UserEntity byUsername = findByUserName(username);
		if (byUsername != null) {
			userRepository.deleteById(userId);
		} else {
			LOG.error("User not found");
		}
	}

	@Transactional
    public void updateUserPassword(long userId, String password) {
		Preconditions.checkNotNull(userId, "userId must not be null");
		Preconditions.checkNotNull(password, "password must not be null");
		
    	password = bCryptPasswordEncoder.encode(password);
    	userRepository.updateUserPassword(userId, password);
    	LOG.debug("Password updated successfully for user id {} ", userId);
    	
    	Set<PasswordResetToken> resetToken = passwordResetTokenRepository.findAllByUserId(userId);
    	if (!resetToken.isEmpty()) {
    		passwordResetTokenRepository.deleteAll(resetToken);
    	}
    }

    @Transactional
	public void activateUser(long userId) {
		userRepository.activateUser(userId);
		LOG.debug("User id {} activated successfully", userId);

		Set<TokenEntity> activateToken = tokenRepository.findAllByUserId(userId);
		if (!activateToken.isEmpty()) {
			tokenRepository.deleteAll(activateToken);
		}
	}
	
	@Transactional
    public void updateUserEmail(long userId, String email) {
		Preconditions.checkNotNull(userId, "userId must not be null");
		Preconditions.checkNotNull(email, "email must not be null");
    	userRepository.updateUserEmail(userId, email);
    	LOG.debug("Email updated successfully for user id {} ", userId);	
    }

    public Optional<UserEntity> findById(Long userId) {
		return userRepository.findById(userId);
    }
}