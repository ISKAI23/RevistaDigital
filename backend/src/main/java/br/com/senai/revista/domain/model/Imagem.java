package br.com.senai.revista.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 *
 * @author DIGMA
 */
@Entity 
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonBackReference
    private Noticia noticia;
    
    @Size(max = 255)
    private String url;

    @Size(max = 150)
    private String altText;

    private LocalDateTime criadoEm;

    public Imagem() {
    }

    public Imagem(Long id, Noticia noticia, String url, String altText, LocalDateTime criadoEm) {
        this.id = id;
        this.noticia = noticia;
        this.url = url;
        this.altText = altText;
        this.criadoEm = criadoEm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Noticia getNoticia() {
        return noticia;
    } 
    
    public void setNoticia(Noticia noticia) {
        this.noticia = noticia;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

}
