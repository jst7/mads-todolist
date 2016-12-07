package helpers;

import java.util.Date;
import javax.persistence.*;
import play.data.validation.Constraints;
import play.data.format.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Random;

import models.*;

public class Helper {

    // Un constructor por defecto
    public Helper() {}

    /**
    * Función que convierte un string en hash MD5.
    * Se utilizará principalmente para la contraseña de usuarios.
    */
    public String convertToMD5(String input) {
        try {
            MessageDigest md        = MessageDigest.getInstance("MD5");
            byte[] messageDigest    = md.digest(input.getBytes());
            BigInteger number       = new BigInteger(1, messageDigest);
            String hashtext         = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String randomWord(Integer length) {
        String randomStrings = "";
        Random random = new Random();
        char[] word = new char[10];
        for(int i = 0; i < word.length; i++)
        {
            word[i] = (char)('a' + random.nextInt(26));
        }
        return new String(word);
    }
}    
