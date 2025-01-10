package com.gorigeek.banorte.apirest.controllers;

import com.gorigeek.banorte.apirest.security.CertificateUtils;
import com.gorigeek.banorte.apirest.service.BanorteEncryptService;
import com.gorigeek.banorte.apirest.service.BanorteHttpService;
import com.gorigeek.banorte.apirest.entity.BanorteResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/banorte")
public class BanorteController {

	@PostMapping("/procesarPago")
	public Map<String, Object> procesarPago(@RequestBody Map<String, String> datosPago) {
	    Map<String, Object> respuesta = new HashMap<>();
	    try {
	        String certificatePath = "src/main/resources/static/multicobros.cer";
	        String publicKey = CertificateUtils.extractPublicKey(certificatePath);

	        String jsonData = String.format("{ \"merchantId\": \"%s\", \"amount\": \"%s\", \"controlNumber\": \"%s\" }",
	                datosPago.get("merchantId"),
	                datosPago.get("amount"),
	                datosPago.get("controlNumber"));

	        BanorteEncryptService encryptService = new BanorteEncryptService();
	        String cadenaCifrada = encryptService.generarCadenaCifrada(jsonData, publicKey);

	        String url = "https://via.pagosbanorte.com/payw2";

	        BanorteHttpService httpService = new BanorteHttpService();
	        String respuestaBanorte = httpService.enviarCadenaCifrada(cadenaCifrada, url);

	        System.out.println("Respuesta sin procesar de Banorte: " + respuestaBanorte);

	        // Verificar si la respuesta es JSON
	        if (respuestaBanorte.trim().startsWith("{")) {
	            BanorteResponse banorteResponse = new BanorteResponse(respuestaBanorte);
	            respuesta.put("success", true);
	            respuesta.put("data", banorteResponse);
	        } else {
	            respuesta.put("success", false);
	            respuesta.put("rawResponse", respuestaBanorte);
	            respuesta.put("message", "La respuesta no es JSON. Verifica los parámetros.");
	        }
	    } catch (Exception e) {
	        respuesta.put("success", false);
	        respuesta.put("error", e.getMessage());
	        e.printStackTrace();
	    }
	    return respuesta;
	}

}
