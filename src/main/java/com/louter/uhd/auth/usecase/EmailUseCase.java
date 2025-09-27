package com.louter.uhd.auth.usecase;

import com.louter.uhd.auth.domain.User;
import com.louter.uhd.auth.domain.VerificationPurpose;
import com.louter.uhd.auth.dto.request.SendVerificationEmailRequest;
import com.louter.uhd.auth.exception.UserNotFoundException;
import com.louter.uhd.auth.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailUseCase {
    // 이메일 전송용
    private final JavaMailSender mailSender;
    // 이메일-인증코드 저장용
    private final Map<String, Map<VerificationPurpose, CodeInfo>> codeMap = new HashMap<>();
    // 예외 호출
    private final ValidationUseCase validationUseCase;
    private final UserRepository userRepository;

    // 인증 코드 생성
    private String generateCode() {
        return String.format("%06d", new Random().nextInt(1000000));
    }

    // 인증 코드 전송
    public Map<User, String> sendVerificationEmail(SendVerificationEmailRequest emailRequest) {
        validationUseCase.checkUserEmail(emailRequest.getUserEmail());

        String code = generateCode();
        // 이메일 - (목적 - 코드) 저장
        codeMap.computeIfAbsent(emailRequest.getUserEmail(), k -> new HashMap<>())
                .put(emailRequest.getPurpose(), new CodeInfo(code));

        SimpleMailMessage message = new SimpleMailMessage();

        // purpose에 따라 메세지를 다르게 전송 가능하도록 구성
        message.setTo(emailRequest.getUserEmail());
        message.setSubject("[이메일 인증 코드]");
        message.setText("Uhd 인증코드: " + code);

        mailSender.send(message);

        Optional<User> optionalUser  = userRepository.findByUserEmail(emailRequest.getUserEmail());
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("유저가 조회되지 않음");
        }

        User user = optionalUser.get();

        return Map.of(user, code);
    }

    // 만료시간 저장
    private static final Map<VerificationPurpose, Long> EXPIRATION_MINUTES = Map.of(
            VerificationPurpose.SIGN_UP, 10L,
            VerificationPurpose.UPDATE_INFO, 5L
    );

    @Getter
    private static class CodeInfo {
        private final String code;
        private final LocalDateTime createdAt;

        public CodeInfo(String code) {
            this.code = code;
            this.createdAt = LocalDateTime.now();
        }
    }
}
