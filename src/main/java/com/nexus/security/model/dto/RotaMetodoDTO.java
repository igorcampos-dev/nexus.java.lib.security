package com.nexus.security.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpMethod;

@Data
@AllArgsConstructor
public class RotaMetodoDTO {
    private String rota;
    private HttpMethod metodo;
}
