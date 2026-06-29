package com.merchantharmony.notification.sms;

import com.merchantharmony.notification.sms.dto.SmsRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class SmsNotificationController {

    private final SmsProvider smsProvider;

    @PostMapping("/sms")
    public void sendSms(@Valid @RequestBody SmsRequest request) {
        smsProvider.send(request.to(), request.message());
    }
}
