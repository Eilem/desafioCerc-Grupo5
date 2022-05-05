package br.com.cerc.holerite.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.cerc.holerite.persistence.dto.FolhaDePagamentoDTO;
import br.com.cerc.holerite.persistence.model.Adicionais;
import br.com.cerc.holerite.persistence.model.Cargo;
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
		
//		validarIntegridadeDosDados(dto, funcionario.get());
		
		FolhaDePagamento folha = criarFolhaDePagamento(dto, funcionario.get());
		return folhaDePagamentoRepository.save(folha);
	}
	
	public void delete(long id) {
		folhaDePagamentoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
		folhaDePagamentoRepository.deleteById(id);
	}
	
	public void replace(FolhaDePagamentoDTO dto, long id) {
		findById(id);
		Optional<Funcionario> funcionario = funcionarioService.findById(dto.getFuncId());
		
		//validarIntegridadeDosDados(dto, funcionario.get());
		
		FolhaDePagamento folha = criarFolhaDePagamento(dto, funcionario.get());
		
		folha.setId(id);
		folhaDePagamentoRepository.save(folha);
	}
	
	private FolhaDePagamento criarFolhaDePagamento(FolhaDePagamentoDTO dto, Funcionario funcionario) {
		double salarioBruto = Adicionais.calcularSalarioBruto(funcionario.getCargo().getPagamentoHora(), dto.getHorasTrabalhadas());
		double inss = Descontos.calcularINSS(salarioBruto);
		double irrf = Descontos.calcularIRRF(salarioBruto, inss);
		double fgts = Adicionais.calcularFGTS(salarioBruto);
		String dataEmissao  = LocalDate.now().toString();
		String mesRefencia = dto.getAnoReferencia() + "-" + dto.getMesReferencia();
		double salarioLiquido = salarioBruto - inss - irrf;
			
		FolhaDePagamento folha = new FolhaDePagamento(funcionario, round(inss, 2), round(irrf, 2), round(fgts, 2), dataEmissao, mesRefencia, round(salarioBruto, 2), round(salarioLiquido, 2));
	
		return folha;
	}
	
//	private void validarIntegridadeDosDados(FolhaDePagamentoDTO dto, Funcionario funcionario) {
//		FolhaDePagamento folhaDB = folhaDePagamentoRepository
//				.findByFuncionarioAndMesReferencia(funcionario, dto.getAnoReferencia() + "-" + dto.getMesReferencia());
//
//		if(folhaDB != null) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//		}
//
//		if(dto.getMesReferencia() > 12 || dto.getMesReferencia() < 1 || dto.getAnoReferencia() < 1) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//		}
//	}
	
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
}
