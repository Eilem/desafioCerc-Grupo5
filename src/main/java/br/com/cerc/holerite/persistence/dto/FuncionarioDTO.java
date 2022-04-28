package br.com.cerc.holerite.persistence.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FuncionarioDTO {
	@NotBlank(message = "O campo 'nome' nao pode estar vazio")
	private String nome;
	@NotBlank(message = "O campo 'cpf' nao pode estar vazio")
	private String cpf;
	@NotNull(message = "O campo 'cargo_id' nao pode estar vazio")
	private long cargo_id;
	
	public FuncionarioDTO() {
		
	}
	
	public FuncionarioDTO(String nome, String cpf, long cargo_id) {
		this.nome = nome;
		this.cpf = cpf;
		this.cargo_id = cargo_id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public long getCargo_id() {
		return cargo_id;
	}

	public void setCargo_id(long cargo_id) {
		this.cargo_id = cargo_id;
	}

	
}
