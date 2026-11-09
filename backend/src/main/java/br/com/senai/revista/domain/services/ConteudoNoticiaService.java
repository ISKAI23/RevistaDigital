/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.senai.revista.domain.services;

import br.com.senai.revista.api.controllers.ConteudoNoticiaController.FiltroConteudo;
import br.com.senai.revista.domain.model.ConteudoNoticia;
import br.com.senai.revista.domain.repositories.ConteudoNoticiaRepository;
import br.com.senai.revista.domain.repositories.NoticiaRepository;
import br.com.senai.revista.domain.specifications.ConteudoSpecs;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 *
 * @author DIGMA
 */

@Service
public class ConteudoNoticiaService {
    
    @Autowired
    private ConteudoNoticiaRepository conteudoRepository;
    
    @Autowired
    private NoticiaRepository noticiaRepository;
    
    public ConteudoNoticia adicionarConteudoNoticia(ConteudoNoticia conteudo) {
        
        List<ConteudoNoticia> listaConteudo = conteudoRepository
                .findByNoticia(conteudo.getNoticia());
        
        if (listaConteudo.isEmpty()) {
            conteudo.setOrdem(1);
        } else {
            conteudo.setOrdem(listaConteudo.size() + 1);
        }
        
        return conteudoRepository.save(conteudo);
        
    }
    
    public ConteudoNoticia atualizarConteudoNoticia(ConteudoNoticia conteudo) {
        
        int ordem = conteudoRepository
                .findById(conteudo.getId())
                .orElseThrow(() -> new RuntimeException("Conteúdo de notícia com o ID " + conteudo.getId() + " não foi encontrado"))
                .getOrdem();
        
        conteudo.setOrdem(ordem);
        
        return conteudoRepository.save(conteudo);
        
    }
    
    public List<ConteudoNoticia> buscarConteudo(FiltroConteudo busca) {
        
        String titulo = busca.titulo();
        String categoria = busca.categoria();
        String status = busca.status();
        String autor = busca.autor();
        String dataMinima = busca.dataMinima();
        String dataMaxima = busca.dataMaxima();
        String tipo = busca.tipo();
        String conteudo = busca.conteudo();
        
        Specification<ConteudoNoticia> spec = ConteudoSpecs.comFiltros(
          titulo, categoria, status, autor, dataMinima, dataMaxima, tipo, conteudo     
        );
        
        return conteudoRepository.findAll(spec);
        
    }
    
}
