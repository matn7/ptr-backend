package com.pandatronik.backend.service.user.account;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService {

    private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

    @Override
    public void sendGenericEmailMessage(SimpleMailMessage message) {
    	Preconditions.checkNotNull(message, "message must not be null");
        LOG.info("Simulating an email service...");
        LOG.info(message.toString());
        LOG.info("Email sent.");
    }
}
