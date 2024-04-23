package com.example.kitchendesign.service.emailService;

import com.example.kitchendesign.dto.emailSenderDTO.SimpleEmailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class DefaultEmailService implements EmailService {

    private JavaMailSender javaMailSender;

    @Value("spring.mail.username")
    private String sender;

    @Override
    public String sendSimpleEmail(SimpleEmailDTO simpleEmailDTO){

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setFrom(sender, "TheBestKitchenDesigner");
            mimeMessageHelper.setTo(simpleEmailDTO.getRecipient().getEmail());
            mimeMessageHelper.setSubject("Welcome to KitchenDesign!");

            String text = "<h2>Hello. You have registrated in KitchenDesign successfully!\n" +
                          "Your username: " + simpleEmailDTO.getRecipient().getUsername() + "/n" +
                          "Your password: " + simpleEmailDTO.getRecipient().getPassword() + "<h2>";
            simpleEmailDTO.setMessage(text);

            mimeMessageHelper.setText(simpleEmailDTO.getMessage(), true);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to send email. Error: " + e.getMessage());
        }

        javaMailSender.send(mimeMessage);

        return "Congratulate! Mail Sent Successfully!";
    }
}
