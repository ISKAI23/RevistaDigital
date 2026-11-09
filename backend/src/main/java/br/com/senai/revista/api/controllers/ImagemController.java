package br.com.senai.revista.api.controllers;

import br.com.senai.revista.domain.model.Imagem;
import br.com.senai.revista.domain.repositories.ImagemRepository;
import br.com.senai.revista.domain.services.ImagemService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author DIGMA
 */
@RestController
@RequestMapping("/imagem")
public class ImagemController {

    @Autowired
    private ImagemRepository imagemRepository;
    
    @Autowired
    private ImagemService imagemService;
    
    

    @GetMapping
    public List<Imagem> findAll() {
        List<Imagem> result = imagemRepository.findAll();

        return result;

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Imagem> findById(@PathVariable Long id) {

        Optional<Imagem> result = imagemRepository.findById(id);

        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Imagem salvar(@RequestBody Imagem imagem) {
        return imagemService.adicionarImagem(imagem);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Imagem> atualizar(@PathVariable Long id, @RequestBody Imagem imagem) {
        if (imagemRepository.existsById(id)) {
            imagem.setId(id);
            return ResponseEntity.ok(imagemService.adicionarImagem(imagem));

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (imagemRepository.existsById(id)) {
            imagemRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
