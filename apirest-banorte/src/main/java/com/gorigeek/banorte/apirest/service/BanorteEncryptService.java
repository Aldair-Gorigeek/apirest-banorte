package com.gorigeek.banorte.apirest.service;

import java.util.Base64;

import com.gorigeek.banorte.apirest.security.EncryptUtils;



public class BanorteEncryptService {

    public String generarCadenaCifrada(String jsonData, String rsaPublicKeyBase64) throws Exception {
        byte[] aesKey = EncryptUtils.generateAESKey();
        byte[] iv = EncryptUtils.generateIV();
        String subcadena2 = EncryptUtils.encryptAES(jsonData, aesKey, iv);

        String aesParams = Base64.getEncoder().encodeToString(iv) + "::" +
                           Base64.getEncoder().encodeToString(aesKey) + "::passphrase";
        String subcadena1 = EncryptUtils.encryptRSA(aesParams, rsaPublicKeyBase64);

        return subcadena1 + ":::" + subcadena2;
    }

}
