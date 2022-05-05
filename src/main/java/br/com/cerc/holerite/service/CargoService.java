package br.com.cerc.holerite.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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

	public Cargo findByNome(String nomeCargo) {
		return cargoRepository.findByNome( nomeCargo );
	}


	public Optional<Cargo> findById(long id) 
	{
		return cargoRepository.findById(id);
	}
	
	public Page<Cargo> listAll(Pageable pageable) {
		 return cargoRepository.findAll(pageable);
	}
	
	public Cargo save(CargoDTO dto) {
		//@todo ver pra retornar por id do que foi inserido
		Cargo cargoDB = cargoRepository.findByNome(dto.getNome());
		
		if(cargoDB != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		Cargo cargo  = new Cargo(dto.getNome(), dto.getPagamentoHora());
		return cargoRepository.save(cargo);
	}

	public Cargo getCargoByNome( String nome)
	{
		return cargoRepository.findByNome(nome);
	}

	@Transactional
	public void delete( Cargo cargo) 
	{
		List<Funcionario> funcionarios = funcionarioRepository.findAllByCargo(cargo);
		
		funcionarios.stream().forEach(funcionario -> funcionario.setCargo(null));
		
		for(Funcionario funcionario : funcionarios) {
			funcionarioRepository.save(funcionario);
		}
		
		cargoRepository.delete(cargo);
	}
	

	public Cargo replace(Cargo cargo , CargoDTO cargoDto) {
						
		cargo.setNome( cargoDto.getNome() );
		cargo.setPagamentoHora( cargoDto.getPagamentoHora() );

		return cargoRepository.save(cargo);
	}

}
