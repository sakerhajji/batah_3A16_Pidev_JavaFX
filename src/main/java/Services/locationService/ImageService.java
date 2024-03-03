package Services.locationService;

import DataBaseSource.DataSource;
import Entity.location.Image;
import Entity.location.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImageService {

    private Connection conn;
    private PreparedStatement pst;

    public ImageService() {
        conn = DataSource.getInstance().getCnx();
    }

    public void add(Image image) {
        String query = "INSERT INTO image (url, idLocations) VALUES (?, ?)";
        try {
            pst = conn.prepareStatement(query);
            pst.setString(1, image.getUrl());
            pst.setInt(2, image.getLocation().getIdLocation()); // Assuming getIdLocation returns the ID of the location
            pst.executeUpdate();
            System.out.println("Image added successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Image image) {
        String query = "DELETE FROM image WHERE idImage=?";
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, image.getIdImage());
            pst.executeUpdate();
            System.out.println("Image deleted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Image image) {
        String query = "UPDATE image SET url=?, idLocations=? WHERE idImage=?";
        try {
            pst = conn.prepareStatement(query);
            pst.setString(1, image.getUrl());
            pst.setInt(2, image.getLocation().getIdLocation());
            pst.setInt(3, image.getIdImage());
            pst.executeUpdate();
            System.out.println("Image updated successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Image> readAllByLocationId(int locationId) {
        String query = "SELECT * FROM image WHERE idLocations=?";
        List<Image> images = new ArrayList<>();
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, locationId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Image image = new Image();
                image.setIdImage(rs.getInt("idImage"));
                image.setUrl(rs.getString("url"));

                // Create a new Location object and set its ID
                Location location = new Location();
                location.setIdLocation(locationId);

                // Set the Location object in the Image
                image.setLocation(location);

                images.add(image);
            }
            pst.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return images;
    }

    public Image getImageByLocationId(int locationId) {
        String query = "SELECT * FROM image WHERE idLocations=?";
        Image image = null;
        try {
            pst = conn.prepareStatement(query);
            pst.setInt(1, locationId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                image = new Image();
                image.setIdImage(rs.getInt("idImage"));
                image.setUrl(rs.getString("url"));
                // Assuming you have a LocationService to fetch location by ID
                LocationService locationService = new LocationService();
                image.setLocation(locationService.readById(locationId));
            }
            pst.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return image;
    }


}