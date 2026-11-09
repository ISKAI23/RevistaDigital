/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.senai.revista.api.controllers;

import br.com.senai.revista.domain.model.Noticia;
import br.com.senai.revista.domain.model.ConteudoNoticia;
import br.com.senai.revista.domain.repositories.NoticiaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.senai.revista.domain.repositories.ConteudoNoticiaRepository;
import br.com.senai.revista.domain.services.ConteudoNoticiaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author DIGMA
 */
@CrossOrigin
@RestController
@RequestMapping("/conteudo-noticia")
public class ConteudoNoticiaController {

    @Autowired
    private ConteudoNoticiaRepository conteudoRepository;
    
    @Autowired
    private ConteudoNoticiaService conteudoService;
    
    @Autowired
    private NoticiaRepository noticiaRepository;
    
    public record FiltroConteudo(
            String titulo, String categoria, String status,
            String autor, String dataMinima, String dataMaxima,
            String tipo, String conteudo
        ) {}

    @GetMapping
    public List<ConteudoNoticia> listarTodosConteudoNoticia() {
        List<ConteudoNoticia> result = conteudoRepository.findAll();
        return result;

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ConteudoNoticia> listarConteudoNoticiaPorID(@PathVariable Long id) {
        Optional<ConteudoNoticia> result = conteudoRepository.findById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }
    
    @GetMapping("/secao/{tipo}")
    public ResponseEntity<List<ConteudoNoticia>> listarConteudoNoticiaPorTipoSecao(@PathVariable String tipo) {
        
        List<ConteudoNoticia> conteudoNoticia = conteudoRepository.findByTipoSecaoIgnoreCase(tipo);
        
        if (!conteudoNoticia.isEmpty()) {
            return ResponseEntity.ok(conteudoNoticia);
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }
    
    @GetMapping("/noticia/id/{noticiaID}")
    public ResponseEntity<List<ConteudoNoticia>> listarConteudoNoticiaPorNoticia(@PathVariable Long noticiaID) {
        
        Noticia noticia = noticiaRepository
                .findById(noticiaID)
                .orElseThrow(() -> new RuntimeException("Notícia com o ID (" + noticiaID + ") não foi encontrado!"));
        
        List<ConteudoNoticia> conteudoNoticia = conteudoRepository.findByNoticia(noticia);
        
        if (!conteudoNoticia.isEmpty()) {
            return ResponseEntity.ok(conteudoNoticia);
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }
    
    @GetMapping("/personalizado")
    public List<ConteudoNoticia> queryPersonalizada(@RequestBody FiltroConteudo busca) {
        return conteudoService.buscarConteudo(busca);
    } 
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConteudoNoticia adicionarConteudoNoticia(@RequestBody ConteudoNoticia conteudo) {
        
        return conteudoService.adicionarConteudoNoticia(conteudo);
        
    }
    
    @PutMapping("/id/{id}")
    public ResponseEntity<ConteudoNoticia> atualizarConteudoNoticia(
            @PathVariable Long id,
            @RequestBody ConteudoNoticia conteudo) {
        
        if(!conteudoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        conteudo.setId(id);
        conteudo = conteudoService.atualizarConteudoNoticia(conteudo);
        return ResponseEntity.ok(conteudo);
        
    }
    
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> excluirConteudoNoticia(@PathVariable Long id) {
        
        if (!conteudoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        conteudoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
        
    }
    
}
