package br.com.senai.revista.domain.services;

import br.com.senai.revista.domain.model.Imagem;
import br.com.senai.revista.domain.repositories.ImagemRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DIGMA
 */
@Service
public class ImagemService {
    
    @Autowired
    private ImagemRepository imagemRepository;
    
     public Imagem adicionarImagem(Imagem registro){
             
        if (registro.getId() != null) {
            Optional<Imagem> imagem = imagemRepository.findById(registro.getId());
            registro.setCriadoEm(imagem.get().getCriadoEm());
        } else {
            registro.setCriadoEm(LocalDateTime.now());
        }
         
        imagemRepository.save(registro);
        
        return registro;
   }
}
