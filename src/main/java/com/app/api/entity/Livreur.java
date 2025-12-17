package com.app.api.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "livreurs")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Livreur extends Person{
    private String vehicule;
    @OneToMany(mappedBy = "livreur",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Colis> colisLivrer;
    @ManyToOne
    private Zone zone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserInfo user;

}
