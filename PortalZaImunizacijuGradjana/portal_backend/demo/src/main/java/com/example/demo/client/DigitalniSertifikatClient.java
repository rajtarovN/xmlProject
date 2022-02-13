package com.example.demo.client;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;

@org.springframework.stereotype.Service
public class DigitalniSertifikatClient {

    public static String BASE_URL = "http://localhost:8082/api";

    public static String URL_ENCODING = "UTF-8";

    public DigitalniSertifikatClient() {

    }

    public static String getStringFromInputStream(InputStream in) throws Exception {
        return new String(IOUtils.toByteArray(in), URL_ENCODING);
    }
}
