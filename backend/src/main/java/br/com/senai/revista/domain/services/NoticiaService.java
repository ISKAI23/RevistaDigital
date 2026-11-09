package br.com.senai.revista.domain.services;

import br.com.senai.revista.api.controllers.NoticiaController.FiltroNoticia;
import br.com.senai.revista.domain.model.Noticia;
import br.com.senai.revista.domain.model.StatusNoticia;
import br.com.senai.revista.domain.repositories.ConteudoNoticiaRepository;
import br.com.senai.revista.domain.repositories.NoticiaRepository;
import br.com.senai.revista.domain.specifications.NoticiaSpecs;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 *
 * @author DIGMA
 */

@Service
public class NoticiaService {
    
    @Autowired
    private NoticiaRepository noticiaRepository;
    
    @Autowired
    private ConteudoNoticiaRepository conteudoRepository;
    
    public Noticia adicionarNoticia(Noticia noticia) {
        
        String status = noticia.getStatus().toString();
        
        String slug = transformarSlug(noticia.getTitulo());
        noticia.setSlug(slug);
        
        if (status.equalsIgnoreCase("RASCUNHO")) {
            noticia.setStatus(StatusNoticia.RASCUNHO);
        } 
        else if (status.equalsIgnoreCase("PUBLICADO")) {
            noticia.setStatus(StatusNoticia.PUBLICADO);
        }
        
        noticia.setPublicadoEm(LocalDateTime.now());
        
        return noticiaRepository.save(noticia);
        
    }
    
    public Noticia atualizarNoticia(Noticia noticia) {
        
        String status = noticia.getStatus().toString();
        
        String slug = transformarSlug(noticia.getTitulo());
        noticia.setSlug(slug);
        
        if (status.equalsIgnoreCase("RASCUNHO")) {
            noticia.setStatus(StatusNoticia.RASCUNHO);
        }
        else if (status.equalsIgnoreCase("PUBLICADO")) {
            noticia.setStatus(StatusNoticia.PUBLICADO);
        }
        else if (status.equalsIgnoreCase("ARQUIVADO")) {
            noticia.setStatus(StatusNoticia.ARQUIVADO);
        }
        
        LocalDateTime publicadoEm = noticiaRepository
                .findById(noticia.getId())
                .orElseThrow(() -> new RuntimeException("Notícia com o ID " + noticia.getId() + " não foi encontrado"))
                .getPublicadoEm();
        
        noticia.setPublicadoEm(publicadoEm);
        
        return noticiaRepository.save(noticia);
    }
    
    public List<Noticia> buscarNoticia(FiltroNoticia busca) {
        
        String titulo = busca.titulo();
        String status = busca.status();
        String categoria = busca.categoria();
        String autor = busca.autor();
        String dataMinima = busca.dataMinima();
        String dataMaxima = busca.dataMaxima();
        
        Specification<Noticia> spec = NoticiaSpecs.comFiltros(
                titulo, categoria, status, autor, dataMinima, dataMaxima
        );
        
        return noticiaRepository.findAll(spec);
        
    }
    
    public String transformarSlug(String str) {
        
        if (str == null) {
            return null;
        }
        
        String strNormalize = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String slug = pattern.matcher(strNormalize).replaceAll("").toLowerCase();
        
        return slug.replace(" ", "-");
        
    }
    
}
