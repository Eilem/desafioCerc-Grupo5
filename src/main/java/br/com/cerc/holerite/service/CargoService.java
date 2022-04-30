package br.com.cerc.holerite.service;

import java.util.Optional;

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

@Service
public class CargoService {
	private final CargoRepository cargoRepository;
	
	public CargoService(CargoRepository cargoRepository) {
		this.cargoRepository = cargoRepository;
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
		findById(id);
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
