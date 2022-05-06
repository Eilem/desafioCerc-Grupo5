package br.com.cerc.holerite.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class FolhaDePagamento {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;

	@Column(nullable = false)
	private int mesReferencia;

	@Column(nullable = false)
	private int anoReferencia;

	@Column(nullable = false)
	private double INSS;

	@Column(nullable = false)
	private double IRRF;

	@Column(nullable = false)
	private double FGTS;

	@Column(nullable = false)
	private double salarioBruto;

	@Column(nullable = false)
	private double salarioLiquido;

	@Column(nullable = false)
	private double horasTrabalhadas;
	
	public FolhaDePagamento() {
		
	}
	
	public FolhaDePagamento(Funcionario funcionario, double iNSS, double iRRF, double fGTS,
							int mesReferencia, int anoReferencia, double salarioBruto, double salarioLiquido) {

		this.funcionario = funcionario;
		this.INSS = iNSS;
		this.IRRF = iRRF;
		this.FGTS = fGTS;
		this.mesReferencia = mesReferencia;
		this.anoReferencia = anoReferencia;
		this.salarioBruto = salarioBruto;
		this.salarioLiquido = salarioLiquido;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public double getINSS() {
		return INSS;
	}
	public void setINSS(double iNSS) {
		INSS = iNSS;
	}

	public double getIRRF() {
		return IRRF;
	}
	public void setIRRF(double iRRF) {
		IRRF = iRRF;
	}

	public double getFGTS() {
		return FGTS;
	}
	public void setFGTS(double fGTS) {
		FGTS = fGTS;
	}

	public int getMesReferencia() {
		return mesReferencia;
	}
	public void setMesReferencia(int mesReferencia) {
		this.mesReferencia = mesReferencia;
	}

	public int getAnoReferencia() {
		return anoReferencia;
	}
	public void setAnoReferencia(int ano) {
		this.anoReferencia = anoReferencia;
	}

	public double getSalarioBruto() {
		return salarioBruto;
	}
	public void setSalarioBruto(double salarioBruto) {
		this.salarioBruto = salarioBruto;
	}

	public double getSalarioLiquido() {
		return salarioLiquido;
	}
	public void setSalarioLiquido(double salarioLiquido) {
		this.salarioLiquido = salarioLiquido;
	}

	public double getHorasTrabalhadas() {
		return horasTrabalhadas;
	}

	public void setHorasTrabalhadas(double horasTrabalhadas) {
		this.horasTrabalhadas = horasTrabalhadas;
	}
}
