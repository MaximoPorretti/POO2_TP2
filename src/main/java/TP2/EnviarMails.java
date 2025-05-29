package TP2;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class EnviarMails implements EmailsService {

    private final String username = "60248342312b89";
    private final String password = "e627a0728766da";

    public String enviarEmail(String destinatario, String asunto, String mensaje) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("concurso@unrn.edu.ar"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(mensaje);

            Transport.send(message);
            System.out.println("Email enviado a " + destinatario);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error email: " + e.getMessage());
        }
        return destinatario;
    }
}
