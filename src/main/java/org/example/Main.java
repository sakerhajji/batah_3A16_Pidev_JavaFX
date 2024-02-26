package org.example;

import Entity.UserAdmin.Admin;
import Services.UserAdmineServices.AdminService;

import java.io.File;
import java.net.MalformedURLException;

public class Main {
    public static String getImageURL(String imageName) {
        File file = new File(imageName);
        if (file.exists()) {
            try {
                return file.toURI().toURL().toString();
            } catch (MalformedURLException e) {
                System.err.println("Error creating URL for image: " + e.getMessage());
            }
        } else {
            System.err.println("Image file not found: " + imageName);
        }
        return null;
    }
    public static void main(String[] args) {

        String imageName = "C:\\Users\\saker\\Desktop\\esprit\\3eme\\Pidev\\batah_3A16_Pidev_JavaFX\\src\\main\\resources\\images\\sSakerHajji.png";
        String imageURL = getImageURL(imageName);
        if (imageURL != null) {
            System.out.println("Image URL: " + imageURL);
        } else {
            System.out.println("Failed to get image URL.");
        }






    }
}