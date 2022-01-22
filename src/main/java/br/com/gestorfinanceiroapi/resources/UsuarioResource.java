package br.com.gestorfinanceiroapi.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestorfinanceiroapi.model.Usuario;
import br.com.gestorfinanceiroapi.repositories.UsuarioRepository;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioRepository repository;
	private PasswordEncoder enconder;
	
	

	public UsuarioResource(UsuarioRepository repository, PasswordEncoder enconder) {
		super();
		this.repository = repository;
		this.enconder = enconder;
	}

	@GetMapping("/listarTodos")
	public ResponseEntity<List<Usuario>> listarTodos() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@PostMapping
	public ResponseEntity<Usuario> salvar (@RequestBody Usuario usuario ){
		usuario.setPassword(enconder.encode(usuario.getPassword()));
		return ResponseEntity.ok(repository.save(usuario));
		
	}
	
	@GetMapping("/validarSenha")
	public ResponseEntity<Boolean> validarSenha(@RequestParam String login, @RequestParam String password){
		
		Optional<Usuario> optUsuario = repository.findByLogin(login);
		if (optUsuario.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
		
		}
			
			
			Usuario usuario = optUsuario.get();
			boolean valid = enconder.matches(password, usuario.getPassword());
			
			HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
			
			return ResponseEntity.status(status).body(valid);
			
		}
		
	} 


