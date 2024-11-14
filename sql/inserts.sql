begin;

INSERT INTO tb_aluno (nome, dt_nascimento, cpf, ativo) VALUES('Mateus Henrique Fogaça', '11/04/2010', '00549363304', true);
INSERT INTO tb_aluno (nome, dt_nascimento, cpf, ativo) VALUES('Paulo Matheus Rodrigues', '03/11/2010', '04285468700', true);
INSERT INTO tb_aluno (nome, dt_nascimento, cpf, ativo) VALUES('Geraldo Cauã Novaes', '01/10/2012', '24907463758', true);
INSERT INTO tb_aluno (nome, dt_nascimento, cpf, ativo) VALUES('Breno Martin Dias', '20/02/2011', '00530260709', true);
INSERT INTO tb_aluno (nome, dt_nascimento, cpf, ativo) VALUES('Bento Ricardo Bernardes', '11/11/2011', '35586094762', true);


commit;
rollback;