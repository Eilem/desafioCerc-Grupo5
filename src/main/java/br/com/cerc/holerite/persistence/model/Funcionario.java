package br.com.cerc.holerite.persistence.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "funcionarios")
public class Funcionario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	private long id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	//@Size(min = 11, max = 11)
	private String cpf;

	@ManyToOne
	@JoinColumn(name = "cargo_id")
	@JsonIgnoreProperties("funcionario")
	private Cargo cargo;

	@OneToMany(mappedBy = "funcionario")
	@JsonIgnoreProperties("funcionario")
	@ApiModelProperty(hidden = true)
	private List<FolhaDePagamento> folhaDePagamento = new ArrayList<>();

	public List<FolhaDePagamento> getFolhaDePagamento() {
		return folhaDePagamento;
	}

	public void setFolhaDePagamento(List<FolhaDePagamento> folhaDePagamento) {
		this.folhaDePagamento = folhaDePagamento;
	}

	public Funcionario() {
		
	}

	public Funcionario(String nome, String cpf, Cargo cargo) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.cargo = cargo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
		public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funcionario other = (Funcionario) obj;
		return id == other.id;
	}
	
}
