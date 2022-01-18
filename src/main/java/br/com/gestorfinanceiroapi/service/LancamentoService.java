package br.com.gestorfinanceiroapi.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.gestorfinanceiroapi.model.Lancamento;
import br.com.gestorfinanceiroapi.model.Pessoa;
import br.com.gestorfinanceiroapi.repositories.LancamentoRepository;
import br.com.gestorfinanceiroapi.repositories.PessoaRepository;
import br.com.gestorfinanceiroapi.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Lancamento atualizar(@PathVariable Long codigo, @RequestBody Lancamento lancamento) {
		Lancamento lancamentoSalva = this.lancamentoRepository.findById(codigo)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));
		BeanUtils.copyProperties(lancamento, lancamentoSalva, "codigo");
		return this.lancamentoRepository.save(lancamentoSalva);
		

	}

	public Lancamento salvar(Lancamento lancamento) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo());

		if (pessoa.isEmpty() || pessoa.get().isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		return lancamentoRepository.save(lancamento);
	
			
	}


}
