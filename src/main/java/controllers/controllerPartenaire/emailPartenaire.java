package controllers.controllerPartenaire;

import Entity.entitiesPartenaire.Partenaire;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class emailPartenaire {
    public emailPartenaire() {
    }

    public static void sendEmail(String recipientEmail, String subject, String messageBody) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        String username = "batahapp@gmail.com";
        String password = "gpay ypxn mcnf uiod";

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(messageBody);

            Transport.send(message);

            System.out.println("Message envoyé avec succès à : " + recipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi du message : " + e.getMessage());
        }
    }

public static void main(String[] args) {
        // Exemple : Création d'un objet Partenaire avec une adresse e-mail
        Partenaire partenaire = new Partenaire();
        partenaire.setEmail("Nourmrabet02@gmail.com"); // Spécifiez l'adresse e-mail du destinataire ici

        // Envoi d'un e-mail à partir de l'e-mail de l'objet Partenaire
        emailPartenaire.sendEmail(partenaire.getEmail(), "Affectation", "Contenu de l'e-mail");
    }

}
