package com.example.kitchendesign.service.emailService;

import com.example.kitchendesign.dto.emailSenderDTO.SimpleEmailDTO;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailService {
    String sendSimpleEmail(SimpleEmailDTO simpleEmailDTO);
}
