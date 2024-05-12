package Entity.entitiesPartenaire;

public class AvisLivraison {
    private int idAvis;
    private int idLivraison;
    private String commentaire;

    // Constructeur
    public AvisLivraison(){}
    public AvisLivraison(int idAvis, int idLivraison, String commentaire) {
        this.idAvis = idAvis;
        this.idLivraison = idLivraison;
        this.commentaire = commentaire;
    }

    // Getters
    public int getIdAvis() {
        return idAvis;
    }

    public int getIdLivraison() {
        return idLivraison;
    }

    public String getCommentaire() {
        return commentaire;
    }

    // Setters
    public void setIdAvis(int idAvis) {
        this.idAvis = idAvis;
    }

    public void setIdLivraison(int idLivraison) {
        this.idLivraison = idLivraison;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    // Méthode toString pour affichage
    @Override
    public String toString() {
        return "AvisLivraison{" +
                "idAvis=" + idAvis +
                ", idLivraison=" + idLivraison +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }
}
