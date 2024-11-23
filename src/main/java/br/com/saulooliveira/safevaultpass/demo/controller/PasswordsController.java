package br.com.saulooliveira.safevaultpass.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.saulooliveira.safevaultpass.demo.entity.PasswordsEntity;
import br.com.saulooliveira.safevaultpass.demo.service.PasswordsService;

@RestController
@RequestMapping("/passwords")
public class PasswordsController {
    private final PasswordsService passwordsService;

    public PasswordsController(PasswordsService passwordsService) {
        this.passwordsService = passwordsService;
    }
    
    @GetMapping
    public List<PasswordsEntity> list() {
        return passwordsService.list();
    }

    @PostMapping
    public List<PasswordsEntity> create(PasswordsEntity password) {
        return  passwordsService.create(password);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<PasswordsEntity> createPassword(@PathVariable Long userId, @RequestBody PasswordsEntity password) {
        PasswordsEntity createdPassword = passwordsService.create(userId, password);
        return ResponseEntity.ok(createdPassword);
    }
}
