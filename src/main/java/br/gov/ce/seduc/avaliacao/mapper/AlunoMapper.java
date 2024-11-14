package br.gov.ce.seduc.avaliacao.mapper;

import br.gov.ce.seduc.avaliacao.domain.Aluno;
import br.gov.ce.seduc.avaliacao.dto.AlunoDTO;
import org.mapstruct.Mapper;

/**
 * @author Ricardo Oliveira
 * @email rikardos38@gmail.com
 * @date 13/11/2024
 */

@Mapper(componentModel = "spring")
public interface AlunoMapper {

    Aluno toEntity (AlunoDTO dto);

    AlunoDTO toDTO (Aluno entity);
}
