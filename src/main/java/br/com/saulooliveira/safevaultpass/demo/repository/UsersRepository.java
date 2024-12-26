package br.com.saulooliveira.safevaultpass.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.saulooliveira.safevaultpass.demo.entity.UsersEntity;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long>{
        // Busca apenas usuários ativos
        List<UsersEntity> findByActiveTrue();

        Optional<UsersEntity> findByUsername(String username);

        // Método para verificar se o usuário existe
        boolean existsByUsername(String username);  
}
