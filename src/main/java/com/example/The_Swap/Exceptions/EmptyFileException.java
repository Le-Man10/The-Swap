package com.example.The_Swap.Exceptions;

public class EmptyFileException extends RuntimeException {
    public EmptyFileException(){
        super("File has no contents. Please insert a file with content");
    }
}
