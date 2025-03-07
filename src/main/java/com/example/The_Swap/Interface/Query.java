package com.example.The_Swap.Interface;

import org.springframework.http.ResponseEntity;

public interface Query<I,O> {
    ResponseEntity<O> execute(I Input);
}
