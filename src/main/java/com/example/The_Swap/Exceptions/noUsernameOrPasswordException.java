package com.example.TailorMe.API.Exceptionhandling;

public class noUsernameOrPasswordException extends RuntimeException {
    public noUsernameOrPasswordException(){
        super("Please enter the correct password or Email");
    }
}
