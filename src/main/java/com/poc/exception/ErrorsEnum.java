package com.poc.exception;

public enum ErrorsEnum {

    /**
     * ERR_MCS_POC
     */
    ERR_MCS_ORDER_NOT_FOUND("Error occurred - no Order found with this id"),
    ERR_MCS_PAYMENT_NOT_FOUND("Error occurred - no Payment found with this id"),
    ERR_MCS_ORDER_IS_PAID("Error occurred - Order already paid");

    private final String errorMessage;

    private ErrorsEnum(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return " errorMessage : " + errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
