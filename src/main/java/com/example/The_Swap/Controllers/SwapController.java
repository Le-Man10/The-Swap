package com.example.The_Swap.Controllers;

import com.example.The_Swap.Services.Swap;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "api/v1/")
public class SwapController {
    private final Swap Swap;

    public SwapController(com.example.The_Swap.Services.Swap swap) {
        Swap = swap;
    }

    @GetMapping("swap")
    public ResponseEntity<Resource> GetProccessedFile(@RequestParam("File") MultipartFile file) {
        return Swap.execute(file);
    }

}
