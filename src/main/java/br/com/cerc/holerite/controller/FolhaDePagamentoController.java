package br.com.cerc.holerite.controller;

import javax.validation.Valid;

import br.com.cerc.holerite.persistence.model.FolhaDePagamento;
import br.com.cerc.holerite.service.FuncionarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cerc.holerite.persistence.dto.FolhaDePagamentoDTO;
import br.com.cerc.holerite.service.FolhaDePagamentoService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/folha")
public class FolhaDePagamentoController {
	private final FolhaDePagamentoService folhaDePagamentoService;
	private final FuncionarioService funcionarioService;
	
	public FolhaDePagamentoController(FolhaDePagamentoService folhaDePagamentoService, FuncionarioService funcionarioService) {
		this.folhaDePagamentoService = folhaDePagamentoService;
		this.funcionarioService = funcionarioService;
	}

	@ApiOperation(value = "Salva nova folha no sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna folha cadastrada")
	})
	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid FolhaDePagamentoDTO folhaDePagamentoDto){

		if (folhaDePagamentoDto.getFuncId() == 0 || folhaDePagamentoDto.getFuncId() < 1){
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Não existe funcionário para esta folha de pagamento");
		}

		if (folhaDePagamentoDto.getHorasTrabalhadas() <= 0){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não é possível inserir horas trabalhadas menores ou iguais a zero");
		}

		if ((folhaDePagamentoDto.getMesReferencia() > 12)|| (folhaDePagamentoDto.getMesReferencia() < 1)){
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Necessita colocar um valor de mês entre 1 e 12");
		}

		if (folhaDePagamentoDto.getAnoReferencia() < 2000){
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Necessita colocar valores maiores que 2000 no campo Ano Referencia");
		}

		try {
			//Verifica cada elemento da lista de pagamento de um funcionário para validar se já existe cadastrado um ano e mês repetido
		List<FolhaDePagamento> folhaDePagamentoList = folhaDePagamentoService.listAllByFunc(folhaDePagamentoDto.getFuncId());

			for (FolhaDePagamento folhaDePagamento : folhaDePagamentoList) {

				if (folhaDePagamento.getMesReferencia() == folhaDePagamentoDto.getMesReferencia() && folhaDePagamento.getAnoReferencia() == folhaDePagamentoDto.getAnoReferencia()) {
					return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe uma folha de pagamento com este mês e ano cadastrada");
				}

			}
		}catch (NoSuchElementException e){
			//caso caia nesta exceção após criar a lista e não houver funcionários vai retornar que não existe
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não existe este funcionário!");
		}
		return new ResponseEntity<>(folhaDePagamentoService.save(folhaDePagamentoDto), HttpStatus.CREATED);
	}


	@ApiOperation(value = "Busca Folha por Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna folha existente"),
			@ApiResponse(code = 204, message = "Retorno inexistente")
	})
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
		Optional<FolhaDePagamento> folhaDePagamento = folhaDePagamentoService.findById(id);
		if(folhaDePagamento.isEmpty()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Folha de pagamento não localizada.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(folhaDePagamento.get());
	}

	@ApiOperation(value = "Busca lista de folhas no sistema")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna lista de folhas"),
			@ApiResponse(code = 204, message = "Retorno sem folha")
	})
	@GetMapping("/todas")
	public ResponseEntity<?> listAll(Pageable pageable) {
		return new ResponseEntity<>(folhaDePagamentoService.listAll(pageable), HttpStatus.OK);
	}

	@ApiOperation(value = "Busca folha por nome funcionario")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna folha existente"),
			@ApiResponse(code = 204, message = "Retorno inexistente")
	})
	@GetMapping("/todas/{id}")
	public ResponseEntity<?> listAllByFunc(@PathVariable long id) {

		if (funcionarioService.findById(id).isEmpty()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não cadastrado");
		}
		return new ResponseEntity<>(folhaDePagamentoService.listAllByFunc(id), HttpStatus.OK);
	}
}
