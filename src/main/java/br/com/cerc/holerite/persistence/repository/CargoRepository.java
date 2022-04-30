package br.com.cerc.holerite.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cerc.holerite.persistence.model.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long>{
	Cargo findByNome(String nome);
}
