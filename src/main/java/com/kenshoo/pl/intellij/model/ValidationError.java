package com.kenshoo.pl.intellij.model;

public class ValidationError {

    public String getMsg() {
        return msg;
    }

    private final String msg;

    public ValidationError(String msg) {
        this.msg = msg;
    }
}
