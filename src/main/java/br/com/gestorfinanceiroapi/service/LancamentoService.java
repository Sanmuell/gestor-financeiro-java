package br.com.gestorfinanceiroapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.gestorfinanceiroapi.models.Lancamento;
import br.com.gestorfinanceiroapi.repositories.LancamentoRepository;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	public Lancamento atualizar(@PathVariable Long codigo, @RequestBody Lancamento lancamento) {
		Lancamento lancamentoSalva = this.lancamentoRepository.findById(codigo)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));
		BeanUtils.copyProperties(lancamento, lancamentoSalva, "codigo");
		return this.lancamentoRepository.save(lancamentoSalva);
		

	}


}
