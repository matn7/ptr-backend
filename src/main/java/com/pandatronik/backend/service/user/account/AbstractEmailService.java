package com.pandatronik.backend.service.user.account;

import com.pandatronik.backend.persistence.domain.FeedbackPojo;
import org.springframework.mail.SimpleMailMessage;

public abstract class AbstractEmailService implements EmailService {

    protected SimpleMailMessage prepareSimpleMailMessageFromFeedbackPojo(FeedbackPojo feedback) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(feedback.getEmail());
        message.setFrom(feedback.getEmail());
        message.setSubject("[pandatronik]: Feedback received from " + feedback.getFirstName() + " " + feedback
                .getLastName() + "!");
        message.setText(feedback.getFeedback());
        return message;
    }

    @Override
    public void sendFeedbackEmail(FeedbackPojo feedbackPojo) {
        sendGenericEmailMessage(prepareSimpleMailMessageFromFeedbackPojo(feedbackPojo));
    }
}