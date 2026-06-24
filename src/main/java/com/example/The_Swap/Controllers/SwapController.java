package com.example.The_Swap.Controllers;

import com.example.The_Swap.Model.LoginDTO;
import com.example.The_Swap.Model.userDTO;
import com.example.The_Swap.Services.Login;
import com.example.The_Swap.Services.RagService;
import com.example.The_Swap.Services.Swap;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/api/v1/")
public class SwapController {
  private final Swap Swap;
  private final RagService ragService;
  private final Login login;

  public SwapController(com.example.The_Swap.Services.Swap swap, RagService ragService, Login login) {
    Swap = swap;
    this.ragService = ragService;
    this.login = login;
  }

  @PostMapping("load")
  public void loadDoc(@RequestBody MultipartFile file) throws IOException {
    ragService.saveSegments(ragService.saveDocument(file));
  }

  @GetMapping("login")
  public ResponseEntity<userDTO> login(@Valid @RequestBody LoginDTO input) {
    System.out.println("accepted");
    return login.execute(input);
  }

  @GetMapping("swap")
  public ResponseEntity<Resource> GetProccessedFile(@RequestBody MultipartFile file) {
    return Swap.execute(file);
  }

}
