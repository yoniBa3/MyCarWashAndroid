package com.yoni.a2020_02_07mycarwashclient.exceptions;

import com.backendless.rt.users.UserInfo;

public class UserExeption extends Exception {

    public UserExeption() {
        super("Error in spaceShip");
    }


    public UserExeption(String message){
        super(message);
    }
}
