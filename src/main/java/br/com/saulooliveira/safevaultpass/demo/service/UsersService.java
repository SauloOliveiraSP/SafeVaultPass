package br.com.saulooliveira.safevaultpass.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.saulooliveira.safevaultpass.demo.entity.UsersEntity;
import br.com.saulooliveira.safevaultpass.demo.repository.UsersRepository;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    //Listar todos os usuários
    public List<UsersEntity> list() {
        return usersRepository.findAll();
    }

    //Listar apenas os usuários ativos
    public List<UsersEntity> listActive() {
        return usersRepository.findByActiveTrue();
    }
    
    //Cria novo usuário
    public List<UsersEntity> create(UsersEntity user) {
        usersRepository.save(user);
        return list();
    }

    // Ativar/Inativar usuário
    public Map<String, String> active(Long userId, Boolean active) {
        UsersEntity user = usersRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));

        user.setActive(active);
        usersRepository.save(user);

         // Retorna uma mensagem de confirmação
        Map<String, String> response = new HashMap<>();
        response.put("message", "O usuário foi " + (user.isActive() ? "ativado" : "inativado") + " com sucesso.");
        return response;
    }
}
