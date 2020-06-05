package com.yoni.a2020_02_07mycarwashclient.exceptions;

public class PasswordsNotTheSame extends UserExeption {

    public PasswordsNotTheSame(String message){
        super(message);
    }

    public PasswordsNotTheSame(){
        super("The passwords are not the same");
    }
}
