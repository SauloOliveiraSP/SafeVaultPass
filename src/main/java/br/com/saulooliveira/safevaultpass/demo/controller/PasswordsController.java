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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.saulooliveira.safevaultpass.demo.entity.PasswordsEntity;
import br.com.saulooliveira.safevaultpass.demo.service.JwtService;
import br.com.saulooliveira.safevaultpass.demo.service.PasswordsService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/passwords")
@RequiredArgsConstructor
public class PasswordsController {

    private final PasswordsService passwordsService;
    private final JwtService jwtService;  // Injeção do JwtService

    // Lista todas as senhas do usuário logado
    @GetMapping
    public List<PasswordsEntity> list(@RequestHeader("Authorization") String authorizationHeader) {
        Long userId = jwtService.extractUserId(authorizationHeader); // Método que extrai o userId do token
        return passwordsService.listByUserId(userId);
    }

    // Lista as senhas do usuário logado, usando o JWT para autenticar
    @GetMapping("/{id}")
    public ResponseEntity<PasswordsEntity> getById(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        Long userId = jwtService.extractUserId(authorizationHeader); // Extrai o ID do usuário do JWT
        PasswordsEntity password = passwordsService.getByIdAndUser(id, userId); // Método que retorna a senha só se for do usuário
        return ResponseEntity.ok(password);
    }

    // Cria uma nova senha para o usuário logado
    @PostMapping
    public ResponseEntity<PasswordsEntity> create(@RequestBody PasswordsEntity password, @RequestHeader("Authorization") String authorizationHeader) {
        Long userId = jwtService.extractUserId(authorizationHeader); // Extrai o userId do JWT
        PasswordsEntity createdPassword = passwordsService.create(userId, password);
        return ResponseEntity.ok(createdPassword);
    }

    // Atualiza a senha, garantindo que o usuário só possa atualizar as suas próprias senhas
    @PutMapping("/{id}")
    public ResponseEntity<PasswordsEntity> update(@PathVariable Long id, @RequestBody PasswordsEntity password, @RequestHeader("Authorization") String authorizationHeader) {
        Long userId = jwtService.extractUserId(authorizationHeader);
        PasswordsEntity updatedPassword = passwordsService.update(id, password, userId); // Passando o userId para garantir que só o dono possa atualizar
        return ResponseEntity.ok(updatedPassword);
    }

    // Deleta a senha do usuário logado
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        Long userId = jwtService.extractUserId(authorizationHeader);
        Map<String, String> response = passwordsService.delete(id, userId); // Deletar senha do usuário logado
        return ResponseEntity.ok(response);
    }
}
