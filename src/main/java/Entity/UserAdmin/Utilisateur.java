package Entity.UserAdmin;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;

public class Utilisateur {
    protected int idUtilisateur;
    protected String nomUtilisateur;
    protected String prenomUtilisateur;
    protected String mailUtilisateur;
    protected String motDePassUtilisateur ;
    protected Date dateDeNaissance ;
    protected char sexeUtilisateur;
    protected String cinUtilisateur;
    protected char roleUtilisateur;

    protected String NumUtilisateur;

    protected String pays;
    protected String avatar;

    public Utilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Utilisateur() {
    }

    public Utilisateur(String nomUtilisateur, String prenomUtilisateur, String mailUtilisateur, String motDePassUtilisateur, Date dateDeNaissance, char sexeUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
        this.prenomUtilisateur = prenomUtilisateur;
        this.mailUtilisateur = mailUtilisateur;
        this.motDePassUtilisateur = motDePassUtilisateur;
        this.dateDeNaissance = dateDeNaissance;
        this.sexeUtilisateur = sexeUtilisateur;
    }

    public Utilisateur(int idUtilisateur, String nomUtilisateur, String prenomUtilisateur, String mailUtilisateur, String motDePassUtilisateur, Date dateDeNaissance, char sexeUtilisateur, String cinUtilisateur, char roleUtilisateur, String numUtilisateur, String pays, String avatar) {
        this.idUtilisateur = idUtilisateur;
        this.nomUtilisateur = nomUtilisateur;
        this.prenomUtilisateur = prenomUtilisateur;
        this.mailUtilisateur = mailUtilisateur;
        this.motDePassUtilisateur = motDePassUtilisateur;
        this.dateDeNaissance = dateDeNaissance;
        this.sexeUtilisateur = sexeUtilisateur;
        this.cinUtilisateur = cinUtilisateur;
        this.roleUtilisateur = roleUtilisateur;
        NumUtilisateur = numUtilisateur;
        this.pays = pays;
        this.avatar = avatar;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getPrenomUtilisateur() {
        return prenomUtilisateur;
    }

    public void setPrenomUtilisateur(String prenomUtilisateur) {
        this.prenomUtilisateur = prenomUtilisateur;
    }

    public String getMailUtilisateur() {
        return mailUtilisateur;
    }

    public void setMailUtilisateur(String mailUtilisateur) {
        this.mailUtilisateur = mailUtilisateur;
    }

    public String getMotDePassUtilisateur() {
        return motDePassUtilisateur;
    }

    public void setMotDePassUtilisateur(String motDePassUtilisateur) {
        this.motDePassUtilisateur = motDePassUtilisateur;
    }

    public char getRoleUtilisateur() {
        return roleUtilisateur;
    }

    public void setRoleUtilisateur(char roleUtilisateur) {
        this.roleUtilisateur = roleUtilisateur;
    }

    public String getCinUtilisateur() {
        return cinUtilisateur;
    }

    public void setCinUtilisateur(String cinUtilisateur) {
        this.cinUtilisateur = cinUtilisateur;
    }

    public String getNumUtilisateur() {
        return NumUtilisateur;
    }

    public void setNumUtilisateur(String numUtilisateur) {
        NumUtilisateur = numUtilisateur;
    }

    public char getSexeUtilisateur() {
        return sexeUtilisateur;
    }

    public void setSexeUtilisateur(char sexeUtilisateur) {
        this.sexeUtilisateur = sexeUtilisateur;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }
    public  void saveJsonToBinFile(Membre membre) {

        Gson gson = new Gson();
        String json = gson.toJson(membre);
        String filePath="Session.bin";
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            // Convert JSON string to bytes
            byte[] jsonBytes = json.getBytes();

            // Write bytes to the file
            fos.write(jsonBytes);

            System.out.println("JSON data saved to file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving JSON data to file: " + e.getMessage());
        }
    }

    public  String loadJsonFromBinFile() {
        String filePath ="Session.bin";
        StringBuilder jsonBuilder = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            int data;
            while ((data = fis.read()) != -1) {
                jsonBuilder.append((char) data);
            }
            System.out.println("JSON data loaded from file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error loading JSON data from file: " + e.getMessage());
        }
        return jsonBuilder.toString();
    }

    public  boolean isBinFileEmpty() {
        String filePath ="Session.bin" ;
        File file = new File(filePath);
        return file.length() == 0;
    }
    public  void clearBinFile() {
        String filePath = "Session.bin" ;
        try (FileOutputStream fos = new FileOutputStream(filePath, false)) {
            // Truncate the file by opening FileOutputStream in overwrite mode (false)
            fos.getChannel().truncate(0);
            System.out.println("Binary file cleared: " + filePath);
        } catch (IOException e) {
            System.err.println("Error clearing binary file: " + e.getMessage());
        }
    }

}
