package com.ssafy.realestate.user.repository;



import com.ssafy.realestate.user.entity.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
}