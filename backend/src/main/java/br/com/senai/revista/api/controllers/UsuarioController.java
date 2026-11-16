package br.com.senai.revista.api.controllers;

import br.com.senai.revista.domain.model.Usuario;
import br.com.senai.revista.domain.repositories.UsuarioRepository;
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
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento dos usuários do sistema")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista contendo todos os usuários cadastrados no sistema.")
    @ApiResponse(responseCode = "200", description = "Usuários listados com sucesso")
    public List<Usuario> findAll() {
        List<Usuario> result = usuarioRepository.findAll();

        return result;
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna os detalhes de um usuário específico informando o seu ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        Optional<Usuario> result = usuarioRepository.findById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nome/{nome}")
    @Operation(summary = "Buscar usuários por nome", description = "Retorna uma lista de usuários que contenham o termo informado no nome (ignora maiúsculas e minúsculas).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Nenhum usuário encontrado com o nome informado")
    })
    public ResponseEntity<List<Usuario>> findByNome(@PathVariable String nome) {
        List<Usuario> result = usuarioRepository.findByNomeContainingIgnoreCase(nome);
        if (!result.isEmpty()) {
            return ResponseEntity.ok(result);

        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar usuários por e-mail", description = "Retorna uma lista de usuários que possuam o e-mail informado (parcial ou completo).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Nenhum usuário encontrado com o e-mail informado")
    })
    public ResponseEntity<List<Usuario>> findByEmail(@PathVariable String email) {
        List<Usuario> result = usuarioRepository.findByEmailContainingIgnoreCase(email);
        if (!result.isEmpty()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adicionar novo usuário", description = "Cria um novo registro de usuário no sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados de validação incorretos (ex: campos obrigatórios faltando)")
    })
    public Usuario salvar(@RequestBody @Valid Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @PutMapping("/id/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente com base no seu ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados de validação incorretos"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado para atualização")
    })
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        if (usuarioRepository.existsById(id)) {
            usuario.setId(id);
            usuario = usuarioRepository.save(usuario);
            return ResponseEntity.ok(usuario);

        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/id/{id}")
    @Operation(summary = "Excluir usuário", description = "Remove permanentemente um usuário do sistema com base no seu ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso (Sem conteúdo)"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado para exclusão")
    })
    public ResponseEntity<Void> excluir(@PathVariable @Valid Long id) {

        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}