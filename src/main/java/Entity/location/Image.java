package Entity.location;

import Entity.location.Location;

public class Image {
    @Override
    public String toString() {
        return "Image{" +
                "idImage=" + idImage +
                ", url='" + url + '\'' +
                ", location=" + location +
                '}';
    }

    private int idImage;
    private String url;
    private Location location; // Reference to the location object

    // Constructors
    public Image() {
    }

    public Image(String url, Location location) {
        this.url = url;
        this.location = location; // Set the location field
    }

    public Image(int idImage, String url, Location location) {
        this.idImage = idImage;
        this.url = url;
        this.location = location; // Set the location field
    }

    // Getters and setters
    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
