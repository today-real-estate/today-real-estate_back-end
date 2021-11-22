package com.ssafy.realestate.user.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ssafy.realestate.common.BaseTimeEntity;
import com.ssafy.realestate.inquiry.enitity.Inquiry;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String userEmail;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @NotBlank(message = "닉네임을 입력하세요.")
    @Length(min = 2, max = 8, message = "2~10자리의 닉네임을 입력하세요.")
    private String userName;

    @NotBlank(message = "닉네임을 입력하세요.")
    @Length(min = 2, max = 8, message = "2~10자리의 닉네임을 입력하세요.")
    private String nickname;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonManagedReference(value = "user-userAuthority")
    private List<UserAuthority> authorities;

    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonManagedReference(value = "user-inquiry")
    private List<Inquiry> inquiries;

}
