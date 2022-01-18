package br.com.gestorfinanceiroapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestorfinanceiroapi.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
