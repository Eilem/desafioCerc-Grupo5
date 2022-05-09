package br.com.cerc.holerite.controller;

import javax.validation.Valid;

import br.com.cerc.holerite.persistence.model.Cargo;
import br.com.cerc.holerite.persistence.repository.CargoRepository;
import br.com.cerc.holerite.persistence.repository.FuncionarioRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
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

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1/funcionarios")
public class FuncionarioController {
	private final FuncionarioService funcionarioService;
	private final FuncionarioRepository funcionarioRepository;
	private final CargoRepository cargoRepository;
	
	public FuncionarioController(FuncionarioService funcionarioService, FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository) {

		this.funcionarioService = funcionarioService;
		this.funcionarioRepository = funcionarioRepository;
		this.cargoRepository = cargoRepository;
	}

	@ApiOperation(value = "Salva novo usuario no sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna usuario cadastrado"),
			@ApiResponse(code = 400, message = "Erro na requisição")
	})
	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid FuncionarioDTO funcionarioDto) {

		if(funcionarioDto.getCpf() == null || funcionarioDto.getCpf().isEmpty()){
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Campo CPF é obrigatório!");
		}

		//Verifica se existe apenas números no CPF
		if (!Pattern.matches("[0-9]+", funcionarioDto.getCpf())){
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("CPF precisa conter apenas caracteres númericos");
		}

		//Criação de validação para verificar se o CPF possui 11 caracteres
		String cpfSemEspacos = funcionarioDto.getCpf().replaceAll(" ","");
		int tamanhoCpf = cpfSemEspacos.length();

		if(tamanhoCpf != 11){
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Campo CPF deve ter 11 números sem espaço entre eles!");
		}

		if(funcionarioDto.getNome() == null || funcionarioDto.getNome().isEmpty()){
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Campo nome é obrigatório");
		}

		if(funcionarioDto.getCargoId() == 0 || funcionarioDto.getCargoId() < 1){
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Campo id cargo não pode ser vazio ou menor igual a zero");
		}

		Optional<Cargo> cargo = cargoRepository.findById(funcionarioDto.getCargoId());

		if (cargo.isEmpty()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não existe este cargo no banco de dados");
		} 

		//busco no banco se já existe funcionario com o cpf recebido
		Funcionario funcionarioEncontrado = funcionarioService.getFuncionarioByCPF(funcionarioDto.getCpf());
		if(funcionarioEncontrado != null){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um funcionário com este CPF!");
		} else {
			return new ResponseEntity<>(funcionarioService.save(funcionarioDto), HttpStatus.CREATED);
		}

	}

	@ApiOperation(value = "Busca usuario por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna usuario existente ou inexistente"),
			@ApiResponse(code = 400, message = "Retorno inexistente")
	})
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
		Optional<Funcionario> funcionario = funcionarioService.findById(id);
		if(!funcionario.isPresent()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não localizado");
		}

		return ResponseEntity.status(HttpStatus.OK).body(funcionario.get());
	}

	@ApiOperation(value = "Busca lista de usuários no sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna com Usuários"),
			@ApiResponse(code = 204, message = "Retorno sem Usuario")
	})
	@GetMapping
	public ResponseEntity<?> listAll(Pageable pageable) {
		return new ResponseEntity<>(funcionarioService.listAll(pageable), HttpStatus.OK);
	}

	@ApiOperation(value = "Deletar usuário existente")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Usuário deletado!"),
			@ApiResponse(code = 400, message = "Id de usuário inválido")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable long id) {
		Optional<Funcionario> funcionario = funcionarioService.findById(id);

		if (!funcionario.isPresent()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não localizado.");
		}

		funcionarioService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body("Funcionário Deletado com sucesso!");
	}

	@ApiOperation(value = "Atualizar usuario existente")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna usuario atualizado"),
			@ApiResponse(code = 400, message = "Id de usuario inválido")
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> replace(@RequestBody @Valid FuncionarioDTO funcionarioDto, @PathVariable long id) {
		Optional<Funcionario> funcionario = funcionarioService.findById(id);

		if(funcionarioDto.getCpf() == null || funcionarioDto.getCpf().isEmpty()){
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Campo CPF é obrigatório!");
		}
		//Garante que o cpf vai ser numérico
		if (!Pattern.matches("[0-9]+", funcionarioDto.getCpf())){
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("CPF precisa conter apenas caracteres númericos");
		}

		//Criação de validação para verificar se o CPF possui 11 caracteres
		String cpfSemEspacos = funcionarioDto.getCpf().replaceAll(" ","");
		int tamanhoCpf = cpfSemEspacos.length();

		if(tamanhoCpf != 11){
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Campo CPF deve ter 11 números sem espaço entre eles!");
		}

		if(funcionarioDto.getCargoId() == 0 || funcionarioDto.getCargoId() < 1){
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Campo id cargo não pode ser vazio ou menor igual a zero");
		}

		if(funcionarioDto.getNome() == null || funcionarioDto.getNome().isEmpty()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Campo nome é obrigatório!");
		}

		if(!funcionario.isPresent()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não localizado.");
		}

		List<Funcionario> funcionarioList=funcionarioRepository.findAllByCpf(funcionarioDto.getCpf());

		for (Funcionario funcionarioDB : funcionarioList) {
			if (funcionarioDB.getId() != id){
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe funcionário com este CPF cadastrado");
			}
		}

		//criar lista de funcionários para validar se algum usuário já tem o mesmo cpf lá dentro.

		//Validar regra de edição na busca de CPF do usuário de id diferente
//		Funcionario funcionarioEncontrado = funcionarioService.getFuncionarioByCPF(funcionarioDto.getCpf());
//		if(funcionarioEncontrado != null){
//			return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um funcionário com este CPF!");
//		} else {
//
//		}

		funcionarioService.replace(funcionario.get() ,funcionarioDto);

		return  ResponseEntity.status(HttpStatus.OK).body("Funcionário editado com sucesso!");
	}



}
