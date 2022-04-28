package br.com.cerc.holerite.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
	
	public FolhaDePagamento findById(Long id) {
		return folhaDePagamentoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
	}
	
	public Page<FolhaDePagamento> listAll(Pageable pageable) {
		return folhaDePagamentoRepository.findAll(pageable);
	}
	
	public List<FolhaDePagamento> listAllByFunc(long id) {
		Funcionario funcionario = funcionarioService.findById(id);
		return folhaDePagamentoRepository.findAllByFuncionario(funcionario);
	}
	
	public FolhaDePagamento save(FolhaDePagamentoDTO dto) {
		Funcionario funcionario = funcionarioService.findById(dto.getFuncId());
		FolhaDePagamento folha = criarFolhaDePagamento(dto, funcionario);
		return folhaDePagamentoRepository.save(folha);
	}
	
	public void delete(long id) {
		folhaDePagamentoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
		folhaDePagamentoRepository.deleteById(id);
	}
	
	public void replace(FolhaDePagamentoDTO dto, long id) {
		delete(id);
		save(dto);
	}
	
	private FolhaDePagamento criarFolhaDePagamento(FolhaDePagamentoDTO dto, Funcionario funcionario) {
		double salarioBruto = Adicionais.calcularSalarioBruto(funcionario.getCargo().getPagamentoHora(), dto.getHorasTrabalhadas());
		double inss = Descontos.calcularINSS(salarioBruto);
		double irrf = Descontos.calcularIRRF(salarioBruto, inss);
		double fgts = Adicionais.calcularFGTS(salarioBruto);
		String dataEmissao  = LocalDate.now().toString();
		String mesRefencia = dto.getMesReferencia();
		double salarioLiquido = salarioBruto - inss - irrf;
			
		FolhaDePagamento folha = new FolhaDePagamento(funcionario, inss, irrf, fgts, dataEmissao, mesRefencia, salarioBruto, salarioLiquido);
	
		return folha;
	}
}
