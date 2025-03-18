package com.dougfsilva.formativa_gerenciamento_escolar.utilidades;

public class NormalizaNomeImagem {

	public static String normalizar(String nome) {
	    return nome.toLowerCase() 
	               .replaceAll("\\s+", "_") 
	               .replaceAll("[^a-z0-9_.-]", ""); 
	}
}
