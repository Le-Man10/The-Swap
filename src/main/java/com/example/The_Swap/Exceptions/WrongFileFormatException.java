package com.example.The_Swap.Exceptions;

public class WrongFileFormatException extends RuntimeException{
    public WrongFileFormatException(){
        super("Wrong File format sent, please input file with an extension of either '.xls' or '.xlsx' ");
    }
}
