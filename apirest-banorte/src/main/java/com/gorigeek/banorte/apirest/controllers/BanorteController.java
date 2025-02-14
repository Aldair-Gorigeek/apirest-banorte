package com.gorigeek.banorte.apirest.controllers;

import com.gorigeek.banorte.apirest.security.CertificateUtils;
import com.gorigeek.banorte.apirest.service.BanorteEncryptService;
import com.gorigeek.banorte.apirest.service.BanorteHttpService;
import com.gorigeek.banorte.apirest.entity.BanorteResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/banorte")
public class BanorteController {

    @PostMapping("/procesarPago")
    public ResponseEntity<Map<String, Object>> procesarPago(@RequestBody Map<String, String> datosPago) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            // Ruta del certificado
            String certificatePath = "src/main/resources/static/multicobros.cer";
            
            // Extraer llave pública
            String publicKey = CertificateUtils.extractPublicKey(certificatePath);

            // Generar JSON con los datos recibidos
            String jsonData = String.format(
                "{ \"merchantId\": \"%s\", \"name\": \"%s\", \"password\": \"%s\", \"mode\": \"%s\", \"controlNumber\": \"%s\", \"terminalId\": \"%s\", \"amount\": \"%s\", \"merchantName\": \"%s\", \"merchantCity\": \"%s\", \"lang\": \"%s\" }",
                datosPago.get("merchantId"),
                datosPago.get("name"),
                datosPago.get("password"),
                datosPago.get("mode"),
                datosPago.get("controlNumber"),
                datosPago.get("terminalId"),
                datosPago.get("amount"),
                datosPago.get("merchantName"),
                datosPago.get("merchantCity"),
                datosPago.get("lang")
            );

            // Generar cadena cifrada
            BanorteEncryptService encryptService = new BanorteEncryptService();
            String cadenaCifrada = encryptService.generarCadenaCifrada(jsonData, publicKey);

            // URL de prueba de Banorte
            String url = "https://via.pagosbanorte.com/payw2";

            // Enviar cadena cifrada al servidor de Banorte
            BanorteHttpService httpService = new BanorteHttpService();
            String respuestaBanorte = httpService.enviarCadenaCifrada(cadenaCifrada, url);

            // Analizar respuesta de Banorte
            if (respuestaBanorte.trim().startsWith("{")) {
                BanorteResponse banorteResponse = new BanorteResponse(respuestaBanorte);
                respuesta.put("success", true);
                respuesta.put("data", banorteResponse);
            } else {
                respuesta.put("success", false);
                respuesta.put("rawResponse", respuestaBanorte);
                respuesta.put("message", "La respuesta no es JSON. Verifica los parámetros.");
            }
            respuesta.put("cadenaCifrada", cadenaCifrada);
            respuesta.put("urlEnviada", url);
            respuesta.put("respuestaBanorte", respuestaBanorte);

            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            // Manejo de excepciones
            respuesta.put("success", false);
            respuesta.put("error", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(respuesta);
        }
    }
}
