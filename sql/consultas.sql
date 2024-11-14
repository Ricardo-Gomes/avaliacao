--- 01. Crie uma consulta que retorne todos os alunos cujos CPF's começem com "005".

select *
from tb_aluno ta
where ta.cpf
like '005%';


--- 02. Crie uma consulta que retorne todos os alunos com nomes repetidos

SELECT ta.nome, Count(*) qtd FROM tb_aluno ta
GROUP BY ta.nome
HAVING Count(*) > 1


--- 03. Crie uma consulta que retorne o ano de nascimento e a quantidade de alunos por ano, ordenado por ano ex:

--  ano  | qtd
--  2001 | 10
--  2002 | 15
--  2003 | 05


select extract(year from a.dt_nascimento) as ano,
       count(a) as quantidade
from tb_aluno a
group by extract(year from a.dt_nascimento)
order by ano;


--- 04. Crie uma consulta que retorne o menor id dentre os alunos ativos.

select MIN(ta.id)
from tb_aluno ta
where ta.ativo = true;