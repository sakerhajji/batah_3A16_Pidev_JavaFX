package Entity.entitiesProduits;

public class Avis {
    private int id_rating;
    private int id_user;
    private int id_produit;
    private double rating;

    public Avis(int id_rating, int id_user, int id_produit, double rating) {
        this.id_rating = id_rating;
        this.id_user = id_user;
        this.id_produit = id_produit;
        this.rating = rating;
    }

    public Avis(int id_user, int id_produit, double rating) {
        this.id_user = id_user;
        this.id_produit = id_produit;
        this.rating = rating;
    }


    public int getId_rating() {
        return id_rating;
    }

    public void setId_rating(int id_rating) {
        this.id_rating = id_rating;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Avis{" +
                "id_rating=" + id_rating +
                ", id_user=" + id_user +
                ", id_produit=" + id_produit +
                ", rating=" + rating +
                '}';
    }
}
