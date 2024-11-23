package br.com.saulooliveira.safevaultpass.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PostMapping
    public List<UsersEntity> create(UsersEntity user) {
        return usersService.create(user);
    }
    
}
