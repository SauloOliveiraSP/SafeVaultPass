package br.com.saulooliveira.safevaultpass.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.saulooliveira.safevaultpass.demo.entity.PasswordsEntity;

public interface PasswordsRepository extends JpaRepository<PasswordsEntity,Long>{
    
}
