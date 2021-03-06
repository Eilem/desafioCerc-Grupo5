package br.com.cerc.holerite.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.cerc.holerite.persistence.dto.FolhaDePagamentoDTO;
import br.com.cerc.holerite.persistence.model.Adicionais;
import br.com.cerc.holerite.persistence.model.Descontos;
import br.com.cerc.holerite.persistence.model.FolhaDePagamento;
import br.com.cerc.holerite.persistence.model.Funcionario;
import br.com.cerc.holerite.persistence.repository.FolhaDePagamentoRepository;

@Service
public class FolhaDePagamentoService {
	private final FuncionarioService funcionarioService;
	private final FolhaDePagamentoRepository folhaDePagamentoRepository;

	public FolhaDePagamentoService(FuncionarioService funcionarioService, FolhaDePagamentoRepository folhaDePagamentoRepository) {
		this.funcionarioService = funcionarioService;
		this.folhaDePagamentoRepository = folhaDePagamentoRepository;
	}
	
	public Optional<FolhaDePagamento> findById(long id) {
		return folhaDePagamentoRepository.findById(id);
		
	}
	
	public Page<FolhaDePagamento> listAll(Pageable pageable) {
		return folhaDePagamentoRepository.findAll(pageable);
	}
	
	public List<FolhaDePagamento> listAllByFunc(long id) {
		Optional<Funcionario> funcionario = funcionarioService.findById(id);

		return folhaDePagamentoRepository.findAllByFuncionario(funcionario.get());
	}

	public FolhaDePagamento save(FolhaDePagamentoDTO dto) {
		Optional<Funcionario> funcionario = funcionarioService.findById(dto.getFuncId());

		FolhaDePagamento folha = criarFolhaDePagamento(dto, funcionario.get());
		return folhaDePagamentoRepository.save(folha);
	}
	
//	public void delete(long id) {
//		folhaDePagamentoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
//		folhaDePagamentoRepository.deleteById(id);
//	}
	
//	public void replace(FolhaDePagamentoDTO dto, long id) {
//		findById(id);
//		Optional<Funcionario> funcionario = funcionarioService.findById(dto.getFuncId());
//
//		FolhaDePagamento folha = criarFolhaDePagamento(dto, funcionario.get());
//
//		folha.setId(id);
//		folhaDePagamentoRepository.save(folha);
//	}
	
	private FolhaDePagamento criarFolhaDePagamento(FolhaDePagamentoDTO dto, Funcionario funcionario) {
		double salarioBruto = Adicionais.calcularSalarioBruto(funcionario.getCargo().getPagamentoHora(), dto.getHorasTrabalhadas());
		double inss = Descontos.calcularINSS(salarioBruto);
		double irrf = Descontos.calcularIRRF(salarioBruto, inss);
		double fgts = Adicionais.calcularFGTS(salarioBruto);
		int mesReferencia = dto.getMesReferencia();
		int anoReferencia = dto.getAnoReferencia();
		double horasTrabalhadas = dto.getHorasTrabalhadas();
		double salarioLiquido = salarioBruto - inss - irrf;
			
		FolhaDePagamento folha = new FolhaDePagamento(funcionario, round(inss, 2),
				round(irrf, 2), round(fgts, 2), mesReferencia, anoReferencia,
				round(salarioBruto, 2), round(salarioLiquido, 2), round(horasTrabalhadas, 2));
	
		return folha;
	}

	
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
}
