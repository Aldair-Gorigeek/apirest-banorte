package com.gorigeek.banorte.apirest.entity;

import org.json.JSONObject;

public class BanorteResponse {
    private String resultadoPayw;
    private String referencia;
    private String texto;
    private String codigoPayw;

    // Constructor para inicializar desde un JSON
    public BanorteResponse(String json) {
        JSONObject jsonResponse = new JSONObject(json);
        this.resultadoPayw = jsonResponse.optString("resultadoPayw");
        this.referencia = jsonResponse.optString("referencia");
        this.texto = jsonResponse.optString("texto");
        this.codigoPayw = jsonResponse.optString("codigoPayw");
    }

    // Getters
    public String getResultadoPayw() {
        return resultadoPayw;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getTexto() {
        return texto;
    }

    public String getCodigoPayw() {
        return codigoPayw;
    }

    @Override
    public String toString() {
        return "Resultado: " + resultadoPayw + ", Referencia: " + referencia +
               ", Texto: " + texto + ", Código: " + codigoPayw;
    }
}

