package com.app.api.dto.zoneDTO;

import jakarta.validation.constraints.NotNull;

public class ZoneRequestDTO {
    @NotNull(message = "entrer un code postal valide")
    private String codePostal;

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }
}
