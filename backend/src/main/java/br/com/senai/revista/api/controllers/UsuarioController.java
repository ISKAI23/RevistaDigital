package br.com.senai.revista.api.controllers;

import br.com.senai.revista.domain.model.Usuario;
import br.com.senai.revista.domain.repositories.UsuarioRepository;
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
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Usuario> findAll() {
        List<Usuario> result = usuarioRepository.findAll();

        return result;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        Optional<Usuario> result = usuarioRepository.findById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Usuario>> findByNome(@PathVariable String nome) {
        List<Usuario> result = usuarioRepository.findByNomeContainingIgnoreCase(nome);
        if (!result.isEmpty()) {
            return ResponseEntity.ok(result);

        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/email/{email}")
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
    public Usuario salvar(@RequestBody @Valid Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @PutMapping("/id/{id}")
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
    public ResponseEntity<Void> excluir(@PathVariable @Valid Long id) {

        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
