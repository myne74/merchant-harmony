package com.merchantharmony.notification.sms;

public interface SmsProvider {

    void send(String to, String message);
}
