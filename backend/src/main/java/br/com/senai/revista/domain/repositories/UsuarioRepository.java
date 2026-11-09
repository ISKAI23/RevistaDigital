package br.com.senai.revista.domain.repositories;

import br.com.senai.revista.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author DIGMA
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
   List<Usuario> findByNomeContainingIgnoreCase(String nome);
   List<Usuario> findByEmailContainingIgnoreCase(String email);

}
