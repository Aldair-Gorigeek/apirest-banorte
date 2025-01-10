package com.gorigeek.banorte.apirest.security;

import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class CertificateUtils {

    public static String extractPublicKey(String certificatePath) throws Exception {
        // Leer el archivo .cer
        FileInputStream fis = new FileInputStream(certificatePath);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(fis);

        // Extraer la clave pública
        PublicKey publicKey = cert.getPublicKey();

        // Codificar en Base64
        byte[] publicKeyBytes = publicKey.getEncoded();
        return Base64.getEncoder().encodeToString(publicKeyBytes);
    }
}

