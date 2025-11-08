package com.app.api.specification;

import com.app.api.dto.colisDTO.ColisFilterDTO;
import com.app.api.entity.Colis;
import com.app.api.enums.ColisPriority;
import com.app.api.enums.ColisStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ColisSpecification {

    public static Specification<Colis> buildColisSpecification(ColisFilterDTO filters) {

        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(filters.getStatus() != null && !filters.getStatus().trim().isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("status"), ColisStatus.valueOf(filters.getStatus())));
            }
            if(filters.getPriority() != null && !filters.getPriority().trim().isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("priority"), ColisPriority.valueOf(filters.getPriority())));
            }
            if(filters.getVille() != null && !filters.getVille().trim().isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("villeDestination"),filters.getVille()));
            }
            if(filters.getZone() != null && !filters.getZone().trim().isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("zone").get("codePostal"),filters.getZone()));
            }
            if(filters.getSearch() != null && !filters.getSearch().trim().isEmpty()){
                String pattern = "%"+filters.getSearch().toLowerCase()+"%";
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),pattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("villeDestination")),pattern)
                ));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
