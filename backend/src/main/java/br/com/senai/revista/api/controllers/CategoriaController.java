package br.com.senai.revista.api.controllers;

import br.com.senai.revista.domain.model.Categoria;
import br.com.senai.revista.domain.repositories.CategoriaRepository;
import br.com.senai.revista.domain.services.CategoriaService;
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
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaRepository repository;
    
    @Autowired
    private CategoriaService service;
    
    @GetMapping
    public List<Categoria> listarTodasCategorias() {
        
        return repository.findAll();
        
    }
    
    @GetMapping("/id/{id}")
    public ResponseEntity<Categoria> listarCategoriaPorId(
            @PathVariable Long id) {
        
        Optional<Categoria> categoria = repository.findById(id);
        
        if (categoria.isPresent()) {
            return ResponseEntity.ok(categoria.get());
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }
    
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Categoria>> listarCategoriasPorNome(
            @PathVariable String nome) {
        
        List<Categoria> categorias = repository
                .findByNomeContainingIgnoreCase(nome);
        
        if (!categorias.isEmpty()) {
            return ResponseEntity.ok(categorias);
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }
    
    @GetMapping("/slug/{slug}")
    public ResponseEntity<List<Categoria>> listarCategoriasPorSlug(
            @PathVariable String slug) {
        
        List<Categoria> categorias = repository
                .findBySlugContainingIgnoreCase(slug);
        
        if (!categorias.isEmpty()) {
            return ResponseEntity.ok(categorias);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria adicionarCategoria(
            @RequestBody @Valid Categoria categoria) {
        
        return service.adicionarCategoria(categoria);
        
    }
    
    @PutMapping("/id/{id}")
    public ResponseEntity<Categoria> atualizarCategoria(
            @PathVariable Long id,
            @RequestBody Categoria categoria) {
        
        if(!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        categoria.setId(id);
        categoria = service.atualizarCategoria(categoria);
        return ResponseEntity.ok(categoria);
        
    }
    
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> excluirCategoria(
            @PathVariable Long id) {
        
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
        
    }
    
}
