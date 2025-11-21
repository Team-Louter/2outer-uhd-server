package com.louter.uhd.auth.usecase;

import com.louter.uhd.auth.domain.User;
import com.louter.uhd.auth.domain.VerificationPurpose;
import com.louter.uhd.auth.dto.request.SendVerificationEmailRequest;
import com.louter.uhd.auth.dto.request.VerifyCodeRequest;
import com.louter.uhd.auth.exception.UserNotFoundException;
import com.louter.uhd.auth.exception.WrongVerifiedCodeException;
import com.louter.uhd.auth.repository.UserRepository;
import com.louter.uhd.common.usecase.EmailSenderUseCase;
import lombok.Getter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class AuthEmailUseCase extends EmailSenderUseCase {
    // 이메일-인증코드 저장용
    private final Map<String, Map<VerificationPurpose, CodeInfo>> codeMap = new HashMap<>();
    // 예외 호출
    private final AuthValidationUseCase authValidationUseCase;
    // 디비 이용
    private final UserRepository userRepository;

    // 생성자 주입
    public AuthEmailUseCase(JavaMailSender mailSender,
                        AuthValidationUseCase authValidationUseCase,
                        UserRepository userRepository) {
        super(mailSender); // 부모 생성자 호출
        this.authValidationUseCase = authValidationUseCase;
        this.userRepository = userRepository;
    }

    // 인증 코드 생성
    private String generateCode() {
        return String.format("%06d", new Random().nextInt(1000000));
    }

    // 인증 코드 전송
    public User sendVerificationEmail(SendVerificationEmailRequest emailRequest) {
        authValidationUseCase.checkUserEmail(emailRequest.getUserEmail());

        String code = generateCode();
        // 이메일 - (목적 - 코드) 저장
        codeMap.computeIfAbsent(emailRequest.getUserEmail(), k -> new HashMap<>())
                .put(emailRequest.getPurpose(), new CodeInfo(code));

        String subject = "[이메일 인증 코드]";
        String text = "ReBloom 인증코드: " + code;

        sendEmail(emailRequest.getUserEmail(), subject, text);

        return userRepository.findByUserEmail(emailRequest.getUserEmail())
                .orElseThrow(() -> new UserNotFoundException("유저가 조회되지 않음"));
    }

    // 인증 코드 검증
    public User verifyCode(VerifyCodeRequest verifyCodeRequest) {
        // 보낸 인증 코드 저장
        CodeInfo userCode = codeMap.get(verifyCodeRequest.getUserEmail()).get(verifyCodeRequest.getPurpose());

        // 정보가 존재하는지 확인
        if (userCode == null) {
            throw new WrongVerifiedCodeException("존재하지 않는 정보");
        }

        long expiration = EXPIRATION_MINUTES.get(verifyCodeRequest.getPurpose());

        // 코드 만료 확인
        if (Duration.between(userCode.getCreatedAt(), LocalDateTime.now()).toMinutes() >= expiration) {
            // 코드 지움
            codeMap.remove(verifyCodeRequest.getUserEmail());
            throw new WrongVerifiedCodeException("만료된 인증 코드");
        }

        // 코드 일치 확인
        if (!verifyCodeRequest.getCode().equals(userCode.getCode())) {
            System.out.println(verifyCodeRequest.getCode());
            System.out.println(userCode.getCode());
            throw new WrongVerifiedCodeException("잘못된 인증 코드");
        }

        return userRepository.findByUserEmail(verifyCodeRequest.getUserEmail())
                .orElseThrow(() -> new UserNotFoundException("유저가 조회 되지 않음"));
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
