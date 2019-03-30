package com.pandatronik.backend.service.user.account;

import com.pandatronik.backend.persistence.domain.FeedbackPojo;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

	void sendFeedbackEmail(FeedbackPojo feedbackPojo);

	void sendGenericEmailMessage(SimpleMailMessage message);
}
