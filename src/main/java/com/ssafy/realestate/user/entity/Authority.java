package com.ssafy.realestate.user.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authority_name", length = 30)
    private String authorityName;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "authority_id", referencedColumnName = "id")
    @JsonManagedReference(value = "auth-userAuthority")
    private Set<UserAuthority> authorities;
}
