package br.com.gestorfinanceiroapi.resources;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestorfinanceiroapi.event.RecursoCriadoEvent;
import br.com.gestorfinanceiroapi.model.Lancamento;
import br.com.gestorfinanceiroapi.repositories.LancamentoRepository;
import br.com.gestorfinanceiroapi.repositories.filter.LancamentoFilter;
import br.com.gestorfinanceiroapi.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	
	
	
	@GetMapping
	public List<Lancamento> pesquisar(LancamentoFilter lancamentoFilter) {
		return lancamentoRepository.filtrar(lancamentoFilter);

	}
	
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Optional<Lancamento>> buscarPorId(@PathVariable Long codigo) {
		Optional<Lancamento> lancamentoSalva = lancamentoRepository.findById(codigo);

		if (!lancamentoSalva.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().body(lancamentoSalva);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Lancamento> salvar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {

		Lancamento lancamentoSalva = lancamentoService.salvar(lancamento);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalva.getCodigo()));

		// Retorno de Conteúdo
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalva);

	}
	
	
		

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		lancamentoRepository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Lancamento> atualizar(@PathVariable Long codigo, @Valid @RequestBody Lancamento lancamento){
		Lancamento lancamentoAtual = lancamentoService.atualizar(codigo, lancamento);
		return ResponseEntity.ok(lancamentoAtual);
		
	}
	
	
}
