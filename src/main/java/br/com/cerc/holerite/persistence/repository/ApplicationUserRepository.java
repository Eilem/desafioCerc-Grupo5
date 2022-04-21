package br.com.cerc.holerite.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cerc.holerite.persistence.model.ApplicationUser;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long>{
	ApplicationUser findByUsername(String username);
}
