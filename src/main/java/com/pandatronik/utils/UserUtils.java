package com.pandatronik.utils;

import com.pandatronik.backend.persistence.domain.BasicAccountPayload;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.web.controllers.users.ForgotMyPasswordResource;
import com.pandatronik.web.controllers.users.SignupResource;

import javax.servlet.http.HttpServletRequest;

public class UserUtils {

	private UserUtils() {
		throw new AssertionError("Non instantiable");
	}

	/**
	 * Creates a user with basic attributes set.
	 * 
	 * @param username
	 *            The username.
	 * @param email
	 *            The email.
	 * @return A User entity
	 */
	public static UserEntity createBasicUser(String username, String email) {

		UserEntity user = new UserEntity();
		user.setUsername(username);
		user.setPassword("secret123");
		user.setConfirmPassword("secret123");
		user.setEmail(email);
		user.setFirstName("firstName");
		user.setLastName("lastName");
		user.setEnabled(true);

		return user;
	}

	public static UserEntity createBasicUserEntity(UserEntity userEntity) {

		UserEntity user = new UserEntity();
		user.setUsername(userEntity.getUsername());
		user.setPassword(userEntity.getPassword());
		user.setEmail(userEntity.getEmail());
		user.setFirstName(userEntity.getFirstName());
		user.setLastName(userEntity.getLastName());
		user.setEnabled(true);
		return user;
	}

	/**
	 * Builds and returns the URL to reset the user password.
	 * 
	 * @param request
	 *            The Http Servlet Request
	 * @param userId
	 *            The user id
	 * @param token
	 *            The token
	 * @return the URL to reset the user password.
	 */
	public static String createPasswordResetUrl(HttpServletRequest request, long userId, String token) {
		String passwordResetUrl = request.getScheme() + "://localhost:4200"
				+ request.getContextPath() + ForgotMyPasswordResource.CHANGE_PASSWORD_PATH + "?id=" + userId
				+ "&token=" + token;

		return passwordResetUrl;
	}

	public static String createActivateUserUrl(HttpServletRequest request, long userId, String token) {
		String passwordResetUrl = request.getScheme() + "://localhost:4200"
				+ request.getContextPath() + SignupResource.ACTIVATE_ACCOUNT_PATH + "?id=" + userId
				+ "&token=" + token;

		return passwordResetUrl;
	}

	public static <T extends BasicAccountPayload> UserEntity fromWebUserToDomainUser(T frontendPayload) {
		UserEntity user = new UserEntity();
		user.setUsername(frontendPayload.getUsername());
		user.setPassword(frontendPayload.getPassword());
		user.setFirstName(frontendPayload.getFirstName());
		user.setLastName(frontendPayload.getLastName());
		user.setEmail(frontendPayload.getEmail());
		user.setEnabled(true);

		return user;
	}
}