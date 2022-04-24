package br.com.cerc.holerite.endpoint;

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

import br.com.cerc.holerite.persistence.dto.FuncionarioDTO;
import br.com.cerc.holerite.persistence.model.Funcionario;
import br.com.cerc.holerite.service.FuncionarioService;

@RestController
@RequestMapping("funcionario")
public class FuncionarioEndpoint {
	private final FuncionarioService funcionarioService;
	
	public FuncionarioEndpoint(FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
		return new ResponseEntity<>(funcionarioService.findById(id), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<?> listAll(Pageable pageable) {
		return new ResponseEntity<>(funcionarioService.listAll(pageable), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid FuncionarioDTO funcionarioDTO) {
		return new ResponseEntity<>(funcionarioService.save(funcionarioDTO), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		funcionarioService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<?> replace(@RequestBody @Valid FuncionarioDTO dto) {
		funcionarioService.replace(dto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
