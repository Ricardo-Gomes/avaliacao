package br.gov.ce.seduc.avaliacao.api;

import br.gov.ce.seduc.avaliacao.exception.AlunoException;
import br.gov.ce.seduc.avaliacao.domain.Aluno;
import br.gov.ce.seduc.avaliacao.dto.AlunoDTO;
import br.gov.ce.seduc.avaliacao.mapper.AlunoMapper;
import br.gov.ce.seduc.avaliacao.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/alunos")
public class AlunoController {

    private final AlunoService service;
    private final AlunoMapper mapper;

    @PostMapping
    ResponseEntity<Aluno> cadastrar(@RequestBody @Valid AlunoDTO dto){
        try{
            Aluno entity = service.salvar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(entity);
        } catch (AlunoException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao Salvar o Aluno !");
        }
    }

    @GetMapping("/pesquisa")
    ResponseEntity<List<AlunoDTO>> pesquisar(@RequestParam(required = false) String nome, @RequestParam(required = false) String cpf){

        List<Aluno> alunos = service.obterPorNomeOuCpf(nome, cpf);

        if (alunos.isEmpty()) return ResponseEntity.notFound().build();

        List<AlunoDTO> dtos = alunos.stream().map(mapper::toDTO).toList();

        return ResponseEntity.ok(dtos);

    }

    @DeleteMapping("/{cpf}/deletar")
    ResponseEntity<Void> deletar(@PathVariable String cpf){
        try{
            service.deletar(cpf);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao Tentar Excluir o Aluno", e);
        }
    }

    @GetMapping("{id}")
    ResponseEntity<AlunoDTO> buscarPorId(@PathVariable("id") Long id){

        if (id == null || id <= 0) { return ResponseEntity.badRequest().build(); }

        return service.obterPorId(id).map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PutMapping("{id}")
    ResponseEntity<Aluno> atualizar(@PathVariable Long id, @RequestBody @Valid AlunoDTO dto){

        if (id == null) { throw new IllegalArgumentException("ID do Aluno não pode ser nulo."); }

        try{
            Aluno entity = service.atualizar(id, dto);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(entity);
        } catch (AlunoException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao Atualizar o Aluno", e);
        }
    }

    @PatchMapping("/status/{id}")
    ResponseEntity<Aluno> ativarOuInativar(@PathVariable Long id, @RequestBody @Valid AlunoDTO dto){
        try {
            Aluno entity = service.atualizarStatus(id,dto);
            return ResponseEntity.status(HttpStatus.OK).body(entity);
        }catch (AlunoException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao Ativar/Desativar o Aluno !");
        }
    }

    @GetMapping("cpf005")
    ResponseEntity<List<AlunoDTO>> obterPorCPF005(){

        List<Aluno> alunos = service.obterPorCPF005();

        if (alunos.isEmpty()) return ResponseEntity.notFound().build();

        List<AlunoDTO> dtos = alunos.stream().map(mapper::toDTO).toList();

        return ResponseEntity.ok(dtos);

    }

    @GetMapping("nomes")
    ResponseEntity<List<AlunoDTO>> obterNomesReptidos(){

        List<Aluno> alunos = service.obterNomesReptidos();

        if (alunos.isEmpty()) return ResponseEntity.notFound().build();

        List<AlunoDTO> dtos = alunos.stream().map(mapper::toDTO).toList();

        return ResponseEntity.ok(dtos);

    }

    @GetMapping("ativos")
    ResponseEntity<Long> obterPorAlunosAtivos(){

        Long aluno = service.obterPorAlunosAtivos();

        if (aluno == null) { return ResponseEntity.notFound().build(); }

        return ResponseEntity.ok(aluno);

    }

    @GetMapping("qtd-alunos-ano")
    public ResponseEntity<List<Map<String, Object>>> obterQtdAlunosPorAno() {
        List<Map<String, Object>> dados = service.obterQtdAlunosPorAno();

        if (dados.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dados);
    }

}
