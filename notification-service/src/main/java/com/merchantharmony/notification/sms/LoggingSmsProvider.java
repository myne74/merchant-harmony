package com.merchantharmony.notification.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@ConditionalOnProperty(name = "sms.provider", havingValue = "logging", matchIfMissing = true)
@Slf4j
public class LoggingSmsProvider implements SmsProvider {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int FAILURE_THRESHOLD = 10;

    @Override
    public void send(String to, String message) {
        if (SECURE_RANDOM.nextInt(FAILURE_THRESHOLD) == 0) {
            log.warn("[SMS] Simulated delivery failure for to={}", to);
            throw new RuntimeException("Simulated SMS delivery failure (10% failure rate)");
        }
        log.info("[SMS] to={} | message={}", to, message);
    }
}
