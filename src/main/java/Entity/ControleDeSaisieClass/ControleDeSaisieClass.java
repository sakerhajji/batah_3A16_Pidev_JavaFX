package Entity.ControleDeSaisieClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControleDeSaisieClass {
    public ControleDeSaisieClass() {
    }
    public  boolean isValidEmail(String email) {
        String pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }
    public  boolean checkText(String s  )
    {
        return s.matches("[a-zA-Z]*") &&  s.length()>=2 ;
    }
    public boolean chekNumero(String s) {
        return s.matches("[0-9]*") && s.length() ==8 ;
    }

    public  boolean isDateValidAndOver18(LocalDate selectedDate) {

        if (selectedDate.isAfter(LocalDate.now())) return false;
            Period age = Period.between(selectedDate, LocalDate.now());
            return age.getYears() >= 18;
        }
    public  int checkPasswordStrength(String password) {
        if (password.length() < 6) return 0;
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,}$");
        Matcher matcher = pattern.matcher(password);

        if (matcher.matches()) return 1;
        else return -1;

    }

    public  void copyImage(String sourceImagePath, String destinationFolderPath) throws IOException {
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


