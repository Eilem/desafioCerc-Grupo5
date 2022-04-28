package br.com.cerc.holerite.service.util;

public class Descontos {
	private static double inssValorDevido1 = 82.50;
	private static double inssValorDevido2 = 99.31;
	private static double inssValorDevido3 = 132.21;
	private static double inssValorTeto = 751.99; 
	
	private static double irrfValorDevido1 = 0;
	private static double irrfValorDevido2 = 142.8;
	private static double irrfValorDevido3 = 354.8;
	private static double irrfValorDevido4 = 636.13;
	private static double irrfValorDevido5 = 869.36;
			
	public static double calcularINSS(double salario) {
		if(salario <= 1100) {
			return (salario * 7.5) / 100;
			
		} else if (salario <= 2203.48){
			salario = salario - 1100;
			return ((salario * 9) / 100) + inssValorDevido1;
		
		} else if (salario <= 3305.22) {
			salario = salario - 2203.48;
			return ((salario * 12) / 100) + inssValorDevido1 + inssValorDevido2;
		
		} else if (salario <= 6433.57) {
			salario = salario - 3305.22;
			return ((salario * 14) / 100) + inssValorDevido1 + inssValorDevido2 + inssValorDevido3;
			
		} else {
			return inssValorTeto;
		}
	
	}
	
	public static double calcularIRRF(double salario, double INSS) {
		salario = salario - INSS;
		
		if(salario <= 1903.98) {
			return irrfValorDevido1;
			
		} else if(salario <= 2826.65) {
			return ((salario * 7.5) / 100) - irrfValorDevido2;
			
		} else if(salario <= 3751.05) {
			return ((salario * 15) / 100) - irrfValorDevido3;
			
		} else if(salario <= 4664.68) {
			return ((salario * 22.5) / 100) - irrfValorDevido4;
			
		} else {
			return ((salario * 27.5) / 100) - irrfValorDevido5;
		}
	}
	
}
