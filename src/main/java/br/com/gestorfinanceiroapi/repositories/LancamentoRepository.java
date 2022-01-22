package br.com.gestorfinanceiroapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestorfinanceiroapi.model.Lancamento;
import br.com.gestorfinanceiroapi.repositories.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

	
	


}
