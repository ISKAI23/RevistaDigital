package br.com.senai.revista.api.controllers;

import br.com.senai.revista.domain.model.Noticia;
import br.com.senai.revista.domain.model.ConteudoNoticia;
import br.com.senai.revista.domain.repositories.NoticiaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Conteúdo de Notícias", description = "Endpoints para gerenciamento dos blocos de conteúdo (seções) atrelados às notícias")
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
    @Operation(summary = "Listar todos os conteúdos de notícias", description = "Retorna uma lista contendo todos os blocos de conteúdo cadastrados no sistema.")
    @ApiResponse(responseCode = "200", description = "Conteúdos listados com sucesso")
    public List<ConteudoNoticia> listarTodosConteudoNoticia() {
        List<ConteudoNoticia> result = conteudoRepository.findAll();
        return result;

    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Buscar conteúdo por ID", description = "Retorna os detalhes de um bloco de conteúdo específico baseado no seu ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conteúdo encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Conteúdo não encontrado")
    })
    public ResponseEntity<ConteudoNoticia> listarConteudoNoticiaPorID(
            @Parameter(description = "ID do conteúdo a ser buscado") @PathVariable Long id) {
        Optional<ConteudoNoticia> result = conteudoRepository.findById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }
    
    @GetMapping("/secao/{tipo}")
    @Operation(summary = "Buscar conteúdos por tipo de seção", description = "Retorna uma lista de conteúdos filtrados pelo tipo da seção (ex: TEXTO, IMAGEM, VIDEO).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conteúdos encontrados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Nenhum conteúdo encontrado para o tipo de seção informado")
    })
    public ResponseEntity<List<ConteudoNoticia>> listarConteudoNoticiaPorTipoSecao(
            @Parameter(description = "Tipo da seção (ex: TEXTO, IMAGEM)") @PathVariable String tipo) {
        
        List<ConteudoNoticia> conteudoNoticia = conteudoRepository.findByTipoSecaoIgnoreCase(tipo);
        
        if (!conteudoNoticia.isEmpty()) {
            return ResponseEntity.ok(conteudoNoticia);
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }
    
    @GetMapping("/noticia/id/{noticiaID}")
    @Operation(summary = "Buscar conteúdos vinculados a uma Notícia", description = "Retorna todos os blocos de conteúdo atrelados a uma notícia específica, informando o ID da notícia.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conteúdos encontrados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Nenhum conteúdo encontrado para esta notícia"),
        @ApiResponse(responseCode = "500", description = "Erro interno: Notícia não encontrada (gera RuntimeException)")
    })
    public ResponseEntity<List<ConteudoNoticia>> listarConteudoNoticiaPorNoticia(
            @Parameter(description = "ID da Notícia a qual o conteúdo pertence") @PathVariable Long noticiaID) {
        
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
    @Operation(summary = "Busca avançada e personalizada de conteúdos", description = "Realiza uma busca dinâmica utilizando múltiplos filtros através do corpo da requisição.")
    @ApiResponse(responseCode = "200", description = "Lista de conteúdos correspondentes aos filtros aplicados")
    public List<ConteudoNoticia> queryPersonalizada(
            @Parameter(description = "Objeto contendo os filtros de busca desejados") @RequestBody FiltroConteudo busca) {
        return conteudoService.buscarConteudo(busca);
    } 
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adicionar novo conteúdo de notícia", description = "Cadastra um novo bloco de conteúdo atrelado a uma notícia no sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Conteúdo criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro na requisição ou dados inválidos")
    })
    public ConteudoNoticia adicionarConteudoNoticia(
            @RequestBody ConteudoNoticia conteudo) {
        
        return conteudoService.adicionarConteudoNoticia(conteudo);
        
    }
    
    @PutMapping("/id/{id}")
    @Operation(summary = "Atualizar conteúdo de notícia", description = "Atualiza os dados de um bloco de conteúdo existente com base no seu ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conteúdo atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Conteúdo não encontrado para atualização")
    })
    public ResponseEntity<ConteudoNoticia> atualizarConteudoNoticia(
            @Parameter(description = "ID do conteúdo a ser atualizado") @PathVariable Long id,
            @RequestBody ConteudoNoticia conteudo) {
        
        if(!conteudoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        conteudo.setId(id);
        conteudo = conteudoService.atualizarConteudoNoticia(conteudo);
        return ResponseEntity.ok(conteudo);
        
    }
    
    @DeleteMapping("/id/{id}")
    @Operation(summary = "Excluir conteúdo de notícia", description = "Remove permanentemente um bloco de conteúdo do sistema baseado no seu ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Conteúdo excluído com sucesso (Sem conteúdo)"),
        @ApiResponse(responseCode = "404", description = "Conteúdo não encontrado para exclusão")
    })
    public ResponseEntity<Void> excluirConteudoNoticia(
            @Parameter(description = "ID do conteúdo a ser excluído") @PathVariable Long id) {
        
        if (!conteudoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        conteudoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
        
    }
    
}