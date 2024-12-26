package br.com.saulooliveira.safevaultpass.demo.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
