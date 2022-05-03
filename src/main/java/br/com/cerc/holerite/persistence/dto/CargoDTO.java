package br.com.cerc.holerite.persistence.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CargoDTO {
	@NotBlank(message = "O nome do cargo é obrigatório. ")
	private String nome;

	@NotNull(message = "O pagamento Hora é obrigatório. ")
	private double pagamentoHora;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getPagamentoHora() {
		return pagamentoHora;
	}
	public void setPagamentoHora(double pagamentoHora) {
		this.pagamentoHora = pagamentoHora;
	}
	
}
