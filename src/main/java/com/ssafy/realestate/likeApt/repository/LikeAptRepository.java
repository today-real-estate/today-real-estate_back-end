package com.ssafy.realestate.likeApt.repository;

import com.ssafy.realestate.likeApt.entity.LikeApt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LikeAptRepository extends JpaRepository<LikeApt, Long> {
    List<LikeApt> findByUserId(Long id);

    List<LikeApt> findByUserIdAndAptCode(Long userId, String aptCode);

    @Transactional
    @Modifying
    @Query(value = "delete from like_apt where user_id=:userId and apt_code=:aptCode", nativeQuery = true)
    void deleteByUserIdByAptCode(Long userId, String aptCode);
}