package br.com.gestorfinanceiroapi.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.gestorfinanceiroapi.data.DetalheUsuarioData;
import br.com.gestorfinanceiroapi.model.Usuario;
import br.com.gestorfinanceiroapi.repositories.UsuarioRepository;

@Component
public class DetalheUsuarioServiceImpl implements UserDetailsService {
	
	private final UsuarioRepository repository;
	
	

	public DetalheUsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = repository.findByLogin(username);
		if (usuario.isEmpty()) {
			throw new UsernameNotFoundException("Usuario " + username + "não encontrado" );
			
		}
		return new DetalheUsuarioData(usuario);
	}

}
