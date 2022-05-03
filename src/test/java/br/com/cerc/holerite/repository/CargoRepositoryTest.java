package br.com.cerc.holerite.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import br.com.cerc.holerite.persistence.model.Cargo;
import br.com.cerc.holerite.persistence.repository.CargoRepository;

@DataJpaTest
public class CargoRepositoryTest {
	@Autowired
	private CargoRepository cargoRepository;
		
	private Cargo createCargo() {
		return new Cargo("Gerente", 20);
	}
	
	
	
	@Test
	void save_PersistCargo_WhenSuccessful() {
		Cargo cargoToBeSaved = createCargo();
		
		Cargo cargoSaved = this.cargoRepository.save(cargoToBeSaved);
		
		Assertions.assertThat(cargoSaved).isNotNull();
		
		Assertions.assertThat(cargoSaved.getId()).isNotNull();
		
		Assertions.assertThat(cargoSaved.getNome()).isEqualTo(cargoToBeSaved.getNome());
		
		Assertions.assertThat(cargoSaved.getPagamentoHora()).isEqualTo(cargoToBeSaved.getPagamentoHora());
	}
	
	@Test
	void update_UpdateCargo_WhenSuccesful() {
		Cargo cargoToBeSaved = createCargo();
		
		Cargo cargoSaved = this.cargoRepository.save(cargoToBeSaved);
		
		cargoSaved.setNome("Engenheiro");
		
		Cargo cargoUpdated = this.cargoRepository.save(cargoSaved);
		
		Assertions.assertThat(cargoUpdated).isNotNull();
		
		Assertions.assertThat(cargoUpdated.getId()).isNotNull();
		
		Assertions.assertThat(cargoUpdated.getNome()).isEqualTo(cargoSaved.getNome());
		
		Assertions.assertThat(cargoUpdated.getPagamentoHora()).isEqualTo(cargoSaved.getPagamentoHora());
		
	}
	
	@Test
	void delete_RemovesCargo_WhenSuccesful() {
		Cargo cargoToBeSaved = createCargo();
		
		Cargo cargoSaved = this.cargoRepository.save(cargoToBeSaved);
		
		this.cargoRepository.delete(cargoSaved);
		
		Optional<Cargo> cargoOptional = this.cargoRepository.findById(cargoToBeSaved.getId());	
		
		Assertions.assertThat(cargoOptional).isEmpty();
	}
	
	@Test
	void listAll_ReturnsListOfCargo_WhenSuccessful() {
		Cargo cargoToBeSaved = createCargo();
		
		Cargo cargoSaved = this.cargoRepository.save(cargoToBeSaved);
		
		List<Cargo> cargos = this.cargoRepository.findAll();
		
		Assertions.assertThat(cargos)
				.isNotEmpty()
				.contains(cargoSaved);
	}
	
	@Test
	void findById_ReturnsCargo_WhenSuccessful() {
		Cargo cargoToBeSaved = createCargo();
		
		Cargo cargoSaved = this.cargoRepository.save(cargoToBeSaved);
		
		Optional<Cargo> cargoOptional = this.cargoRepository.findById(cargoSaved.getId());
		
		Assertions.assertThat(cargoOptional).isNotEmpty();
		
		Assertions.assertThat(cargoOptional.get()).isEqualTo(cargoSaved);
	}
	
	@Test
	void findByNome_ReturnsCargo_WhenSuccessful() {
		Cargo cargoToBeSaved = createCargo();
		
		Cargo cargoSaved = this.cargoRepository.save(cargoToBeSaved);
		
		Cargo cargo = this.cargoRepository.findByNome(cargoSaved.getNome());
		
		Assertions.assertThat(cargo).isEqualTo(cargoSaved);
	}
	 
}
