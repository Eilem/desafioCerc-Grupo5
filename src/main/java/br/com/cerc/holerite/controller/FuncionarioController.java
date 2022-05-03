package br.com.cerc.holerite.controller;

import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/api/v1/funcionario")
public class FuncionarioController {
	private final FuncionarioService funcionarioService;
	
	public FuncionarioController(FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;
	}

	@ApiOperation(value = "Busca usuario por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna usuario existente ou inexistente"),
			@ApiResponse(code = 400, message = "Retorno inexistente")
	})
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
		return new ResponseEntity<>(funcionarioService.findById(id), HttpStatus.OK);	
	}

	@ApiOperation(value = "Busca lista de usuarios no sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna com Usuarios"),
			@ApiResponse(code = 204, message = "Retorno sem Usuario")
	})
	@GetMapping
	public ResponseEntity<?> listAll(Pageable pageable) {
		return new ResponseEntity<>(funcionarioService.listAll(pageable), HttpStatus.OK);
	}

	@ApiOperation(value = "Salva novo usuario no sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna usuario cadastrado"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid FuncionarioDTO dto) {
		return new ResponseEntity<>(funcionarioService.save(dto), HttpStatus.CREATED);
	}

	@ApiOperation(value = "Deletar usuário existente")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Usuário deletado!"),
			@ApiResponse(code = 400, message = "Id de usuário invalido")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		funcionarioService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "Atualizar usuario existente")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna usuario atualizado"),
			@ApiResponse(code = 400, message = "Id de usuario invalido")
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> replace(@RequestBody @Valid FuncionarioDTO dto, @PathVariable long id) {
		funcionarioService.replace(dto, id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
