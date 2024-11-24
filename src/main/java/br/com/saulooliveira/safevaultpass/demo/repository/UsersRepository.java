package br.com.saulooliveira.safevaultpass.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.saulooliveira.safevaultpass.demo.entity.UsersEntity;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long>{
        // Busca apenas usuários ativos
        List<UsersEntity> findByActiveTrue();
}
