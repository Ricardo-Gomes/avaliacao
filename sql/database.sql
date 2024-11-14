create table aluno(
	id bigserial not null primary key,
	nome varchar (255),
    data_nascimento timestamp,
	cpf varchar(11) unique ,
	ativo boolean default true
);