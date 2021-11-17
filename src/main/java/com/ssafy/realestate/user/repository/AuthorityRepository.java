package com.ssafy.realestate.user.repository;


import com.ssafy.realestate.user.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}