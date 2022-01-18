package br.com.gestorfinanceiroapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gestorfinanceiroapi.model.Categoria;


@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
