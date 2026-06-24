package com.example.The_Swap.Exceptions;

public class noUsernameOrPasswordException extends RuntimeException {
    public noUsernameOrPasswordException(){
        super("Please enter the correct password or Email");
    }
}
