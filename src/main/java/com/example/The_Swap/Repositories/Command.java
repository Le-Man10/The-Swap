package com.example.The_Swap.Repositories;

import org.springframework.http.ResponseEntity;

public interface Command<I,O>{
    ResponseEntity<O> execute(I input);
}
