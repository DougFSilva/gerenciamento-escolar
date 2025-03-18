package com.dougfsilva.formativa_gerenciamento_escolar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dougfsilva.formativa_gerenciamento_escolar.exception.ObjetoNaoEncontradoException;
import com.dougfsilva.formativa_gerenciamento_escolar.model.Curso;
import com.dougfsilva.formativa_gerenciamento_escolar.model.Modalidade;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

	default Curso findByIdOrElseThrow(Long id) {
		return findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException(
				String.format("Curso com id %d não encontrado", id)));
	}
	
	Page<Curso> findByAreaTecnologica(String areaTecnologica, Pageable paginacao);
	
	Page<Curso> findByModalidade(Modalidade modalidade, Pageable paginacao);
	
	boolean existsByTitulo(String titulo);
}
