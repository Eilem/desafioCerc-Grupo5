package br.com.cerc.holerite.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.cerc.holerite.persistence.model.Cargo;
import br.com.cerc.holerite.persistence.model.Funcionario;
import br.com.cerc.holerite.persistence.repository.CargoRepository;
import br.com.cerc.holerite.persistence.repository.FuncionarioRepository;

@DataJpaTest
public class FuncionarioRepositoryTest {
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private CargoRepository cargoRepository;
		
	private Cargo createCargo() {
		return new Cargo("Gerente", 20);
	}
	
	private Funcionario createFuncionario() {
		return new Funcionario("Marcus", "11122233344", createCargo());
	}
	
	
	@Test
	void save_PersistFuncionario_WhenSuccessful() {
		Funcionario funcionarioToBeSaved = createFuncionario();
		
		Funcionario funcionarioSaved = this.funcionarioRepository.save(funcionarioToBeSaved);
		
		Assertions.assertThat(funcionarioSaved).isNotNull();
		
		Assertions.assertThat(funcionarioSaved.getId()).isNotNull();
		
		Assertions.assertThat(funcionarioSaved.getCpf()).isEqualTo(funcionarioSaved.getCpf());
	
		Assertions.assertThat(funcionarioSaved.getNome()).isEqualTo(funcionarioSaved.getNome());
		
		Assertions.assertThat(funcionarioSaved.getCargo()).isEqualTo(funcionarioSaved.getCargo());
	}
	
	@Test
	void update_UpdateFuncionario_WhenSuccesful() {
		Funcionario funcionarioToBeSaved = createFuncionario();
		
		Funcionario funcionarioSaved = this.funcionarioRepository.save(funcionarioToBeSaved);
		
		funcionarioSaved.setNome("Elisangela");
		
		Funcionario funcionarioUpdated = this.funcionarioRepository.save(funcionarioSaved);
			
		Assertions.assertThat(funcionarioUpdated).isNotNull();
		
		Assertions.assertThat(funcionarioUpdated.getId()).isNotNull();
		
		Assertions.assertThat(funcionarioUpdated.getCpf()).isEqualTo(funcionarioSaved.getCpf());
	
		Assertions.assertThat(funcionarioUpdated.getNome()).isEqualTo(funcionarioSaved.getNome());
		
		Assertions.assertThat(funcionarioUpdated.getCargo()).isEqualTo(funcionarioSaved.getCargo());
		
	}
	
	@Test
	void delete_RemovesCargo_WhenSuccesful() {
		Funcionario funcionarioToBeSaved = createFuncionario();
		
		Funcionario funcionarioSaved = this.funcionarioRepository.save(funcionarioToBeSaved);
		
		this.funcionarioRepository.delete(funcionarioSaved);
		
		Optional<Funcionario> funcionarioOptional = this.funcionarioRepository.findById(funcionarioToBeSaved.getId());	
		
		Assertions.assertThat(funcionarioOptional).isEmpty();
	}
	
	@Test
	void findAllByCargo_ReturnsListOfCargo_WhenSuccessful() {
		Funcionario funcionarioToBeSaved = createFuncionario();
		
		Cargo cargo = cargoRepository.save(createCargo());
		
		funcionarioToBeSaved.setCargo(cargo);
		
		Funcionario funcionarioSaved = this.funcionarioRepository.save(funcionarioToBeSaved);
		
		List<Funcionario> funcionarios = this.funcionarioRepository.findAllByCargo(cargo);
		
		Assertions.assertThat(funcionarios)
				.isNotEmpty()
				.contains(funcionarioSaved);
	}
	
	@Test
	void findById_ReturnsFuncionario_WhenSuccessful() {
		Funcionario funcionarioToBeSaved = createFuncionario();
		
		Funcionario funcionarioSaved = this.funcionarioRepository.save(funcionarioToBeSaved);
		
		Optional<Funcionario> funcionarioOptional = this.funcionarioRepository.findById(funcionarioSaved.getId());
		
		Assertions.assertThat(funcionarioOptional).isNotEmpty();
		
		Assertions.assertThat(funcionarioOptional.get()).isEqualTo(funcionarioSaved);
	}
	
	@Test
	void findByCpf_ReturnsFuncionario_WhenSuccessful() {
		Funcionario funcionarioToBeSaved = createFuncionario();
		
		Cargo cargo = cargoRepository.save(createCargo());
		
		funcionarioToBeSaved.setCargo(cargo);
		
		Funcionario funcionarioSaved = this.funcionarioRepository.save(funcionarioToBeSaved);
		
		Funcionario funcionario = this.funcionarioRepository.findByCpf(funcionarioToBeSaved.getCpf());
		
		Assertions.assertThat(funcionario).isEqualTo(funcionarioSaved);
	}
}
