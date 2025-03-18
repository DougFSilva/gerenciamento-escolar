package com.dougfsilva.formativa_gerenciamento_escolar.dto;

import java.util.Optional;

public record EditaCursoForm(
		
		Optional<String> areaTecnologica,

		Optional<String> modalidade,

		Optional<String> titulo) {

}



