package br.gov.ce.seduc.avaliacao.dto;

import java.time.LocalDate;

/**
 * @author Ricardo Oliveira
 * @email rikardos38@gmail.com
 * @date 13/11/2024
 */


public record AlunoDTO(Long id,
                       String nome,
                       LocalDate dataNascimento,
                       String cpf,
                       Boolean ativo) {}
