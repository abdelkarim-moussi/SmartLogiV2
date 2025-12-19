package com.app.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "clients_expediteurs")
@NoArgsConstructor
@AllArgsConstructor
public class ClientExpediteur extends Person{
    @Column(nullable = false)
    private String adresse;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "clientExpediteur",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Colis> colisEnvoyer;

}
