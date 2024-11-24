package br.com.saulooliveira.safevaultpass.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.saulooliveira.safevaultpass.demo.entity.UsersEntity;
import br.com.saulooliveira.safevaultpass.demo.service.UsersService;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public List<UsersEntity> list() {
        return usersService.list();
    }

    @GetMapping("/active")
    public List<UsersEntity> listActiveUsers() {
        return usersService.listActive();
    }

    @PostMapping
    public List<UsersEntity> create(UsersEntity user) {
        return usersService.create(user);
    }

    @PutMapping("/{id}/active")
    public ResponseEntity<Map<String, String>> setActiveStatus( @PathVariable Long id, @RequestParam Boolean active) {
        Map<String, String> response = usersService.active(id, active);
        return ResponseEntity.ok(response);
    }
    
}
