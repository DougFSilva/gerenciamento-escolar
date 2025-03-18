package com.dougfsilva.formativa_gerenciamento_escolar.dto;

import jakarta.validation.constraints.NotBlank;

public record CriaCursoForm(
		
		@NotBlank(message = "O campo area tecnológica não pode ser vazio")
		String areaTecnologica,
		
		@NotBlank(message = "O campo modalidade não pode ser vazio")
		String modalidade,
		
		@NotBlank(message = "O campo título não pode ser nulo")
		String titulo) {

}
