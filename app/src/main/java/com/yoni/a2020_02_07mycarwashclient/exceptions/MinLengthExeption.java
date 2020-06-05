package com.yoni.a2020_02_07mycarwashclient.exceptions;

public class MinLengthExeption extends UserExeption {

    public MinLengthExeption(String message){
        super(message);
    }

    public MinLengthExeption(){
        super("There is not enough characters");
    }
}
