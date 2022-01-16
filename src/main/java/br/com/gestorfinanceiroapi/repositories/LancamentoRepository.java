package br.com.gestorfinanceiroapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestorfinanceiroapi.models.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
