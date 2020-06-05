package com.yoni.a2020_02_07mycarwashclient.exceptions;

public class NotPhoneNumber extends UserExeption{

    public NotPhoneNumber(String message){
        super(message);
    }

    public NotPhoneNumber(){
        super("Phone number is not valid");
    }
}
