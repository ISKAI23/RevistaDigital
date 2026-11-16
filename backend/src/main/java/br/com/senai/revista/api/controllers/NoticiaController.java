package br.com.senai.revista.api.controllers;

import br.com.senai.revista.domain.model.Categoria;
import br.com.senai.revista.domain.model.Noticia;
import br.com.senai.revista.domain.repositories.CategoriaRepository;
import br.com.senai.revista.domain.repositories.NoticiaRepository;
import br.com.senai.revista.domain.services.NoticiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/noticia")
@Tag(name = "Notícias", description = "Endpoints para gerenciamento e consulta de notícias")
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
    @Operation(summary = "Listar todas as notícias", description = "Retorna uma lista contendo todas as notícias cadastradas no sistema, sem filtros.")
    @ApiResponse(responseCode = "200", description = "Notícias listadas com sucesso")
    public List<Noticia> listarTodasNoticias() {
        return noticiaRepository.findAll();
    }
    
    @GetMapping("/id/{id}")
    @Operation(summary = "Buscar notícia por ID", description = "Retorna os detalhes completos de uma notícia específica baseada no seu ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notícia encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Notícia não encontrada")
    })
    public ResponseEntity<Noticia> listarNoticiaPorId(
            @Parameter(description = "ID da notícia a ser buscada") @PathVariable Long id) {
        
        Optional<Noticia> noticia = noticiaRepository.findById(id);
        
        if (noticia.isPresent()) {
            return ResponseEntity.ok(noticia.get());
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }
    
    @GetMapping("/titulo/{titulo}")
    @Operation(summary = "Buscar notícias por título", description = "Retorna uma lista de notícias cujo título contenha a palavra ou frase informada (ignora maiúsculas/minúsculas).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notícias encontradas com sucesso"),
        @ApiResponse(responseCode = "404", description = "Nenhuma notícia encontrada com o título informado")
    })
    public ResponseEntity<List<Noticia>> listarNoticiaPorTitulo(
            @Parameter(description = "Termo de busca para o título") @PathVariable String titulo) {
        
        List<Noticia> noticias = noticiaRepository.findByTituloContainingIgnoreCase(titulo);
        
        if (!noticias.isEmpty()) {
            return ResponseEntity.ok(noticias);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/slug/{slug}")
    @Operation(summary = "Buscar notícias por slug", description = "Pesquisa e retorna notícias que correspondam parcial ou totalmente ao slug (URL amigável) informado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notícias encontradas com sucesso"),
        @ApiResponse(responseCode = "404", description = "Nenhuma notícia encontrada com o slug informado")
    })
    public ResponseEntity<List<Noticia>> listarNoticiaPorSlug(
            @Parameter(description = "Slug ou parte do slug da notícia") @PathVariable String slug) {
        
        List<Noticia> noticias = noticiaRepository.findBySlugContainingIgnoreCase(slug);
        
        if (!noticias.isEmpty()) {
            return ResponseEntity.ok(noticias);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/categoria/id/{categoriaID}")
    @Operation(summary = "Buscar notícias por Categoria", description = "Retorna todas as notícias vinculadas a uma determinada categoria, informando o ID da categoria.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notícias encontradas para a categoria informada"),
        @ApiResponse(responseCode = "404", description = "Nenhuma notícia encontrada ou a categoria não existe"),
        @ApiResponse(responseCode = "500", description = "Erro interno: Categoria não encontrada (gera RuntimeException)")
    })
    public ResponseEntity<List<Noticia>> listarNoticiaPorCategoria(
            @Parameter(description = "ID da categoria") @PathVariable Long categoriaID) {
        
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
    @Operation(summary = "Buscar notícias por status", description = "Retorna uma lista de notícias filtradas pelo seu status atual (ex: PUBLICADA, RASCUNHO, etc).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notícias encontradas com sucesso"),
        @ApiResponse(responseCode = "404", description = "Nenhuma notícia encontrada com o status informado")
    })
    public ResponseEntity<List<Noticia>> listarNoticiaPorStatus(
            @Parameter(description = "Status da notícia a ser filtrado") @PathVariable String status) {
        
        List<Noticia> noticias = noticiaRepository.findByStatusIgnoreCase(status);
        
        if (!noticias.isEmpty()) {
            return ResponseEntity.ok(noticias);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/personalizado")
    @Operation(summary = "Busca avançada e personalizada de notícias", description = "Realiza uma busca dinâmica utilizando múltiplos filtros opcionais através do corpo da requisição (titulo, categoria, status, autor, dataMinima, dataMaxima).")
    @ApiResponse(responseCode = "200", description = "Lista de notícias correspondentes aos filtros aplicados")
    public List<Noticia> queryPersonalizada(
            @Parameter(description = "Objeto contendo os filtros desejados") @RequestBody FiltroNoticia busca) {
        
        return noticiaService.buscarNoticia(busca);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adicionar nova notícia", description = "Cadastra uma nova notícia no sistema. O corpo da requisição deve passar pela validação do bean.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Notícia criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
    })
    public Noticia adicionarNoticia(
            @RequestBody @Valid Noticia noticia) {
        
        return noticiaService.adicionarNoticia(noticia);
        
    }
    
    @PutMapping("/id/{id}")
    @Operation(summary = "Atualizar notícia", description = "Substitui os dados de uma notícia existente pelos dados enviados no corpo da requisição, mantendo o mesmo ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notícia atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados"),
        @ApiResponse(responseCode = "404", description = "Notícia não encontrada para atualização")
    })
    public ResponseEntity<Noticia> atualizarNoticia(
            @Parameter(description = "ID da notícia a ser atualizada") @PathVariable Long id,
            @RequestBody @Valid Noticia noticia) {
        
        if (!noticiaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        noticia.setId(id);
        noticia = noticiaService.atualizarNoticia(noticia);
        return ResponseEntity.ok(noticia);
        
    }
    
    @DeleteMapping("/id/{id}")
    @Operation(summary = "Excluir notícia", description = "Remove permanentemente uma notícia do banco de dados baseada no seu ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Notícia excluída com sucesso (Sem conteúdo)"),
        @ApiResponse(responseCode = "404", description = "Notícia não encontrada para exclusão")
    })
    public ResponseEntity<Void> excluirNoticia(
            @Parameter(description = "ID da notícia a ser excluída") @PathVariable Long id) {
        
        if (!noticiaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        noticiaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
        
    }
    
}