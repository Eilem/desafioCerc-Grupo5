package br.com.cerc.holerite.service;

import java.util.Optional;

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

import javax.transaction.Transactional;

@Service
public class FuncionarioService {
	private final FuncionarioRepository funcionarioRepository;
	private final CargoRepository cargoRepository;
	
	public FuncionarioService(FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository) {
		this.funcionarioRepository = funcionarioRepository;
		this.cargoRepository = cargoRepository;
	}
	
	
	public Optional<Funcionario> findById(long id) {
		return funcionarioRepository.findById(id);
	}
	
	public Page<Funcionario> listAll(Pageable pageable) {
		return funcionarioRepository.findAll(pageable);
	}
	
	public Funcionario save(FuncionarioDTO dto) {
		Funcionario funcionarioDB = funcionarioRepository.findByCpf(dto.getCpf());
		Optional<Cargo> cargo = cargoRepository.findById(dto.getCargoId());
		
		if(funcionarioDB != null || !cargo.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		Funcionario funcionario  = new Funcionario(dto.getNome(), dto.getCpf().replaceAll(" ",""), cargo.get());
		return funcionarioRepository.save(funcionario);
	}

	public Funcionario getFuncionarioByCPF(String cpf) {
		return funcionarioRepository.findByCpf(cpf);
	}

	@Transactional
	public void delete(long id) {
		findById(id);
		funcionarioRepository.deleteById(id);
	}
	
	public void replace(FuncionarioDTO dto, long id) {
//		Funcionario funcionarioDB = findById(id);
//
//		Optional<Cargo> cargo = cargoRepository.findById(dto.getCargoId());
//
//		if(!funcionarioDB.getCpf().equals(dto.getCpf()) || !cargo.isPresent()) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//		}
//
//		Funcionario funcionario = new Funcionario();
//
//		funcionario.setId(id);
//		funcionario.setCargo(cargo.get());
//		funcionario.setCpf(dto.getCpf());
//		funcionario.setNome(dto.getNome());
//
//		funcionarioRepository.save(funcionario);
	}


}
