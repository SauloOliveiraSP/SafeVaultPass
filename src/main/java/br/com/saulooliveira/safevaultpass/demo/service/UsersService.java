package br.com.saulooliveira.safevaultpass.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.saulooliveira.safevaultpass.demo.entity.UsersEntity;
import br.com.saulooliveira.safevaultpass.demo.repository.UsersRepository;

@Service
public class UsersService implements UserDetailsService {
    private final UsersRepository usersRepository;
     private final BCryptPasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    //Listar todos os usuários
    public List<UsersEntity> list() {
        return usersRepository.findAll();
    }

    //Listar apenas os usuários ativos
    public List<UsersEntity> listActive() {
        return usersRepository.findByActiveTrue();
    }
    
    public List<UsersEntity> create(UsersEntity user) {
        // Criptografa a senha antes de salvar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
        return list();
    }

    public boolean existsByUsername(String username) {
        return usersRepository.existsByUsername(username);
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsersEntity> user = usersRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado com o nome de usuário: " + username);
        }

        return user.get();
    }
}
