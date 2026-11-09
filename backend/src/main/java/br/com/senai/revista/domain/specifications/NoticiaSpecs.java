package br.com.senai.revista.domain.specifications;

import br.com.senai.revista.domain.model.Noticia;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author DIGMA
 */
public class NoticiaSpecs {
    
     public static Specification<Noticia> comFiltros(
            String titulo, 
            String categoria, 
            String status, 
            String autor,
            String dataMinima,
            String dataMaxima
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (titulo != null && !titulo.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(
                                root.get("titulo")), "%" + titulo.toLowerCase() + "%"));
            }
            
            if (categoria != null && !categoria.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("categoria").get("nome"), categoria));
            }
            
            if (status != null && !status.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            
            if (autor != null && !autor.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("autor").get("nome")), "%" + autor.toLowerCase() + "%"));
            }
            
            if ((dataMinima != null && !dataMinima.isEmpty()) &&
                    (dataMaxima != null && !dataMaxima.isEmpty())) {
            
                LocalDate dataInicial = conversaoData(dataMinima);
                LocalDate dataFinal = conversaoData(dataMaxima);
                
                predicates.add(criteriaBuilder.between(root.get("publicadoEm"), dataInicial, dataFinal));
            
            } else if (dataMinima != null && !dataMinima.isEmpty()) {
                LocalDate data = conversaoData(dataMinima);
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("publicadoEm"), data));
            } else if (dataMaxima != null && !dataMaxima.isEmpty()) {
                LocalDate data = conversaoData(dataMaxima);
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("publicadoEm"), data));
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
