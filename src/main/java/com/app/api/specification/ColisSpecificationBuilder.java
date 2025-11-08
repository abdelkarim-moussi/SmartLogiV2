package com.app.api.specification;

import com.app.api.dto.colisDTO.ColisFiltersRequestDTO;
import com.app.api.dto.colisDTO.ColisRequestDTO;
import com.app.api.entity.Colis;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ColisSpecificationBuilder {
    private List<Specification<Colis>> specifications = new ArrayList<>();

    public ColisSpecificationBuilder with(Specification<Colis> specification){
        if(specification != null){
            specifications.add(specification);
        }
        return this;
    }

    public Specification<Colis> build(){
        return Specification.allOf(specifications);
    }
    public List<Specification<Colis>> fromFilter(ColisFiltersRequestDTO filter){
        return new ColisSpecificationBuilder()
                .with(ColisSpecif)
    }
}
