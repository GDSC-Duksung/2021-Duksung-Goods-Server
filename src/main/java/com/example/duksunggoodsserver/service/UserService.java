package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.exception.CustomException;
import com.example.duksunggoodsserver.model.entity.User;
import com.example.duksunggoodsserver.repository.UserRepository;
import com.example.duksunggoodsserver.security.JwtTokenProvider;
import com.example.duksunggoodsserver.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final RedisUtil redisUtil;
    @Value("${mail.verify-url}")
    private String verifyUrl;
    @Value("${mail.valid-time}")
    private Long validTime;

    public String signIn(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return jwtTokenProvider.createToken(email);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid email/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String signUp(User user) throws Exception {
        if (!userRepository.existsByEmail(user.getEmail()) && !userRepository.existsByNickname(user.getNickname())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setCreatedAt(LocalDateTime.now());
            user.setCreatedBy(user.getNickname());
            user.setEnabled(false);
            userRepository.save(user);

            sendVerificationMail(user.getEmail());

            return jwtTokenProvider.createToken(user.getEmail());
        } else {
            throw new CustomException("Email or Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public Optional<User> getUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND)));
        return user;
    }

    public Optional<User> getCurrentUser(HttpServletRequest req) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(jwtTokenProvider.getEmail(jwtTokenProvider.resolveToken(req)))
                .orElseThrow(() -> new CustomException("Any user have logged in yet", HttpStatus.BAD_REQUEST)));
        return user;
    }

    public String refresh(String username) {
        return jwtTokenProvider.createToken(username);
    }

    public boolean sendVerificationMail(String email) {
        try{
            String code = createCode();
            redisUtil.setDataExpiration(code, email, validTime);
            MimeMessage message = emailSender.createMimeMessage();

            message.addRecipients(MimeMessage.RecipientType.TO, email); // 보낼 이메일 설정
            message.setSubject("[인증 메일]덕성 굿즈플랫폼"); // 이메일 제목
            message.setText(setContext(code, verifyUrl), "utf-8", "html"); // 내용 설정(Template Process)

            emailSender.send(message); // 이메일 전송
            return true;
        } catch(Exception e) {
            log.info(String.valueOf(e));
            throw new MailSendException("Failed to send email");
        }
    }

    private String setContext(String code, String verifyUrl) { // 타임리프 설정하는 코드
        Context context = new Context();
        context.setVariable("code", code); // Template에 전달할 데이터 설정
        context.setVariable("verify_url", verifyUrl);
        return templateEngine.process("mail", context); // mail.html
    }

    private String createCode() {
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 7; i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0:
                    code.append((char) (rnd.nextInt(26) + 97));
                    break;
                case 1:
                    code.append((char) (rnd.nextInt(26) + 65));
                    break;
                case 2:
                    code.append((rnd.nextInt(10)));
                    break;
            }
        }
        return code.toString();
    }

    public boolean verify(String code) {
        Optional<String> email = Optional.ofNullable(redisUtil.getData(code));
        if (email.isPresent()) {
            Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email.get())
                    .orElseThrow(() -> new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND)));
            user.get().setEnabled(true);
            userRepository.save(user.get());
            return true;
        } else {
            return false;
        }
    }
}
