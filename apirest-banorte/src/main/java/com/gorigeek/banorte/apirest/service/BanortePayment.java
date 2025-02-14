package com.gorigeek.banorte.apirest.service;

import com.gorigeek.banorte.apirest.entity.PagosBancariosBean;
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

public class BanortePayment {

	public String procesaPago(String montoComision, String tarjetaDecrypt, String vencimiento, String cvvDecrypt,
            String verificar3DSecure, String XID, String CAVV, String ECI, String status3D) {
		String responsePago = "";
		
		// Obtener propiedades configuradas
		PagosBancariosBean pagosBean = new ReadProperties().getPagosBanorteProperties();
		
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(pagosBean.getUrlBanco());
			post.setHeader("User-Agent", "Mozilla/5.0");
			
			// Construir los parámetros obligatorios
			List<NameValuePair> urlParameters = new ArrayList<>();
			urlParameters.add(new BasicNameValuePair("merchantId", pagosBean.getMerchant_id()));
			urlParameters.add(new BasicNameValuePair("name", pagosBean.getMerchant_user()));
			urlParameters.add(new BasicNameValuePair("password", pagosBean.getMerchant_pass()));
			urlParameters.add(new BasicNameValuePair("mode", pagosBean.getMode()));
			urlParameters.add(new BasicNameValuePair("controlNumber", "UNIQUE12345")); // Número único de control
			urlParameters.add(new BasicNameValuePair("terminalId", pagosBean.getTerminal_id()));
			urlParameters.add(new BasicNameValuePair("amount", montoComision)); // Monto de la transacción
			urlParameters.add(new BasicNameValuePair("merchantName", "My Store")); // Nombre del comercio
			urlParameters.add(new BasicNameValuePair("merchantCity", "Monterrey")); // Ciudad del comercio
			urlParameters.add(new BasicNameValuePair("lang", "ES")); // Idioma de la respuesta
			
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
			
			// Imprimir parámetros enviados para depuración
			System.out.println("Parámetros enviados:");
			urlParameters.forEach(param -> System.out.println(param.getName() + ": " + param.getValue()));
			
			// Enviar la solicitud y capturar la respuesta
			HttpResponse responseHttp = client.execute(post);
			
			if (responseHttp != null) {
			// Imprimir encabezados de la respuesta
			System.out.println("Encabezados de la respuesta:");
			for (org.apache.http.Header header : responseHttp.getAllHeaders()) {
			  System.out.println(header.getName() + ": " + header.getValue());
			}
			
			// Leer contenido de la respuesta
			BufferedReader reader = new BufferedReader(new InputStreamReader(responseHttp.getEntity().getContent()));
			StringBuilder responseContent = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
			  responseContent.append(line);
			}
			
			System.out.println("Contenido de la respuesta:");
			System.out.println(responseContent.toString());
			
			// Procesar encabezados específicos
			if (responseHttp.getFirstHeader("RESULTADO_PAYW") != null) {
			  String statusPago = responseHttp.getFirstHeader("RESULTADO_PAYW").getValue();
			  String fechaReturnPago = responseHttp.getFirstHeader("FECHA_RSP_CTE") != null ?
			          responseHttp.getFirstHeader("FECHA_RSP_CTE").getValue() : "";
			  String transaccion = statusPago.equals("A") ?
			          responseHttp.getFirstHeader("REFERENCIA") != null ?
			                  responseHttp.getFirstHeader("REFERENCIA").getValue() : "" : "";
			  responsePago = statusPago + "@" + fechaReturnPago + "@" + transaccion;
			} else {
			  responsePago = "R"; // Rechazado si no hay encabezado "RESULTADO_PAYW"
			}
			} else {
			responsePago = "C"; // Error de conexión
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			responsePago = "C";
		}
		
		return responsePago;
	}
}
