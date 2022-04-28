package br.com.cerc.holerite.endpoint;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cerc.holerite.service.CargoService;

@RestController
@RequestMapping("/cargo")
public class CargoEndpoint {
	private final CargoService cargoService;
	
	public CargoEndpoint(CargoService cargoService) {
		this.cargoService = cargoService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
		return new ResponseEntity<>(cargoService.findById(id), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<?> listAll(Pageable page) {
		return new ResponseEntity<>(cargoService.listAll(page), HttpStatus.OK);
	}
}
