package com.dougfsilva.formativa_gerenciamento_escolar.model;

import java.util.Arrays;

import com.dougfsilva.formativa_gerenciamento_escolar.exception.ModalidadeInvalidaException;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Modalidade {

	PRESENCIAL, SEMI_PRESENCIAL, EAD;
	
	public static Modalidade paraEnum(String valor) {
		return Arrays.asList(values())
				.stream()
				.filter(m -> m.name().equalsIgnoreCase(valor))
				.findFirst()
				.orElseThrow(() -> new ModalidadeInvalidaException("Valor inválido para a modalidade: " + valor));
	}
}
