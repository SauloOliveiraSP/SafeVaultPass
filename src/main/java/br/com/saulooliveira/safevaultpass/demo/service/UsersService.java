package br.com.saulooliveira.safevaultpass.demo.service;

import java.util.List;

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
    
    //Cria novo usuário
    public List<UsersEntity> create(UsersEntity user) {
        usersRepository.save(user);
        return list();
    }
}
