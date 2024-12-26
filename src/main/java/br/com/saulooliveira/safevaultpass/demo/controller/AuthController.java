package br.com.saulooliveira.safevaultpass.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.saulooliveira.safevaultpass.demo.dto.LoginRequest;
import br.com.saulooliveira.safevaultpass.demo.dto.LoginResponse;
import br.com.saulooliveira.safevaultpass.demo.dto.RegisterRequest;
import br.com.saulooliveira.safevaultpass.demo.entity.UsersEntity;
import br.com.saulooliveira.safevaultpass.demo.service.JwtService;
import br.com.saulooliveira.safevaultpass.demo.service.UsersService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Autentica o usuário
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );

            // Carrega o usuário autenticado
            UsersEntity user = (UsersEntity) authentication.getPrincipal();

            // Verifica se o usuário está ativo
            if (!user.isActive()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário inativo");
            }

            // Gera o token JWT
            String token = jwtService.generateToken(user);

            // Retorna a resposta com o token
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            // Verifica se o usuário já existe
            if (usersService.existsByUsername(registerRequest.getUsername())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
            }

            // Cria o novo usuário
            UsersEntity user = new UsersEntity();
            user.setUsername(registerRequest.getUsername());
            user.setPassword(registerRequest.getPassword());
            user.setActive(true);  // Usuário ativo por padrão

            // Chama o serviço para salvar o usuário com a senha criptografada
            usersService.create(user);

            // Gera o token JWT após o registro
            String token = jwtService.generateToken(user);

            // Retorna a resposta com o token
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso. Token: " + token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao registrar o usuário");
        }
    }
}
