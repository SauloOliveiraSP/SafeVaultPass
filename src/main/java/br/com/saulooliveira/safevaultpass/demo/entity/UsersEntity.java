package br.com.saulooliveira.safevaultpass.demo.entity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UsersEntity implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private boolean active;

    @OneToMany(mappedBy = "user")
    private List<PasswordsEntity> passwords;

    private Set<Role> authorities; // Roles (simplificados para exemplo)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<PasswordsEntity> getPasswords() {
        return passwords;
    }

    public void setPasswords(List<PasswordsEntity> passwords) {
        this.passwords = passwords;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public Set<Role> getRoles() {
        return authorities;
    }

    // Implementação de métodos obrigatórios da interface UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public enum Role implements GrantedAuthority {
        ROLE_USER("USER"),
        ROLE_ADMIN("ADMIN");
    
        private String value;
    
        Role(String value) {
            this.value = value;
        }
    
        public String getValue() {
            return this.value;
        }
    
        @Override
        public String getAuthority() {
            return name();
        }
    }
}
