package com.merchantharmony.common.exception;

public class MerchantHarmonyException extends RuntimeException {

    private final ErrorCode errorCode;

    public MerchantHarmonyException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
