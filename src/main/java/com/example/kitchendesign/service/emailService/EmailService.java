package com.example.kitchendesign.service.emailService;

import com.example.kitchendesign.dto.emailSenderDTO.SimpleEmailDTO;
import com.example.kitchendesign.entity.User;

public interface EmailService {
    String sendSimpleEmail(User user);
}
