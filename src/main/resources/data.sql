-- Limpa dados anteriores da tabela 'users' (se estiver usando ddl-auto=create ou create-drop, isso ajuda a começar do zero)
-- Se ddl-auto=update, esta linha pode não ser necessária ou pode causar erro se a tabela não existir na primeira vez.
-- Considere remover se ddl-auto=update e você não quiser limpar a tabela a cada reinício.
-- DELETE FROM users; 

-- Usuário Administrador
-- Substitua 'SEU_HASH_BCRYPT_PARA_ADMIN123' pelo hash gerado para a senha "admin123"
-- bootcamp_duo_final/src/main/resources/data.sql
INSERT INTO users (id, nome, email, senha, ROLE) VALUES -- ROLE em maiúsculo
(RANDOM_UUID(), 'Administrador Principal', 'admin@empresa.com', '$2a$10$JpL4yTj7U9C8U.mXqj7VMOl3bS3BqRk6.Yc2f3b.kI9Z0rQZ1g0tO', 'ADMIN');

INSERT INTO users (id, nome, email, senha, ROLE) VALUES -- ROLE em maiúsculo
(RANDOM_UUID(), 'Colaborador Um', 'user@empresa.com', '$2a$10$b.rP.b.H7kX9jA8sS8xPqOQ8sA7i.kU5gL0i.oP1nQ8mP2jR3sYtU', 'USER');
-- O hash acima é apenas um EXEMPLO para "user123", USE O SEU GERADO!

-- Você pode adicionar mais usuários aqui se necessário
-- Exemplo de outro usuário comum:
-- INSERT INTO users (id, nome, email, senha, role) VALUES
-- (RANDOM_UUID(), 'Colaborador Dois', 'user2@empresa.com', 'SEU_HASH_BCRYPT_PARA_OUTRA_SENHA', 'USER');