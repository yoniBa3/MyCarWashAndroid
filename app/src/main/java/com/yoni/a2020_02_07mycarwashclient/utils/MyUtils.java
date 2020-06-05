package com.yoni.a2020_02_07mycarwashclient.utils;

import android.util.Log;

import com.backendless.BackendlessUser;
import com.yoni.a2020_02_07mycarwashclient.DataClasses.Machon;
import com.yoni.a2020_02_07mycarwashclient.DataClasses.Services;
import com.yoni.a2020_02_07mycarwashclient.DataClasses.Wash;
import com.yoni.a2020_02_07mycarwashclient.exceptions.MinLengthExeption;
import com.yoni.a2020_02_07mycarwashclient.exceptions.NotCarNumber;
import com.yoni.a2020_02_07mycarwashclient.exceptions.NotPhoneNumber;
import com.yoni.a2020_02_07mycarwashclient.exceptions.PasswordsNotTheSame;

import java.util.ArrayList;
import java.util.List;

public class MyUtils {

    public static final String APP_ID = "EC0A8396-802D-DDFE-FFF7-BBFF0F009B00";

    public static final String APP_KEY = "C536017F-6B68-44ED-8246-64AFAE968059";

    public static BackendlessUser user;
    public static Machon myMachon;
    public static List<Services> myServices;
    public static List<Wash> myWashes;


    public static void checkMinLength(String string ,int minLength) throws MinLengthExeption {
        if (string.length()<minLength)
            throw new MinLengthExeption("The min length of characters is: "+minLength);
    }

    public static void checkIfPasswordsAreTheSame(String pass1 ,String pass2)throws PasswordsNotTheSame {
        if (!pass1.equals(pass2))
            throw new PasswordsNotTheSame();
    }

    public static void checkCarNumber(String carNumber)throws NotCarNumber{
        if (carNumber.length()!=7 && carNumber.length()!=8)
            throw new NotCarNumber("Car number not valid");

        if (checkIfNumber(carNumber)) {
            throw new NotCarNumber("Must be numbers");
        }
    }

    public static void checkTellNumber(String tellNumber) throws NotPhoneNumber {
        if (tellNumber.length()!=10)
            throw new NotPhoneNumber("Phone number not valid");
        if (checkIfNumber(tellNumber)) {
            throw new NotPhoneNumber("Must be numbers");
        }
    }

    private static boolean checkIfNumber(String number){
        char[]chars = number.toCharArray();
        for (char charAt:chars) {
            if (charAt < 48 || charAt > 57) {
                return true;
            }
        }
        return false;
    }

    public static List<String> getHourList(){
        List<String>list =new ArrayList<>();

        int startNumber = 6;
        int stopNumber = 21;
        int counter = 0;

        while ( startNumber <= stopNumber) {
            String time = "";
            if (counter % 2 == 0) {
                if (startNumber < 10)
                    time = "0";
                time += startNumber + ":00";

            } else {
                if (startNumber < 10)
                    time = "0";
                time += startNumber + ":30";
                startNumber++;

            }
            counter++;
            list.add(time);
        }
        Log.e("Error", "hourList: "+list.toString() );

        return list;
    }




}
