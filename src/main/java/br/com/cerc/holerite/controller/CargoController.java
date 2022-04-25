package br.com.cerc.holerite.controller;

import br.com.cerc.holerite.persistence.model.Cargo;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.cerc.holerite.service.CargoService;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/cargo")
public class CargoController {
	private final CargoService cargoService;
	
	public CargoController(CargoService cargoService) {
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

	@PostMapping()
	public ResponseEntity<Object> salvarCargo(@Valid @RequestBody Cargo cargo) {
		return cargoService.cadastrarCargo(cargo).map(resp -> ResponseEntity.status(201).body(resp)).orElseThrow(() -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cargo já existe, cadestre outro!");
		});
	}
}
