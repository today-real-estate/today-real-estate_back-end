package com.ssafy.realestate.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UserAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user-userAuthority")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    @JsonBackReference(value = "auth-userAuthority")
    private Authority auth;
}
