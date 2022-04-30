package br.com.cerc.holerite.persistence.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FolhaDePagamentoDTO {
	@NotNull(message = "O campo 'func_id' nao pode estar vazio")
	private long funcId;
	@NotNull(message = "O campo 'horasTrabalhadas' nao pode estar vazio")
	private long horasTrabalhadas;
	@NotBlank(message = "O campo 'mesReferencia' nao pode estar vazio")
	private String mesReferencia;

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

	public String getMesReferencia() {
		return mesReferencia;
	}

	public void setMesReferencia(String mesReferencia) {
		this.mesReferencia = mesReferencia;
	}
}
