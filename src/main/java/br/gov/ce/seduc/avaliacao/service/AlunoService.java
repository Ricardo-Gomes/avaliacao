package br.gov.ce.seduc.avaliacao.service;

import br.gov.ce.seduc.avaliacao.exception.AlunoException;
import br.gov.ce.seduc.avaliacao.domain.Aluno;
import br.gov.ce.seduc.avaliacao.dto.AlunoDTO;
import br.gov.ce.seduc.avaliacao.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

/**
 * @author Ricardo Oliveira
 * @email rikardos38@gmail.com
 * @date 13/11/2024
 */

@Service
@RequiredArgsConstructor
public class AlunoService {
    private final AlunoRepository repository;

    @Transactional(readOnly = true)
    public Optional<Aluno> obterPorId(Long id){ return repository.findById(id); }

    @Transactional(readOnly = true)
    public List<Aluno> obterPorNomeOuCpf(String nome, String cpf){
        return repository.obterPorNomeOuCpf("%" + nome + "%", cpf);
    }

    @Transactional(readOnly = true)
    public List<Aluno> obterPorCPF005(){
        return repository.obterPorCPF005();
    }

    @Transactional(readOnly = true)
    public List<Aluno> obterNomesReptidos(){
        return repository.obterNomesReptidos();
    }

    @Transactional(readOnly = true)
    public Long obterPorAlunosAtivos(){
        return repository.obterPorAlunosAtivos();
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> obterQtdAlunosPorAno() {
        List<Object[]> resultados = repository.obterQtdAlunosPorAno();

        List<Map<String, Object>> resposta = new ArrayList<>();

        for (Object[] resultado : resultados) {
            Map<String, Object> map = new HashMap<>();
            map.put("ano", resultado[0]);
            map.put("quantidade", resultado[1]);
            resposta.add(map);
        }

        return resposta;
    }


    @Transactional
    public void deletar (String cpf) {

        Aluno a = repository.obterPorCpf(cpf);

        if (a == null) { throw new AlunoException("Aluno com CPF: " + cpf + "não encontrado");}

        repository.delete(a);
    }

    @Transactional
    public Aluno salvar (AlunoDTO dto){

        Aluno a = new Aluno();

        a.setCpf(dto.cpf());
        a.setNome(dto.nome());
        a.setDataNascimento(dto.dataNascimento());
        a.setAtivo(dto.ativo());

        return repository.save(a);
    }


    @Transactional
    public Aluno atualizar(Long id, AlunoDTO dto){

        Aluno aluno = repository.findById(id).orElseThrow( () -> new AlunoException("Aluno não encontrado"));

        return repository.findById(id).map( a -> {

            if (dto.cpf() != null && !dto.cpf().equals(aluno.getCpf())) {
                boolean exists = repository.existsByCpf(dto.cpf());
                if (exists) { throw new AlunoException("Aluno já cadastrado com o CPF informado: " + dto.cpf());  }
            }

            a.setCpf(dto.cpf());
            a.setNome(dto.nome());
            a.setDataNascimento(dto.dataNascimento());
            a.setAtivo(dto.ativo());

            return repository.save(a);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Aluno atualizarStatus(Long id, AlunoDTO dto){

        Aluno aluno = repository.findById(id).orElseThrow( () -> new AlunoException("Aluno não encontrado"));

        return repository.findById(id).map( a -> {

            a.setAtivo(dto.ativo());

            return repository.save(a);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
