/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.senai.revista.domain.services;

import br.com.senai.revista.domain.model.Categoria;
import br.com.senai.revista.domain.repositories.CategoriaRepository;
import java.text.Normalizer;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DIGMA
 */

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository repository;
    
    public Categoria adicionarCategoria(Categoria categoria) {
        
        String slug = transformarSlug(categoria.getNome());
        categoria.setSlug(slug);
        
        return repository.save(categoria);
        
    }
    
    public Categoria atualizarCategoria(Categoria categoria) {
        
        String slug = transformarSlug(categoria.getNome());
        categoria.setSlug(slug);
        
        return repository.save(categoria);
        
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
