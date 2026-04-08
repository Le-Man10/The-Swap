package com.example.The_Swap.Exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = org.springframework.http.HttpStatus.BAD_REQUEST)
public class EmptyFileException extends RuntimeException {
    public EmptyFileException(){
        super("File has no contents. Please insert a file with content");
    }
}
