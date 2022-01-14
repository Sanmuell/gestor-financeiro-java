package br.com.gestorfinanceiroapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gestorfinanceiroapi.models.Categoria;


@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
