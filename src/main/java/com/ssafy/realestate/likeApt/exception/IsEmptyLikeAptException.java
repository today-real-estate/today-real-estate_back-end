package com.ssafy.realestate.likeApt.exception;

public class IsEmptyLikeAptException extends IllegalArgumentException {
    public IsEmptyLikeAptException() {
        super("관심목록이 없습니다.");
    }
}

