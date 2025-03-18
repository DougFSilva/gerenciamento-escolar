package com.dougfsilva.formativa_gerenciamento_escolar.dto;

import com.dougfsilva.formativa_gerenciamento_escolar.model.Curso;
import com.dougfsilva.formativa_gerenciamento_escolar.model.Modalidade;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class CursoResponse {

	private Long id;
	private String areaTecnologica;
	private Modalidade modalidade;
	private String titulo;
	private String imagemUrl;
	
	public CursoResponse(Curso curso, String baseUrl) {
		this.id = curso.getId();
		this.areaTecnologica = curso.getAreaTecnologica();
		this.modalidade = curso.getModalidade();
		this.titulo = curso.getTitulo();
		this.imagemUrl = baseUrl + curso.getImagem();
	}
	
	public static CursoResponse paraDto(Curso curso, String baseUrl) {
		return new CursoResponse(curso, baseUrl);
	}

}
