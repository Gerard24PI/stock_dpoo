package controller;

import model.Candle;
import model.Error;

import java.util.ArrayList;

public class Commons {

    public static final boolean BUY = true;
    public static final boolean SELL = false;

    /**
     * Checks fields are not empty, email is formatted, password is strong and confirm equals password
     * @param username username input
     * @param email email input
     * @param password password input
     * @param confirm confirm input
     * @return integer referring type of error
     */

    public static Error checkSignUp(String username, String email, String password, String confirm){
        //empty fields
        if(username.equals("")||email.equals("")||password.equals("")||confirm.equals(""))
            return Error.EMPTY_FIELDS;

        //email regex (2)
        //https://emailregex.com/ RFC 5322
        if (!email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])" +
                "*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\" +
                "[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|" +
                "[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])"))
            return Error.EMAIL_FORMAT;

        //password strong (3): MIT directives
        //https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@$!#~€¬()¿º\\-ª\"%*?&_{}/]{8,}$"))
            return Error.PASSWORD_FORMAT;

        //confirm equals password (4)
        if (!confirm.equals(password))
            return Error.PASSWORD_CONFIRMATION;

        //If there's no mismatch, 0 errors
        return Error.SIGNUP_OK;
    }

    public static void addCandle(Candle candle, ArrayList<Candle> candles) {
        candles.remove(0);
        candles.add(candle);
    }

    public static void addShareValueSample(float sample, ArrayList<Float> samples) {
        samples.remove(0);
        samples.add(sample);
    }

    public static String getJlImgUser(int i) {
        switch (i){
            case 1:
                return "Doggo";
            case 2:
                return "Grumpy Cat";
            case 3:
                return "Boromir";
            case 4:
                return "Horse";
            case 5:
                return "Sculpture";
            case 6:
                return "Gentleman";
            case 7:
                return "Ceus";
            case 8:
                return "Super Taldo";
            case 9:
                return "Pedobear";
            default:
                return "Default";
        }
    }
}
