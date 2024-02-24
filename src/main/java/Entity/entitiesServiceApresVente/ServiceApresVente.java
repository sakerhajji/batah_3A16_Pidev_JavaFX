package Entity.entitiesServiceApresVente;

import Entity.UserAdmin.Admin;
import Entity.UserAdmin.Membre;
import Entity.entitiesPartenaire.partenaire;
import Entity.entitiesProduits.Achats;

import java.sql.Date;

public class ServiceApresVente {
    private int idService;
    private String description;
    private String type;
    private Date date;
    private boolean status;
    private partenaire idPartenaire;
    private Achats idAchats;


    public ServiceApresVente(int idService, String description, String type, Date date, boolean status, partenaire idPartenaire, Achats idAchats) {
        this.idService = idService;
        this.description = description;
        this.type = type;
        this.date = date;
        this.status = status;
        this.idPartenaire = idPartenaire;
        this.idAchats = idAchats;
    }

    public ServiceApresVente(String description, String type, Date date, boolean status, partenaire idPartenaire, Achats idAchats) {
        this.description = description;
        this.type = type;
        this.date = date;
        this.status = status;
        this.idPartenaire = idPartenaire;
        this.idAchats = idAchats;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public partenaire getIdPartenaire() {
        return idPartenaire;
    }

    public void setIdPartenaire(partenaire idPartenaire) {
        this.idPartenaire = idPartenaire;
    }

    public Achats getIdAchats() {
        return idAchats;
    }

    public void setIdAchats(Achats idAchats) {
        this.idAchats = idAchats;
    }

    public ServiceApresVente() {
    }



}
