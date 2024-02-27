package org.example;

import Entity.UserAdmin.Admin;
import Services.UserAdmineServices.AdminService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
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
    public static void main(String[] args) {
        String sourceImagePath = "C:" + File.separator + "Users" + File.separator + "saker" + File.separator + "Desktop" + File.separator + "A188.jpg";
        String destinationFolderPath = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "images" + File.separator + "imageUserAdmin"; // Relative path to project directory
        String newFileName = "newFileName.jpg"; // New file name
        try {
            copyImage(sourceImagePath, destinationFolderPath, newFileName);
            System.out.println("Image copied and renamed successfully.");
        } catch (IOException e) {
            System.out.println("Error copying image: " + e.getMessage());
        }
    }

}