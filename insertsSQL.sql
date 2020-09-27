INSERT INTO TB_CATEGORIA (id, nome) 
values ('1', 'Eletrônicos');
INSERT INTO TB_CATEGORIA (id, nome) 
values ('2', 'Instrumentos');
INSERT INTO TB_CATEGORIA (id, nome) 
values ('3', 'Veículos');

INSERT INTO TB_USUARIO (id, email, nome, telefone, Is_Active, senha) 
VALUES (1, 'victor@email.com', 'Victor', '(81)99999-9999', 1, 'fcea920f7412b5da7be0cf42b8c93759');
INSERT INTO TB_GRUPO (email, nome) VALUES ('victor@email.com', 'usr');

INSERT INTO TB_USUARIO (id, email, nome, telefone, Is_Active, senha) 
VALUES (2, 'darllan@email.com', 'Darllan', '(81)88888-8888', 1, 'fcea920f7412b5da7be0cf42b8c93759');
INSERT INTO TB_GRUPO (email, nome) VALUES ('darllan@email.com', 'usr');

INSERT INTO TB_ANUNCIO (id, descricao, finalizado, is_active, nome, prazo, valor_base, id_categoria, id_usuario) 
VALUES (1, 'videogame usado por 2 anos inclui 2 controles e 5 jogos', 'false', 1, 'PS4', '2020-09-30T16:00:00', 1500, 1, 2);

INSERT INTO TB_ANUNCIO (id, descricao, finalizado, is_active, nome, prazo, valor_base, id_categoria, id_usuario) 
VALUES (2, 'guitarra stratocaster seminova modelo 2', 'false', 1, 'guitarra', '2020-09-30T16:00:00', 800, 2, 2);

INSERT INTO TB_ANUNCIO (id, descricao, finalizado, is_active, nome, prazo, valor_base, id_categoria, id_usuario) 
VALUES (3, 'Honda Civic modelo 2020, banco de couro, direção hidráulica', 'false', 1, 'Honda Civic', '2020-09-30T16:00:00', 100000, 3, 1);

INSERT INTO TB_ANUNCIO (id, descricao, finalizado, is_active, nome, prazo, valor_base, id_categoria, id_usuario) 
VALUES (4, 'Notebook lenovo ideapad320 com SSD', 'false', 1, 'Notebook lenovo', '2020-09-27T12:00:00', 2000, 1, 1);

INSERT INTO TB_ANUNCIO (id, descricao, finalizado, is_active, nome, prazo, valor_base, id_categoria, id_usuario) 
VALUES (5, 'Note 7 preto com capa e películo 64GB', 'false', 1, 'Xiaomi Redmi Note 7', '2020-09-28T13:00:00', 1000, 1, 1);


SELECT * from TB_ANUNCIO a WHERE a.prazo >= date('now', 'localtime')