package br.com.senai.revista.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DIGMA
 */
@Entity
@Table(name = "noticias")
public class Noticia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(max = 200)
    private String titulo;
    
    @Size(max = 220)
    private String slug;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario autor;
    
    @Enumerated(EnumType.STRING)
    private StatusNoticia status;
    
    @ManyToOne
    private Categoria categoria;
    
    @OneToMany(mappedBy = "noticia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ConteudoNoticia> conteudos = new ArrayList<>();
    
    @OneToOne(mappedBy = "noticia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Imagem capa;
    
    private LocalDateTime publicadoEm;

    public Noticia() {
    }
    
    public Noticia(Long id, String titulo, Usuario autor, String slug, StatusNoticia status, Categoria categoria, Imagem capa, List<ConteudoNoticia> conteudos, LocalDateTime publicadoEm) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.slug = slug;
        this.status = status;
        this.categoria = categoria;
        this.capa = capa;
        this.conteudos = conteudos;
        this.publicadoEm = publicadoEm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Usuario getAutor() {
        return autor;
    }
    
    public void setAutor(Usuario autor) {
        this.autor = autor;
    }
    
    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public StatusNoticia getStatus() {
        return status;
    }

    public void setStatus(StatusNoticia status) {
        this.status = status;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    public Imagem getCapa() {
        return capa;
    }
    
    public void setCapa(Imagem capa) {
        this.capa = capa;
    }
    
    public List<ConteudoNoticia> getConteudos() {
        return conteudos;
    }
    
    public void setConteudos(List<ConteudoNoticia> conteudos) {
        this.conteudos = conteudos;
    }

    public LocalDateTime getPublicadoEm() {
        return publicadoEm;
    }

    public void setPublicadoEm(LocalDateTime publicadoEm) {
        this.publicadoEm = publicadoEm;
    }
    
}
