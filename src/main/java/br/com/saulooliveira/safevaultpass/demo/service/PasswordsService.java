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

    // Lista senhas do usuário autenticado
    public List<PasswordsEntity> listByUserId(Long userId) {
        return passwordsRepository.findByUserId(userId); // Filtra as senhas pelo userId
    }

    // Busca uma senha por ID, garantindo que a senha pertença ao usuário logado
    public PasswordsEntity getByIdAndUser(Long id, Long userId) {
        PasswordsEntity password = passwordsRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Senha com ID " + id + " não encontrada."));
        if (!password.getUser().getId().equals(userId)) {
            throw new RuntimeException("Essa senha não pertence ao usuário logado.");
        }
        return password;
    }

    // Cria uma nova senha para o usuário logado
    public PasswordsEntity create(Long userId, PasswordsEntity password) {
        UsersEntity user = usersRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));

        password.setUser(user);

        if (password.getId() != null) {
            passwordsRepository.findById(password.getId())
                .orElseThrow(() -> new RuntimeException("Senha não encontrada para atualização."));
        }

        return passwordsRepository.save(password);
    }

    // Atualiza a senha, garantindo que ela pertença ao usuário logado
    public PasswordsEntity update(Long id, PasswordsEntity password, Long userId) {
        PasswordsEntity existingPassword = passwordsRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Senha com ID " + id + " não encontrada."));
        if (!existingPassword.getUser().getId().equals(userId)) {
            throw new RuntimeException("Essa senha não pertence ao usuário logado.");
        }
        existingPassword.setLogin(password.getLogin());
        existingPassword.setPassword(password.getPassword());
        return passwordsRepository.save(existingPassword);
    }

    // Deleta a senha, garantindo que ela pertença ao usuário logado
    public Map<String, String> delete(Long id, Long userId) {
        PasswordsEntity password = passwordsRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Senha com ID " + id + " não encontrada."));
        if (!password.getUser().getId().equals(userId)) {
            throw new RuntimeException("Essa senha não pertence ao usuário logado.");
        }
        passwordsRepository.delete(password);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Senha com ID " + id + " removida com sucesso.");
        return response;
    }
}
