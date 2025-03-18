package com.dougfsilva.formativa_gerenciamento_escolar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "enderecos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 50)
    private String pais;

    @Column(nullable = false, length = 50)
    private String estado;

    @Column(nullable = false, length = 50)
    private String cidade;

    @Column(nullable = false, length = 50)
    private String bairro;

    @Column(nullable = false, length = 10)
    private String cep;

    @Column(nullable = false, length = 100)
    private String rua;

    @Column(nullable = false, length = 10)
    private String numero;

	public Endereco(String pais, String estado, String cidade, String bairro, String cep, String rua, String numero) {
		this.pais = pais;
		this.estado = estado;
		this.cidade = cidade;
		this.bairro = bairro;
		this.cep = cep;
		this.rua = rua;
		this.numero = numero;
	}
    
}
