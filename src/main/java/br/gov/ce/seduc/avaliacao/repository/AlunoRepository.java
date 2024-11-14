package br.gov.ce.seduc.avaliacao.repository;

import br.gov.ce.seduc.avaliacao.domain.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    @Query("SELECT a FROM Aluno a WHERE a.cpf like :cpf ")
    Aluno obterPorCpf(@Param("cpf") String cpf);

    @Query("SELECT a FROM Aluno a WHERE a.cpf LIKE '%005%' ")
    List<Aluno> obterPorCPF005();

    @Query("SELECT a FROM Aluno a WHERE a.nome IN (SELECT a2.nome FROM Aluno a2 GROUP BY a2.nome HAVING COUNT(a2.nome) > 1)")
    List<Aluno> obterNomesReptidos();

    @Query("SELECT MIN(a.id) FROM Aluno a WHERE a.ativo = true")
    Long obterPorAlunosAtivos();

    boolean existsByCpf(String cpf);

    @Query("SELECT a FROM Aluno a WHERE a.nome LIKE :nome OR a.cpf LIKE :cpf")
    List<Aluno> obterPorNomeOuCpf(@Param("nome") String nome, @Param("cpf") String cpf);

    @Query("SELECT EXTRACT(YEAR FROM a.dataNascimento) AS ano, COUNT(a) AS quantidade FROM Aluno a GROUP BY EXTRACT(YEAR FROM a.dataNascimento) ORDER BY ano")
    List<Object[]> obterQtdAlunosPorAno();

}
