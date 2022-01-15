package br.com.gestorfinanceiroapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestorfinanceiroapi.models.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
