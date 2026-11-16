package br.com.senai.revista.api.controllers;

import br.com.senai.revista.domain.model.Imagem;
import br.com.senai.revista.domain.repositories.ImagemRepository;
import br.com.senai.revista.domain.services.ImagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Imagens", description = "Endpoints para gerenciamento de imagens do sistema")
public class ImagemController {

    @Autowired
    private ImagemRepository imagemRepository;
    
    @Autowired
    private ImagemService imagemService;
    
    @GetMapping
    @Operation(summary = "Listar todas as imagens", description = "Retorna uma lista contendo todos os registros de imagens cadastrados no sistema.")
    @ApiResponse(responseCode = "200", description = "Imagens listadas com sucesso")
    public List<Imagem> findAll() {
        List<Imagem> result = imagemRepository.findAll();

        return result;

    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Buscar imagem por ID", description = "Retorna os detalhes de uma imagem específica baseada no seu ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Imagem encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Imagem não encontrada")
    })
    public ResponseEntity<Imagem> findById(
            @Parameter(description = "ID da imagem a ser buscada") @PathVariable Long id) {

        Optional<Imagem> result = imagemRepository.findById(id);

        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adicionar nova imagem", description = "Cadastra um novo registro de imagem no sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Imagem criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição")
    })
    public Imagem salvar(
            @RequestBody Imagem imagem) {
        return imagemService.adicionarImagem(imagem);
    }

    @PutMapping("/id/{id}")
    @Operation(summary = "Atualizar imagem", description = "Atualiza os dados de uma imagem existente com base no seu ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Imagem atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Imagem não encontrada para atualização")
    })
    public ResponseEntity<Imagem> atualizar(
            @Parameter(description = "ID da imagem a ser atualizada") @PathVariable Long id, 
            @RequestBody Imagem imagem) {
        
        if (imagemRepository.existsById(id)) {
            imagem.setId(id);
            return ResponseEntity.ok(imagemService.adicionarImagem(imagem));

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("id/{id}")
    @Operation(summary = "Excluir imagem", description = "Remove permanentemente o registro de uma imagem do sistema baseada no seu ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Imagem excluída com sucesso (Sem conteúdo)"),
        @ApiResponse(responseCode = "404", description = "Imagem não encontrada para exclusão")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da imagem a ser excluída") @PathVariable Long id) {
        
        if (imagemRepository.existsById(id)) {
            imagemRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}