package br.com.senai.revista.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


/**
 *
 * @author DIGMA
 */
@Entity
@Table(name = "conteudos")
public class ConteudoNoticia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Noticia noticia;
    
    private int ordem;
    
    @NotNull
    private String conteudo;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoSecaoNoticia tipoSecao;

    public ConteudoNoticia() {
    }

    public ConteudoNoticia(Long id, Noticia noticia, int ordem, String conteudo, TipoSecaoNoticia tipoSecao) {
        this.id = id;
        this.noticia = noticia;
        this.ordem = ordem;
        this.conteudo = conteudo;
        this.tipoSecao = tipoSecao;
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

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public TipoSecaoNoticia getTipoSecao() {
        return tipoSecao;
    }

    public void setTipoSecao(TipoSecaoNoticia tipoSecao) {
        this.tipoSecao = tipoSecao;
    }

    

}