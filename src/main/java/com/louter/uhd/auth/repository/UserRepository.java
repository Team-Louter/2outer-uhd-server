package com.louter.uhd.auth.repository;

import com.louter.uhd.auth.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 유저 조회 함수
    Optional<User> findByUserId(String userId);
    Optional<User> findByUserEmail(String userEmail);

    // 유저 존재 확인 함수
    boolean existsByUserId(String userId);
    boolean existsByUserEmail(String userEmail);

    // 비번 수정
    @Transactional
    @Modifying
    @Query("UPDATE User user SET user.userPassword = :newPassword WHERE user.userId = :userId")
    void updateUserPassword(@Param("userId") String id, @Param("newPassword") String newPassword);
}
