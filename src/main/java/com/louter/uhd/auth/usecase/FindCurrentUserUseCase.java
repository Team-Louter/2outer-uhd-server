package com.louter.uhd.auth.usecase;

import com.louter.uhd.auth.domain.User;
import com.louter.uhd.auth.exception.UserNotFoundException;
import com.louter.uhd.auth.jwt.JwtUtil;
import com.louter.uhd.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindCurrentUserUseCase {
    // 이메일 추출
    private final JwtUtil jwtUtil;
    // 디비 접근
    private final UserRepository userRepository;

    public User getCurrentUser() {
        String email = jwtUtil.getCurrentUserEmail();

        return userRepository.findByUserEmail(email)
                .orElseThrow(() -> new UserNotFoundException("유저 조회 실패"));
    }
}
