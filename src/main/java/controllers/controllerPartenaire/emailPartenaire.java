package controllers.controllerPartenaire;

import Entity.entitiesPartenaire.Partenaire;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.PasswordAuthentication;

import java.util.Properties;

public class emailPartenaire {

    public static void sendEmail(String recipientEmail, String subject, String messageBody) {
        // Paramètres de configuration du serveur SMTP de Gmail
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Informations d'identification du compte Gmail
        String username = "batahapp@gmail.com";
        String password = "gpay ypxn mcnf uiod"; // Convertir le mot de passe en tableau de caractères

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Création du message e-mail
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(messageBody);

            // Envoi du message
            Transport.send(message);

            System.out.println("Message envoyé avec succès à : " + recipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi du message : " + e.getMessage());
        }
    }

public static void main(String[] args) {
        // Exemple : Création d'un objet Partenaire avec une adresse e-mail
        Partenaire partenaire = new Partenaire();
        partenaire.setEmail("sofiennemrabet321@gmail.com"); // Spécifiez l'adresse e-mail du destinataire ici

        // Envoi d'un e-mail à partir de l'e-mail de l'objet Partenaire
        emailPartenaire.sendEmail(partenaire.getEmail(), "affectation", "Contenu de l'e-mail");
    }

}
