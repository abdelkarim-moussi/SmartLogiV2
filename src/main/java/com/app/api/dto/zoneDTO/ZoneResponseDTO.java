package com.app.api.dto.zoneDTO;

import com.app.api.entity.Colis;
import com.app.api.entity.Livreur;

import java.util.List;

public class ZoneResponseDTO {

    private String id;
    private String codePostal;
    private List<Colis> colisList;
    private List<Livreur> livreurList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public List<Colis> getColisList() {
        return colisList;
    }

    public void setColisList(List<Colis> colisList) {
        this.colisList = colisList;
    }

    public List<Livreur> getLivreurList() {
        return livreurList;
    }

    public void setLivreurList(List<Livreur> livreurList) {
        this.livreurList = livreurList;
    }
}
