package com.ssafy.realestate.user.exception;

public class NoUserFoundException extends IllegalArgumentException {
    public NoUserFoundException() {
        super("ID나 PW가 존재하지 않습니다.");
    }
}