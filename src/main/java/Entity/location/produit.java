package Entity.location;

import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class produit {

    private String name;
    private String imgSrc;
    private double price;
    private String color;

    public produit(String name, String imgSrc, double price, String color) {
        this.name = name;
        this.imgSrc = imgSrc;
        this.price = price;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}