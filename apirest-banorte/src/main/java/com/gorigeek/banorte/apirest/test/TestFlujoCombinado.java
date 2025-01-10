package com.gorigeek.banorte.apirest.test;

import com.gorigeek.banorte.apirest.service.BanortePayment;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class TestFlujoCombinado {
    public static void main(String[] args) {
        try {
            // Paso 1: Simular el flujo de pago
            BanortePayment payment = new BanortePayment();
            String montoComision = "1.01";
            String tarjetaDecrypt = "4111111111111111"; // Tarjeta de prueba
            String vencimiento = "1225"; // MMYY
            String cvvDecrypt = "123";
            String verificar3DSecure = "SeUsa"; // Simular flujo 3D Secure
            String XID = "sampleXID123";
            String CAVV = "sampleCAVV123";
            String ECI = "05";
            String status3D = "A";

            String resultadoPago = payment.procesaPago(
                    montoComision, tarjetaDecrypt, vencimiento, cvvDecrypt,
                    verificar3DSecure, XID, CAVV, ECI, status3D
            );

            // Imprimir el resultado del pago
            System.out.println("Resultado del Pago: " + resultadoPago);

            // Paso 2: Simular la redirección del flujo 3D Secure
            simulateSecure3DResponse(XID, CAVV, ECI, status3D);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void simulateSecure3DResponse(String XID, String CAVV, String ECI, String status3D) {
        // Simulación de la llamada al endpoint Secure3D
        System.out.println("Simulando la redirección al flujo 3D Secure...");

        try {
            // Crear una solicitud HTTP POST
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost("http://localhost:8080/api/banorte/secure3d");

            // Configurar parámetros simulados
            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("XID", XID));
            urlParameters.add(new BasicNameValuePair("CAVV", CAVV));
            urlParameters.add(new BasicNameValuePair("ECI", ECI));
            urlParameters.add(new BasicNameValuePair("Status", status3D));

            post.setEntity(new UrlEncodedFormEntity(urlParameters));

            // Enviar la solicitud
            HttpResponse response = client.execute(post);

            // Leer y mostrar la respuesta
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder responseContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }

            System.out.println("Respuesta del flujo 3D Secure: " + responseContent.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
