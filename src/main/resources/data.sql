-- Limpa dados anteriores da tabela 'users' (se estiver usando ddl-auto=create ou create-drop, isso ajuda a começar do zero)
-- Se ddl-auto=update, esta linha pode não ser necessária ou pode causar erro se a tabela não existir na primeira vez.
-- Considere remover se ddl-auto=update e você não quiser limpar a tabela a cada reinício.
-- DELETE FROM users; 

-- Usuário Administrador
INSERT INTO users (id, nome, email, senha, ROLE) VALUES 
(RANDOM_UUID(), 'Administrador Principal', 'admin@empresa.com', '$2a$10$dYDPIT0P3o6j3ohNOTmqN.Ljv6fsv/lIn89IdjPm8IrnHEoAk9HY2', 'ADMIN');

-- Usuário DevOps
INSERT INTO users (id, nome, email, senha, ROLE) VALUES
(RANDOM_UUID(), 'DevOps', 'devops@empresa.com', '$2a$10$kGrMHKrgndr2euRmA9orve3oBu92Qp4v.h.znqjndBaneokeGB1/C', 'DEVOPS'); -- Substitua pelo hash real

-- Usuário Inteligência (Intel)
INSERT INTO users (id, nome, email, senha, ROLE) VALUES
(RANDOM_UUID(), 'Inteligência Artificial', 'intel@empresa.com', '$2a$10$5XrFRIb/DU3g1As2ilRg3uSW2h5nTL6639ky8udug26KfWiCg1b2G', 'INTEL'); -- Substitua pelo hash real