package br.com.cerc.holerite.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.cerc.holerite.persistence.dto.CargoDTO;
import br.com.cerc.holerite.persistence.model.Cargo;
import br.com.cerc.holerite.persistence.model.Funcionario;
import br.com.cerc.holerite.persistence.repository.CargoRepository;
import br.com.cerc.holerite.persistence.repository.FuncionarioRepository;

@Service
public class CargoService {
	private final CargoRepository cargoRepository;
	private final FuncionarioRepository funcionarioRepository;
	
	public CargoService(CargoRepository cargoRepository, FuncionarioRepository funcionarioRepository) {
		this.cargoRepository = cargoRepository;
		this.funcionarioRepository = funcionarioRepository;
	}
	
	public Cargo findById(long id) {
		return cargoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
	}
	
	public Page<Cargo> listAll(Pageable pageable) {
		 return cargoRepository.findAll(pageable);
	}
	
	public Cargo save(CargoDTO dto) {
		Cargo cargoDB = cargoRepository.findByNome(dto.getNome());
		
		if(cargoDB != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		Cargo cargo  = new Cargo(dto.getNome(), dto.getPagamentoHora());
		return cargoRepository.save(cargo);
	}
	
	public void delete(long id) {
		Cargo cargo = findById(id);
		
		List<Funcionario> funcionarios = funcionarioRepository.findAllByCargo(cargo);
		
		funcionarios.stream().forEach(funcionario -> funcionario.setCargo(null));
		
		for(Funcionario funcionario : funcionarios) {
			funcionarioRepository.save(funcionario);
		}
		
		cargoRepository.deleteById(id);
	}
	
	public void replace(CargoDTO dto, long id) {
		findById(id);
		
		Cargo cargo = new Cargo();
		
		cargo.setId(id);
		cargo.setNome(dto.getNome());
		cargo.setPagamentoHora(dto.getPagamentoHora());
		
		cargoRepository.save(cargo);
	}
}
