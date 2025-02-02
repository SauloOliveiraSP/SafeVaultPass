# Password Vault API (SafeVaultPass)  

## Funcionalidades  
1. **Cadastro de Usuários**  
   - Cada usuário tem seu próprio cofre de senhas.

2. **CRUD de Senhas**  
   - Criar, ler, atualizar e excluir senhas.  
   - Campos: nome do serviço (banco, e-commerce, redes sociais), login, senha.

3. **Criptografia**  
   - Armazenamento seguro das senhas usando algoritmos como AES ou bcrypt.

4. **Autenticação e Autorização**  
   - Login com JWT (JSON Web Tokens).  
   - Limitação de acesso às senhas por usuário.

5. **Exportação de Dados**  
   - Exportar senhas em um arquivo seguro (JSON ou CSV criptografado).

---

## Tecnologias e Ferramentas  
- **Framework**: Spring Boot  
- **Banco de Dados**: MySQL
- **Segurança**:  
  - Spring Security para autenticação e autorização  
  - HTTPS para comunicação segura  
- **Documentação**: Swagger/OpenAPI  

---

## Estrutura das Rotas  
- **Usuários**:  
  - `POST /api/v1/auth/register`: Criar usuário  
  - `POST /api/v1/auth/login`: Autenticação  

- **Senhas**:  
  - `GET /api/v1/passwords`: Listar senhas  
  - `POST /api/v1/passwords`: Criar nova senha  
  - `PUT /api/v1/passwords/{id}`: Atualizar senha  
  - `DELETE /api/v1/passwords/{id}`: Excluir senha  

---

## Diferenciais (Extras)  
- **Expiração de Sessão**: Tokens JWT com tempo limitado  

---

Este projeto destaca minha capacidade de trabalhar com segurança, algo altamente valorizado no mercado!
