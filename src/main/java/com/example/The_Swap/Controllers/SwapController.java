package com.example.The_Swap.Controllers;

import com.example.The_Swap.Interface.AiChatService;
import com.example.The_Swap.Services.RagService;
import com.example.The_Swap.Services.Swap;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RestController
@RequestMapping(path = "/api/v1/")
public class SwapController {
    private final Swap Swap;
    private final AiChatService chatService;
    private final RagService ragService;

    public SwapController(com.example.The_Swap.Services.Swap swap, AiChatService chatService, RagService ragService) {
        Swap = swap;
        this.chatService = chatService;
        this.ragService = ragService;
    }

    @PostMapping("load")
    public void loadDoc(@RequestBody MultipartFile file) throws IOException {
        ragService.saveSegments(ragService.saveDocument(file));
    }

    @GetMapping("swap")
    public ResponseEntity<Resource> GetProccessedFile(@RequestBody MultipartFile file) {
        return Swap.execute(file);
    }

}
