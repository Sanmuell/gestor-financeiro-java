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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestorfinanceiroapi.event.RecursoCriadoEvent;
import br.com.gestorfinanceiroapi.models.Categoria;
import br.com.gestorfinanceiroapi.repositories.CategoriaRepository;

@RestController
@RequestMapping("/categorias")

public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Categoria> listarTodas() {
		return categoriaRepository.findAll();

	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Optional<Categoria>> buscarPorId(@PathVariable Long codigo) {
		Optional<Categoria> categoriaSalva = categoriaRepository.findById(codigo);

		if (!categoriaSalva.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().body(categoriaSalva);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Categoria> salvar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {

		Categoria categoriaSalva = categoriaRepository.save(categoria);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));

		// Retorno de Conteúdo
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);

	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		categoriaRepository.deleteById(codigo);
	}
}
