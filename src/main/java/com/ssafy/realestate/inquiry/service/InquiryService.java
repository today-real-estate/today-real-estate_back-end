package com.ssafy.realestate.inquiry.service;

import com.ssafy.realestate.inquiry.dto.*;
import com.ssafy.realestate.inquiry.enitity.Inquiry;
import com.ssafy.realestate.inquiry.enitity.InquiryAnswer;
import com.ssafy.realestate.inquiry.exception.NoInquiryIdException;
import com.ssafy.realestate.inquiry.repository.InquiryAnswerRepository;
import com.ssafy.realestate.inquiry.repository.InquiryRepository;
import com.ssafy.realestate.user.entity.UserEntity;
import com.ssafy.realestate.user.exception.NoUserException;
import com.ssafy.realestate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final InquiryAnswerRepository inquiryAnswerRepository;
    private final UserRepository userRepository;

    public List<InquiryResponseDto> findAll() {
        List<Inquiry> inquiries = inquiryRepository.findAll();
        return inquiries.stream().map(InquiryResponseDto::from).collect(Collectors.toList());
    }

    public List<InquiryResponseDto> findByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(NoUserException::new);
        List<Inquiry> inquiries = inquiryRepository.findByUserId(user.getId());
        return inquiries.stream().map(InquiryResponseDto::from).collect(Collectors.toList());
    }

    public InquiryResponseDto findById(Long id) {
        Inquiry inquiry = inquiryRepository.findById(id).orElseThrow(NoInquiryIdException::new);
        return InquiryResponseDto.from(inquiry);
    }

    @Transactional
    public InquiryResponseDto save(InquiryRequestDto inquiryRequestDto) {
        UserEntity user = userRepository.findById(inquiryRequestDto.getUserId()).orElseThrow(NoUserException::new);
        return InquiryResponseDto.from(inquiryRepository.save(inquiryRequestDto.toSaveInquiryEntity(user)));

    }

    @Transactional
    public Long update(InquiryUpdateDto inquiryUpdateDto) {
        Inquiry originInquiry = inquiryRepository.findById(inquiryUpdateDto.getId())
                .orElseThrow(NoInquiryIdException::new);
        originInquiry.updateInquiry(inquiryUpdateDto);
        return originInquiry.getId();
    }

    @Transactional
    public Long answerUpdate(InquiryAnswerUpdateDto inquiryAnswerUpdateDto) {
        InquiryAnswer originAnswer = inquiryAnswerRepository.findById(inquiryAnswerUpdateDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("답글 ID가 존재하지 않습니다."));
        originAnswer.answerUpdate(inquiryAnswerUpdateDto.getAnswerContent());
        return originAnswer.getId();
    }

    @Transactional
    public void deleteById(Long id) {
        Inquiry inquiry = inquiryRepository.findById(id).orElseThrow(NoInquiryIdException::new);
        inquiryRepository.deleteById(inquiry.getId());
    }

    @Transactional
    public Long answerSave(InquiryAnswerDto inquiryAnswerDto) {
        Inquiry inquiry = inquiryRepository.findById(inquiryAnswerDto.getInquiryId()).orElseThrow(NoInquiryIdException::new);
        InquiryAnswer inquiryAnswer = inquiryAnswerRepository.save(inquiryAnswerDto.toSaveInquiryAnswerEntity(inquiry));
        isCompleteInquiryUpdate(inquiry.getId(), true);
        return inquiryAnswer.getId();
    }

    @Transactional
    public Long answerDelete(Long id, Long inquiryId) {
        InquiryAnswer inquiryAnswer = inquiryAnswerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("답글 ID가 존재하지 않습니다."));
        inquiryAnswerRepository.deleteById(id);
        Inquiry originInquiry = inquiryRepository.findById(inquiryId).orElseThrow(NoInquiryIdException::new);
        if (originInquiry.getInquiryAnswer().size() == 1) {
            isCompleteInquiryUpdate(inquiryId, false);
        }
        return inquiryAnswer.getId();
    }

    @Transactional
    public void isCompleteInquiryUpdate(Long id, Boolean isComplete) {
        Inquiry originInquiry = inquiryRepository.findById(id)
                .orElseThrow(NoInquiryIdException::new);
        originInquiry.isCompleteInquiryUpdate(isComplete);
    }

}
