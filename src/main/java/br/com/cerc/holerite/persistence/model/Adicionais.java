package br.com.cerc.holerite.persistence.model;

public class Adicionais {
	
	public static double calcularFGTS(double salarioBruto) {
		return (salarioBruto * 8) / 100;
	}
	
	public static double calcularSalarioBruto(double pagamentoHora, double horasTrabalhadas) {
		return pagamentoHora * horasTrabalhadas;
	}
}
