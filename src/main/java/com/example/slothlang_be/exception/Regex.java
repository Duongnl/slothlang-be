package com.example.slothlang_be.exception;

public final class Regex {

    private Regex() {}

    public static final String EMAIL = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    public static final String NAME = "^(?=(.*\\p{L}){2,})[\\p{L} ]{2,255}$";
}
