package org.example;

import Entity.UserAdmin.Admin;
import Entity.UserAdmin.Membre;
import Services.UserAdmineServices.AdminService;
import Services.UserAdmineServices.MembreService;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class Main {

    public static void copyImage(String sourceImagePath, String destinationFolderPath, String newFileName) throws IOException {
        File sourceFile = new File(sourceImagePath);
        File destinationFolder = new File(destinationFolderPath);

        // Create the destination folder if it does not exist
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs();
        }

        // If the source file exists and it's a file (not a directory)
        if (sourceFile.exists() && sourceFile.isFile()) {
            // Check if the file is an image
            if (isImageFile(sourceFile)) {
                // Create a destination file with the new name in the destination folder
                File destinationFile = new File(destinationFolder, newFileName);

                // Copy the file
                try (FileInputStream inStream = new FileInputStream(sourceFile);
                     FileOutputStream outStream = new FileOutputStream(destinationFile);
                     FileChannel inChannel = inStream.getChannel();
                     FileChannel outChannel = outStream.getChannel()) {
                    inChannel.transferTo(0, inChannel.size(), outChannel);
                }
            } else {
                throw new IOException("Source file is not an image.");
            }
        } else {
            throw new IOException("Source file does not exist or is not a file.");
        }
    }

    // Method to check if a file is an image based on its extension
    public static boolean isImageFile(File file) {
        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        // List of image extensions to check against
        String[] imageExtensions = {"jpg", "jpeg", "png", "gif", "bmp"};
        for (String ext : imageExtensions) {
            if (extension.equalsIgnoreCase(ext)) {
                return true;
            }
        }
        return false;
    }
    public static void saveJsonToBinFile(String json) {
        String filePath="output.bin";
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

    public static String loadJsonFromBinFile() {
        String filePath ="output.bin";
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

    public static boolean isBinFileEmpty() {
        String filePath ="output.bin" ;
        File file = new File(filePath);
        return file.length() == 0;
    }
    public static void clearBinFile() {
        String filePath = "output.bin" ;
        try (FileOutputStream fos = new FileOutputStream(filePath, false)) {
            // Truncate the file by opening FileOutputStream in overwrite mode (false)
            fos.getChannel().truncate(0);
            System.out.println("Binary file cleared: " + filePath);
        } catch (IOException e) {
            System.err.println("Error clearing binary file: " + e.getMessage());
        }
    }
    public static void main(String[] args) {

//        Membre membre =new Membre() ;
//        membre.clearBinFile();
//        MembreService membreService= new MembreService() ;
//        membre=membreService.readById(2);
//
//        membre.saveJsonToBinFile(membre);
//
//        membre=membre.convertToMembre(membre.loadJsonFromBinFile()) ;
//

        String password = "test";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println("Hashed password: " + hashedPassword);


    }


}