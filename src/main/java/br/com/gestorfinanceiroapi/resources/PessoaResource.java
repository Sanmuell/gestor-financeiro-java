package br.com.gestorfinanceiroapi.resources;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestorfinanceiroapi.event.RecursoCriadoEvent;
import br.com.gestorfinanceiroapi.models.Pessoa;
import br.com.gestorfinanceiroapi.repositories.PessoaRepository;

@RestController
@RequestMapping(path = "/pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public  List<Pessoa> listarTodas(){
		return pessoaRepository.findAll();
		
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Optional<Pessoa>> listarPorId (@PathVariable Long codigo){
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(codigo);
		
		if (pessoa.isPresent()) {
			
			return ResponseEntity.ok().body(pessoa);
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> salvar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
	Pessoa pessoaSalva = pessoaRepository.save(pessoa);
	
	publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
	
	return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
		
	}
	
	
}
