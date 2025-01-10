package com.gorigeek.banorte.apirest.service;

import com.gorigeek.banorte.apirest.entity.PagosBancariosBean;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {

    public PagosBancariosBean getPagosBanorteProperties() {
        PagosBancariosBean bean = new PagosBancariosBean();
        try (InputStream input = new FileInputStream("src/main/resources/comercio.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            bean.setUrlPost(properties.getProperty("urlBanco"));
            bean.setMerchant_id(properties.getProperty("Merchant_id"));
            bean.setMerchant_user(properties.getProperty("Merchant_user"));
            bean.setMerchant_pass(properties.getProperty("Merchant_pass"));
            bean.setCmd_trans(properties.getProperty("Cmd_trans"));
            bean.setTerminal_id(properties.getProperty("Terminal_id"));
            bean.setMode(properties.getProperty("Mode"));
            bean.setEntry_mode(properties.getProperty("Entry_mode"));
            bean.setResponse_language(properties.getProperty("Response_language"));
            bean.setEnvironment(properties.getProperty("environment"));
            bean.setSecure_plus_3d_version(properties.getProperty("Secure_Plus_3d_Version"));
            bean.setSecure3D(properties.getProperty("3Dsecure"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
}
