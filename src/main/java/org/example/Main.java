package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class Main {
    public static void main(String[] args) {
        String sourceImagePath = "/C:/Users/saker/Desktop/A188.jpg";
        String destinationFolderPath = "/C:/Users/saker/Desktop/esprit/3eme/Pidev/batah_3A16_Pidev_JavaFX/src/main/resources/images/";
        try {
            copyImage(sourceImagePath, destinationFolderPath);
            System.out.println("Image copied successfully.");
        } catch (IOException e) {
            System.out.println("Error copying image: " + e.getMessage());
        }
    }

    public static void copyImage(String sourceImagePath, String destinationFolderPath) throws IOException {
        File sourceFile = new File(sourceImagePath);
        File destinationFolder = new File(destinationFolderPath);

        // Create the destination folder if it does not exist
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs();
        }

        // If the source file exists and it's a file (not a directory)
        if (sourceFile.exists() && sourceFile.isFile()) {
            // Get the file name from the source path
            String fileName = getFileName(sourceImagePath);
            // Create a destination file with the same name in the destination folder
            File destinationFile = new File(destinationFolder, fileName);

            // Copy the file
            try (FileInputStream inStream = new FileInputStream(sourceFile);
                 FileOutputStream outStream = new FileOutputStream(destinationFile);
                 FileChannel inChannel = inStream.getChannel();
                 FileChannel outChannel = outStream.getChannel()) {
                inChannel.transferTo(0, inChannel.size(), outChannel);
            }
        } else {
            throw new IOException("Source file does not exist or is not a file.");
        }
    }

    public static String getFileName(String filePath) {
        int lastIndex = filePath.lastIndexOf('/') != -1 ? filePath.lastIndexOf('/') : filePath.lastIndexOf('\\');
        if (lastIndex == -1) {
            return filePath;
        }
        return filePath.substring(lastIndex + 1);
    }
}
