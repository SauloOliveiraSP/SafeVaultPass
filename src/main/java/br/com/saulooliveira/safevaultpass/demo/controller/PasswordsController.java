package br.com.saulooliveira.safevaultpass.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/user/{userId}")
    public List<PasswordsEntity> listByIdUser(@PathVariable Long userId) {
        return passwordsService.listByIdUser(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PasswordsEntity> getById(@PathVariable Long id) {
        PasswordsEntity password =  passwordsService.getById(id);
        return ResponseEntity.ok(password);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<PasswordsEntity> create(@PathVariable Long userId, @RequestBody PasswordsEntity password) {
        PasswordsEntity createdPassword = passwordsService.create(userId, password);
        return ResponseEntity.ok(createdPassword);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PasswordsEntity> update(@PathVariable Long id, @RequestBody PasswordsEntity password) {
        PasswordsEntity createdPassword = passwordsService.update(id, password);
        return ResponseEntity.ok(createdPassword);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        Map<String, String> response = passwordsService.delete(id);
        return ResponseEntity.ok(response);
    }
}
