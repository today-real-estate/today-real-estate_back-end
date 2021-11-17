package com.ssafy.realestate.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDto {
    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "이메일 형식을 확인하세요.")
    private String userEmail;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Length(min = 8, max = 20, message = "8~20자리의 비밀번호를 입력하세요")
    private String password;
}
