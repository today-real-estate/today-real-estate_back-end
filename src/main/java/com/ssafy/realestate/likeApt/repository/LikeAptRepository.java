package com.ssafy.realestate.likeApt.repository;

import com.ssafy.realestate.likeApt.entity.LikeApt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeAptRepository extends JpaRepository<LikeApt, Long> {
    List<LikeApt> findByUserId(Long id);
}