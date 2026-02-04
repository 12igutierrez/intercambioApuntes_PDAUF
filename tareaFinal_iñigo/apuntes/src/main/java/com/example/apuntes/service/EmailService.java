package com.example.apuntes.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarMensajeContacto(
            String nombre,
            String email,
            String mensaje) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("bot999bot999bot000@gmail.com"); // email admin
        mail.setSubject("Nuevo mensaje de contacto");
        mail.setText(
                "Nombre: " + nombre + "\n" +
                        "Email: " + email + "\n\n" +
                        "Mensaje:\n" + mensaje
        );
        mailSender.send(mail);
    }
}
