package br.com.senai.revista.domain.specifications;

import br.com.senai.revista.domain.model.ConteudoNoticia;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DIGMA
 */
public class ConteudoSpecs {

    public static Specification<ConteudoNoticia> comFiltros(
            String titulo, 
            String categoria,
            String status,
            String autor,
            String dataMinima,
            String dataMaxima,
            String tipo,
            String conteudo
    ) {
        
         return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (titulo != null && !titulo.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("noticia").get("titulo")), "%" + titulo.toLowerCase() + "%"));
            }
            
            if (categoria != null && !categoria.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("noticia").get("categoria").get("nome"), categoria));
            }
            
            if (status != null && !status.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("noticia").get("status"), status));
            }
            
            if (autor != null && !autor.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("noticia").get("autor").get("nome")), "%" + autor.toLowerCase() + "%"));
            }
            
            if (tipo != null && !tipo.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("tipo"), tipo));
            }
            
            if (conteudo != null && !conteudo.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("conteudo")), "%" + conteudo.toLowerCase() + "%"));
            }
            
            if ((dataMinima != null && !dataMinima.isEmpty()) &&
                    (dataMaxima != null && !dataMaxima.isEmpty())) {
            
                LocalDate dataInicial = conversaoData(dataMinima);
                LocalDate dataFinal = conversaoData(dataMaxima);
                
                predicates.add(criteriaBuilder.between(root.get("noticia").get("publicadoEm"), dataInicial, dataFinal));
            
            } else if (dataMinima != null && !dataMinima.isEmpty()) {
                LocalDate data = conversaoData(dataMinima);
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("noticia").get("publicadoEm"), data));
            } else if (dataMaxima != null && !dataMaxima.isEmpty()) {
                LocalDate data = conversaoData(dataMaxima);
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("noticia").get("publicadoEm"), data));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
    
    public static LocalDate conversaoData(String data) {
        String dataAntiga = data;
        DateTimeFormatter formatoEntrada = DateTimeFormatter
                .ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoSaida = DateTimeFormatter
                .ofPattern("yyyy-MM-dd");
        
        LocalDate dataNova = LocalDate.parse(dataAntiga, formatoEntrada);
        String conversao = dataNova.format(formatoSaida);
        
        return dataNova;
    }
    
}
