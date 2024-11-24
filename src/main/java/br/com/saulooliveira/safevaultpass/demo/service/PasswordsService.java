package br.com.saulooliveira.safevaultpass.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.saulooliveira.safevaultpass.demo.entity.PasswordsEntity;
import br.com.saulooliveira.safevaultpass.demo.entity.UsersEntity;
import br.com.saulooliveira.safevaultpass.demo.repository.PasswordsRepository;
import br.com.saulooliveira.safevaultpass.demo.repository.UsersRepository;

@Service
public class PasswordsService {
    private final PasswordsRepository passwordsRepository;
    private final UsersRepository usersRepository;

    public PasswordsService(PasswordsRepository passwordsRepository, UsersRepository usersRepository) {
        this.passwordsRepository = passwordsRepository;
        this.usersRepository = usersRepository;
    }

    //Listar todas as senhas
    public List<PasswordsEntity> list() {
        return passwordsRepository.findAll();
    }

    //Listar todas as senhas
    public List<PasswordsEntity> listByIdUser(Long userId) {
        // Verifica se o usuário existe
        usersRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));

        // Retorna as senhas associadas ao usuário
        return passwordsRepository.findByUserId(userId);
    }

    //Busca senha por ID
    public PasswordsEntity getById(Long id) {
        return passwordsRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Senha com ID " + id + " não encontrada."));
    }

    // Criar Senha por usuário
    public PasswordsEntity create(Long userId, PasswordsEntity password) {
        UsersEntity user = usersRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));

        password.setUser(user);

        return passwordsRepository.save(password);
    }

    // Alterar Senha por ID
    public PasswordsEntity update(Long id, PasswordsEntity password) {
        PasswordsEntity existingPasswords = passwordsRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Senha com ID " + id + " não encontrada."));

        // Atualiza os campos
        existingPasswords.setService(password.getService());
        existingPasswords.setLogin(password.getLogin());
        existingPasswords.setPassword(password.getPassword());

        return passwordsRepository.save(existingPasswords);
    }

    // Deletar Senha por ID
    public Map<String, String> delete(Long id) {
        PasswordsEntity existingPasswords = passwordsRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Senha com ID " + id + " não encontrada."));

        passwordsRepository.delete(existingPasswords);

         // Retorna uma mensagem de confirmação
        Map<String, String> response = new HashMap<>();
        response.put("message", "Senha com ID " + id + " removida com sucesso.");
        return response;
    }

}
