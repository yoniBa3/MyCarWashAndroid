package com.yoni.a2020_02_07mycarwashclient.exceptions;

public class NotCarNumber extends UserExeption {
    public NotCarNumber(String message){
        super(message);
    }

    public NotCarNumber(){
        super("Must be a car number");
    }
}
