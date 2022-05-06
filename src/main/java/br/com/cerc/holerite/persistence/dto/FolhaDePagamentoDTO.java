package br.com.cerc.holerite.persistence.dto;

import javax.validation.constraints.NotNull;

public class FolhaDePagamentoDTO {
//	@NotNull(message = "O campo 'func_id' nao pode estar vazio")
	private long funcId;
//	@NotNull(message = "O campo 'horasTrabalhadas' nao pode estar vazio")
	private long horasTrabalhadas;
//	@NotNull(message = "O campo 'mesReferencia' nao pode estar vazio")
	private int mesReferencia;
//	@NotNull(message = "O campo 'anoReferencia' nao pode estar vazio")
	private int anoReferencia;


	
	public long getFuncId() {
		return funcId;
	}
	public void setFuncId(long funcId) {
		this.funcId = funcId;
	}
	public long getHorasTrabalhadas() {
		return horasTrabalhadas;
	}
	public void setHorasTrabalhadas(long horasTrabalhadas) {
		this.horasTrabalhadas = horasTrabalhadas;
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
	public void setAnoReferencia(int anoReferencia) {
		this.anoReferencia = anoReferencia;
	}

	
}
