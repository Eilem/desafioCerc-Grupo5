package br.com.cerc.holerite.controller;

import javax.validation.Valid;

import br.com.cerc.holerite.persistence.model.Cargo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cargos")
public class CargoController {
	private final CargoService cargoService;
	
	public CargoController(CargoService cargoService) {
		this.cargoService = cargoService;
	}

	@ApiOperation(value = "Salva novo cargo no sistema.")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna cargo cadastrado")
	})
	@PostMapping
	public ResponseEntity<Object> save(@RequestBody @Valid CargoDTO cargo) {

		//nome vazio
		if (cargo.getNome() == null || cargo.getNome().isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Campo nome é obrigatório!");
		}

		/**
		 * @todo tratar quando receber espaço vazio " "
		 */
		if (cargo.getPagamentoHora() == 0 ||  cargo.getPagamentoHora()  < 1 ) {

			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Campo pagamentoHora é obrigatório!");
		}

		//busco no banco se já existe cargo com o nome recebido
		Cargo cargoEncontrado = cargoService.getCargoByNome(cargo.getNome());
		if (cargoEncontrado != null) {			
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um cargo com este nome!");
		
		} else {
			return new ResponseEntity<>(cargoService.save(cargo), HttpStatus.OK);
		}
	}

	@ApiOperation(value = "Busca cargo por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna cargo existente"),
			@ApiResponse(code = 204, message = "Retorno inexistente")
	})
	@GetMapping("/{id}")
	public ResponseEntity<Object> findById(@PathVariable long id) 
	{
		Optional<Cargo> cargo = cargoService.findById(id);
        if (!cargo.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cargo não localizado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(cargo.get());
	}

	@ApiOperation(value = "Busca todas os cargos")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna cargo existente"),
			@ApiResponse(code = 204, message = "Retorno inexistente")
	})
	@GetMapping
	public ResponseEntity<?> listAll(Pageable page) {
		return new ResponseEntity<>(cargoService.listAll(page), HttpStatus.OK);
	}

	@ApiOperation(value = "Deletar cargo existente")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Caso deletada!"),
			@ApiResponse(code = 400, message = "Id de cargo invalido")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable long id){
		Optional<Cargo> cargo = cargoService.findById(id);
        if (!cargo.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cargo não localizado.");
        }
		cargoService.delete(cargo.get());
		return ResponseEntity.status(HttpStatus.OK).body("Cargo Deletado com sucesso");
	}


	@ApiOperation(value = "Atualizar cargo existente")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna cargo atualizada")
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> replace(@RequestBody @Valid CargoDTO cargoDto, @PathVariable long id) {
		Optional<Cargo> cargo = cargoService.findById(id);


		if((String) cargoDto.getNome() == null || cargoDto.getNome().isEmpty()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Campo nome é obrigatório!");
		}

        if (!cargo.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cargo não localizado.");
        }
		//@todo RN -> validar se o novo nome do cargo já não pertence há outro cargo cadastrado com id diferente 
		cargoService.replace(cargo.get() , cargoDto);
		return ResponseEntity.status(HttpStatus.OK).body("Cargo editado com sucesso");
	}
}
