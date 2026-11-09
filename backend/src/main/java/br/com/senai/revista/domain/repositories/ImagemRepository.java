package br.com.senai.revista.domain.repositories;

import br.com.senai.revista.domain.model.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author gm_2di
 */
public interface ImagemRepository extends JpaRepository<Imagem,Long> {
    
}
