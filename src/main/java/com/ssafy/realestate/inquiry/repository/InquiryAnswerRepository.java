package com.ssafy.realestate.inquiry.repository;

import com.ssafy.realestate.inquiry.enitity.InquiryAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryAnswerRepository extends JpaRepository<InquiryAnswer, Long> {
}