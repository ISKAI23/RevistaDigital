package br.com.senai.revista.domain.repositories;

import br.com.senai.revista.domain.model.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @author DIGMA
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    List<Categoria> findByNomeContainingIgnoreCase(String nome);
    List<Categoria> findBySlugContainingIgnoreCase(String slug);
    
}