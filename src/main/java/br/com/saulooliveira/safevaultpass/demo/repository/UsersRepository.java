package br.com.saulooliveira.safevaultpass.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.saulooliveira.safevaultpass.demo.entity.UsersEntity;

public interface UsersRepository extends JpaRepository<UsersEntity, Long>{
    
}
