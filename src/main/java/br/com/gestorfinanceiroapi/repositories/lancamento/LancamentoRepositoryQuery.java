package br.com.gestorfinanceiroapi.repositories.lancamento;

import java.util.List;

import br.com.gestorfinanceiroapi.model.Lancamento;
import br.com.gestorfinanceiroapi.repositories.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	
	
	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);


}
