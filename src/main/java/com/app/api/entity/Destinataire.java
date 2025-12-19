package com.app.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "destinataires")
@AllArgsConstructor
@NoArgsConstructor
public class Destinataire extends Person{

    private String adresse;
    @OneToMany(mappedBy = "destinataire",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Colis> colisRecus;

}
