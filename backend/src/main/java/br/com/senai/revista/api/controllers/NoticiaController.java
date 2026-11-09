/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.senai.revista.api.controllers;

import br.com.senai.revista.domain.model.Categoria;
import br.com.senai.revista.domain.model.Noticia;
import br.com.senai.revista.domain.repositories.CategoriaRepository;
import br.com.senai.revista.domain.repositories.NoticiaRepository;
import br.com.senai.revista.domain.services.NoticiaService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@CrossOrigin
@RestController
@RequestMapping("/noticia")
public class NoticiaController {
    
    @Autowired
    private NoticiaRepository noticiaRepository;

    @Autowired
    private NoticiaService noticiaService;
    
    @Autowired
    private CategoriaRepository CategoriaRepository;
    
    public record FiltroNoticia(
        String titulo, String categoria, String status,
        String autor, String dataMinima, String dataMaxima
    ) {}
    
    @GetMapping
    public List<Noticia> listarTodasNoticias() {
        return noticiaRepository.findAll();
    }
    
    @GetMapping("/id/{id}")
    public ResponseEntity<Noticia> listarNoticiaPorId(@PathVariable Long id) {
        
        Optional<Noticia> noticia = noticiaRepository.findById(id);
        
        if (noticia.isPresent()) {
            return ResponseEntity.ok(noticia.get());
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }
    
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<Noticia>> listarNoticiaPorTitulo(@PathVariable String titulo) {
        List<Noticia> noticias = noticiaRepository.findByTituloContainingIgnoreCase(titulo);
        
        if (!noticias.isEmpty()) {
            return ResponseEntity.ok(noticias);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/slug/{slug}")
    public ResponseEntity<List<Noticia>> listarNoticiaPorSlug(@PathVariable String slug) {
        List<Noticia> noticias = noticiaRepository.findBySlugContainingIgnoreCase(slug);
        
        if (!noticias.isEmpty()) {
            return ResponseEntity.ok(noticias);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/categoria/id/{categoriaID}")
    public ResponseEntity<List<Noticia>> listarNoticiaPorCategoria(@PathVariable Long categoriaID) {
        Categoria categoria = CategoriaRepository
                .findById(categoriaID)
                .orElseThrow(() -> new RuntimeException("Categoria com ID (" + categoriaID + ") não foi encontrado"));
        
        List<Noticia> noticias = noticiaRepository.findByCategoria(categoria);
        
        if (!noticias.isEmpty()) {
            return ResponseEntity.ok(noticias);
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Noticia>> listarNoticiaPorStatus(@PathVariable String status) {
        List<Noticia> noticias = noticiaRepository.findByStatusIgnoreCase(status);
        
        if (!noticias.isEmpty()) {
            return ResponseEntity.ok(noticias);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/personalizado")
    public List<Noticia> queryPersonalizada(@RequestBody FiltroNoticia busca) {
        return noticiaService.buscarNoticia(busca);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Noticia adicionarNoticia(@RequestBody @Valid Noticia noticia) {
        
        return noticiaService.adicionarNoticia(noticia);
        
    }
    
    @PutMapping("/id/{id}")
    public ResponseEntity<Noticia> atualizarNoticia(
            @PathVariable Long id,
            @RequestBody @Valid Noticia noticia) {
        
        if (!noticiaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        noticia.setId(id);
        noticia = noticiaService.atualizarNoticia(noticia);
        return ResponseEntity.ok(noticia);
        
    }
    
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> excluirNoticia(@PathVariable Long id) {
        
        if (!noticiaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        noticiaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
        
    }
    
}
