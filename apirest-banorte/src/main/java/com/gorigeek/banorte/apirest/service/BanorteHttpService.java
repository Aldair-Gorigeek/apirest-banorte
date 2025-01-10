package com.gorigeek.banorte.apirest.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BanorteHttpService {

    public String enviarCadenaCifrada(String cadenaCifrada, String url) throws Exception {
        // Crear cliente HTTP
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // Configurar la solicitud POST
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json");
            post.setHeader("User-Agent", "Mozilla/5.0");

            // Crear el cuerpo de la solicitud con la cadena cifrada
            StringEntity entity = new StringEntity("{\"Params\":\"" + cadenaCifrada + "\"}");
            post.setEntity(entity);

            // Enviar la solicitud
            HttpResponse response = client.execute(post);

            // Leer la respuesta
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                StringBuilder responseContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                return responseContent.toString();
            }
        }
    }
}
