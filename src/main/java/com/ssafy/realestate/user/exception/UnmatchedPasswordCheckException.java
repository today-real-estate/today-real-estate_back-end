package com.ssafy.realestate.user.exception;

public class UnmatchedPasswordCheckException extends IllegalArgumentException {
    public UnmatchedPasswordCheckException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}