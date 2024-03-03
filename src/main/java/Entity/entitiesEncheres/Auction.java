package Entity.entitiesEncheres;

public class Auction {
    private int id;
    private String imageUrl;
    private String description;
    private boolean sold;

    public Auction(int id, String imageUrl, String description, boolean sold) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.description = description;
        this.sold = sold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", sold=" + sold +
                '}';
    }
}
