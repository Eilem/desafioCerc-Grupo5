package br.com.cerc.holerite.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.cerc.holerite.persistence.dto.FuncionarioDTO;
import br.com.cerc.holerite.persistence.model.Cargo;
import br.com.cerc.holerite.persistence.model.Funcionario;
import br.com.cerc.holerite.persistence.repository.CargoRepository;
import br.com.cerc.holerite.persistence.repository.FuncionarioRepository;

@Service
public class FuncionarioService {
	private final FuncionarioRepository funcionarioRepository;
	private final CargoService cargoService;
	
	public FuncionarioService(FuncionarioRepository funcionarioRepository, CargoService cargoService) {
		this.funcionarioRepository = funcionarioRepository;
		this.cargoService = cargoService;
	}
	
	public Funcionario findById(long id) {
		return funcionarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Esse funcionario nao esta cadastrado no banco de dados"));
	}
	
	public Page<Funcionario> listAll(Pageable pageable) {
		return funcionarioRepository.findAll(pageable);
	}
	
	public Funcionario save(FuncionarioDTO dto) {
		funcionarioRepository.findByCpf(dto.getCpf()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
		Cargo cargo = cargoService.findById(dto.getCargo_id());
		return new Funcionario(dto.getNome(), dto.getCpf(), cargo);
	}
	
	public void delete(long id) {
		findById(id);
		funcionarioRepository.deleteById(id);	
	}
	
	public void replace(FuncionarioDTO dto) {
		findById(dto.getId());
		Cargo cargo = cargoService.findById(dto.getCargo_id());
		funcionarioRepository.deleteById(dto.getId());
		Funcionario funcionario = new Funcionario(dto.getNome(), dto.getCpf(), cargo);
		funcionarioRepository.save(funcionario);
	}
	
		
	
}
