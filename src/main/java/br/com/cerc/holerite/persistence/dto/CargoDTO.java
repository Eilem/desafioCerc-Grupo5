package br.com.cerc.holerite.persistence.dto;

public class CargoDTO {
	private String nome;
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
