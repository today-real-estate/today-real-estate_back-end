package com.ssafy.realestate.user.exception;

public class NoUserException extends IllegalArgumentException {
    public NoUserException() {
        super("존재하는 사용자가 없습니다.");
    }
}