package Entity.ControleDeSaisieClass;

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


    }


