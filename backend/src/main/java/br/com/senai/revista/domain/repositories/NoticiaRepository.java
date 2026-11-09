package br.com.senai.revista.domain.repositories;

import br.com.senai.revista.domain.model.Categoria;
import br.com.senai.revista.domain.model.Noticia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author DIGMA
 */
public interface NoticiaRepository extends JpaRepository<Noticia, Long>, JpaSpecificationExecutor<Noticia>{
    
    List<Noticia> findByTituloContainingIgnoreCase(String titulo);
    List<Noticia> findBySlugContainingIgnoreCase(String slug);
    List<Noticia> findByCategoria(Categoria categoria);
    
    @Query(nativeQuery = true, value = """
        SELECT * FROM noticias
        WHERE status = :status                               
    """)
    List<Noticia> findByStatusIgnoreCase(String status);
    
}
