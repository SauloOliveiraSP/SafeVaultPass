package br.com.saulooliveira.safevaultpass.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.saulooliveira.safevaultpass.demo.entity.PasswordsEntity;
import br.com.saulooliveira.safevaultpass.demo.entity.UsersEntity;
import br.com.saulooliveira.safevaultpass.demo.repository.PasswordsRepository;
import br.com.saulooliveira.safevaultpass.demo.repository.UsersRepository;

@Service
public class PasswordsService {
    private final PasswordsRepository passwordsRepository;
    private UsersRepository usersRepository;

    public PasswordsService(PasswordsRepository passwordsRepository, UsersRepository usersRepository) {
        this.passwordsRepository = passwordsRepository;
        this.usersRepository = usersRepository;
    }

    //Listar todas as senhas
    public List<PasswordsEntity> list() {
        return passwordsRepository.findAll();
    }

    //Cria nova senha
    public List<PasswordsEntity> create(PasswordsEntity password) {
        passwordsRepository.save(password);
        return list();
    }

    // Função para criar uma senha associada a um usuário
    public PasswordsEntity create(Long userId, PasswordsEntity password) {
        // Busca o usuário pelo ID
        UsersEntity user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));

        // Associa o usuário à senha
        password.setUser(user);

        // Salva a senha no banco de dados
        return passwordsRepository.save(password);
    }

}
