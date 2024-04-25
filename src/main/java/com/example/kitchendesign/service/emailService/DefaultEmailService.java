package com.example.kitchendesign.service.emailService;

import com.example.kitchendesign.dto.emailSenderDTO.SimpleEmailDTO;
import com.example.kitchendesign.entity.User;
import com.example.kitchendesign.mapper.GeneralMapper;
import com.example.kitchendesign.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class DefaultEmailService implements EmailService {

    private final JavaMailSender javaMailSender;
    private final GeneralMapper generalMapper;
    private final UserService userService;

    @Value("spring.mail.username")
    private String sender;


    @Override
    public String sendSimpleEmail(User user) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        SimpleEmailDTO simpleEmailDTO = generalMapper.UserToSimpleEmailDTO(user);

        try {
            mimeMessageHelper.setFrom(sender, "TheBestKitchenDesigner");
            mimeMessageHelper.setTo(simpleEmailDTO.getUser().getEmail());

            simpleEmailDTO.setSubject("Welcome to KitchenDesign!");
            mimeMessageHelper.setSubject(simpleEmailDTO.getSubject());

            String text = "<h2>Hello. You have registrated in KitchenDesign successfully!\n" +
                          "Your username: " + simpleEmailDTO.getUser().getUsername() + "/n" +
                          "Your password: " + simpleEmailDTO.getUser().getPassword() + "<h2>";
            simpleEmailDTO.setMessage(text);

            mimeMessageHelper.setText(simpleEmailDTO.getMessage(), true);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to send email. Error: " + e.getMessage());
        }

        javaMailSender.send(mimeMessage);

        return "Congratulate! Mail Sent Successfully!";
    }
}
