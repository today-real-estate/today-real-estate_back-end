package com.ssafy.realestate.inquiry.exception;

public class NoInquiryUserIdException extends IllegalArgumentException {
    public NoInquiryUserIdException() {
        super("존재하는 사용자 id가 없습니다.");
    }
}
