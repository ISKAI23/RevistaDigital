package br.com.senai.revista.api.controllers;

import br.com.senai.revista.domain.model.Categoria;
import br.com.senai.revista.domain.repositories.CategoriaRepository;
import br.com.senai.revista.domain.services.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Categorias", description = "Endpoints para gerenciamento das categorias de notícias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository repository;
    
    @Autowired
    private CategoriaService service;
    
    @GetMapping
    @Operation(summary = "Listar todas as categorias", description = "Retorna uma lista contendo todas as categorias cadastradas no sistema.")
    @ApiResponse(responseCode = "200", description = "Categorias listadas com sucesso")
    public List<Categoria> listarTodasCategorias() {
        return repository.findAll();
    }
    
    @GetMapping("/id/{id}")
    @Operation(summary = "Buscar categoria por ID", description = "Retorna os detalhes de uma categoria específica informando o seu ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
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
    @Operation(summary = "Buscar categorias por nome", description = "Retorna uma lista de categorias que contenham o termo informado no nome (ignora maiúsculas e minúsculas).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categorias encontradas com sucesso"),
        @ApiResponse(responseCode = "404", description = "Nenhuma categoria encontrada com o nome informado")
    })
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
    @Operation(summary = "Buscar categorias por slug", description = "Retorna uma lista de categorias baseada no slug informado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categorias encontradas com sucesso"),
        @ApiResponse(responseCode = "404", description = "Nenhuma categoria encontrada com o slug informado")
    })
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
    @Operation(summary = "Adicionar nova categoria", description = "Cria uma nova categoria no sistema de notícias.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição")
    })
    public Categoria adicionarCategoria(
            @RequestBody @Valid Categoria categoria) {
        
        return service.adicionarCategoria(categoria);
        
    }
    
    @PutMapping("/id/{id}")
    @Operation(summary = "Atualizar categoria", description = "Atualiza os dados de uma categoria existente com base no seu ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada para atualização")
    })
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
    @Operation(summary = "Excluir categoria", description = "Remove permanentemente uma categoria do sistema baseada no seu ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoria excluída com sucesso (Sem conteúdo)"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada para exclusão")
    })
    public ResponseEntity<Void> excluirCategoria(
            @PathVariable Long id) {
        
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
        
    }
    
}