package service;

import entities.Utilisateur;
import utils.DataSource;

import java.sql.*;
import java.time.LocalDate;

public class UtilisateurService {

    private final Connection conn;
    private PreparedStatement pst;
    private Statement ste;
    public UtilisateurService() {
        conn = DataSource.getInstance().getCnx();
    }
    public Utilisateur readById(int id) {
        String query = "SELECT * FROM utilisateur WHERE id=?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String nomUtilisateur = rs.getString("nomUtilisateur");
                    String prenomUtilisateur = rs.getString("prenomUtilisateur");
                    char sexe = rs.getString("sexe").charAt(0);
                    LocalDate dateDeNaissance = rs.getObject("dateDeNaissance", LocalDate.class);
                    String adresseEmail = rs.getString("adresseEmail");
                    String motDePasse = rs.getString("motDePasse");
                    String adressePostale = rs.getString("adressePostale");
                    String numeroTelephone = rs.getString("numeroTelephone");
                    String numeroCin = rs.getString("numeroCin");
                    String pays = rs.getString("pays");
                    int nbrProduitAchat = rs.getInt("nbrProduitAchat");
                    int nbrProduitVendu = rs.getInt("nbrProduitVendu");
                    int nbrProduit = rs.getInt("nbrProduit");
                    int nbrPoint = rs.getInt("nbrPoint");
                    String languePreferree = rs.getString("languePreferree");
                    float evaluationUtilisateur = rs.getFloat("evaluationUtilisateur");
                    boolean statutVerificationCompte = rs.getBoolean("statutVerificationCompte");
                    String avatar = rs.getString("avatar");
                    LocalDate dateInscription = rs.getObject("dateInscription", LocalDate.class);
                    boolean role = rs.getBoolean("role");

                    return new Utilisateur(id, nomUtilisateur, prenomUtilisateur, sexe, dateDeNaissance,
                            adresseEmail, motDePasse, adressePostale, numeroTelephone, numeroCin, pays,
                            nbrProduitAchat, nbrProduitVendu, nbrProduit, nbrPoint, languePreferree,
                            evaluationUtilisateur, statutVerificationCompte, avatar, dateInscription, role);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading Utilisateur by ID", e);
        }
        return null;
    }
}
