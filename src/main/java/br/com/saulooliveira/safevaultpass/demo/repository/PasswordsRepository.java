package br.com.saulooliveira.safevaultpass.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.saulooliveira.safevaultpass.demo.entity.PasswordsEntity;

@Repository
public interface PasswordsRepository extends JpaRepository<PasswordsEntity,Long>{
    List<PasswordsEntity> findByUserId(Long userId);
}
