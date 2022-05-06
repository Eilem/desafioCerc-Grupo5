package br.com.cerc.holerite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cerc.holerite.persistence.model.FolhaDePagamento;
import br.com.cerc.holerite.persistence.model.Funcionario;

public interface FolhaDePagamentoRepository extends JpaRepository<FolhaDePagamento, Long>{
	List<FolhaDePagamento> findAllByFuncionario(Funcionario funcionario);
	
//	FolhaDePagamento findByFuncionarioMesAnoReferencia(Funcionario funcionario, int mesReferencia, int anoReferencia);


}
