package br.com.cerc.holerite.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cerc.holerite.persistence.dto.CargoDTO;
import br.com.cerc.holerite.service.CargoService;

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
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid CargoDTO dto) {
		return new ResponseEntity<>(cargoService.save(dto), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable long id){
		cargoService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> replace(@RequestBody @Valid CargoDTO dto, @PathVariable long id) {
		cargoService.replace(dto, id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
