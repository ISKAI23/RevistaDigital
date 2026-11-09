package br.com.senai.revista.domain.repositories;

import br.com.senai.revista.domain.model.Noticia;
import br.com.senai.revista.domain.model.ConteudoNoticia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author DIGMA
 */
public interface ConteudoNoticiaRepository extends JpaRepository<ConteudoNoticia, Long>, JpaSpecificationExecutor<ConteudoNoticia> {
    
    List<ConteudoNoticia> findByNoticia(Noticia noticia);
    
    @Query(nativeQuery = true, value = """
        SELECT * FROM conteudos
        WHERE tipo_secao = :tipo;                            
    """)
    List<ConteudoNoticia> findByTipoSecaoIgnoreCase(String tipo);
    
}
