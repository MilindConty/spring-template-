package com.amic.security.springsecurity.config;



import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.security.KeyStore;

//@Configuration
@Slf4j
public class SSLContextConfig {

    public static final String SUN_X_509 = "SunX509";
    public static final String SUN_JSSE = "SunJSSE";
    public static final String TLS = "TLS";
    @Value("${microservice.pki.path}")
    private String keyStoreFile;

    @Value("${microservice.pki.password}")
    private String keyStorePassword;

    @Value("${microservice.pki.type}")
    private String keyStoreType;

    @Value("${microservice.trust.path}")
    private String trustStoreFile;

    @Value("${microservice.trust.password}")
    private String trustPassWord;

    @Value("${microservice.trust.type}")
    private String trustStoreType;


    @Bean(name = "MicroServiceSSLCtx")
    public SSLContext getSSLContext(){
        log.info("Creating SSL Context with KeyStore {} TrustStore {}", keyStoreFile, trustStoreFile);

        try
        {
            /*
             * load TrustStore File.
             */
            final KeyStore trustStore = KeyStore.getInstance(trustStoreType);
            InputStream trustStoreinputStream = new FileInputStream(trustStoreFile);
            trustStore.load(trustStoreinputStream, trustPassWord.toCharArray());
            final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(SUN_X_509, SUN_JSSE);
            trustManagerFactory.init(trustStore);

            /*
            * Load KeyStore File
             */
            final KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            InputStream keyStoreinputStream = new FileInputStream(keyStoreFile);
            keyStore.load(keyStoreinputStream, keyStorePassword.toCharArray());
            final KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(SUN_X_509);
            keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());

            SSLContext sslContext = SSLContext.getInstance(TLS);
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(),null);
            return sslContext;
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Bean(name = "MicroServiceSSLRestTemplate")
    public RestTemplate getClientRequestFactor(){
        return new RestTemplate(new SimpleClientHttpRequestFactory() {
            protected void prepareConnection(final HttpURLConnection connection, final String httpMethod){
                try{
                    final HttpsURLConnection httpsURLConnection = (HttpsURLConnection) connection;
                    httpsURLConnection.setSSLSocketFactory(getSSLContext().getSocketFactory());
                    httpsURLConnection.setHostnameVerifier((String hostname, SSLSession session) -> true);
                    HttpsURLConnection.setDefaultHostnameVerifier((String hostname, SSLSession session) -> true);
                    super.prepareConnection(connection, httpMethod);
                }catch (final Exception ex){
                    throw new RuntimeException("Unable to Create the Client Http Request with SSL configuration", ex);
                }
            }
        });
    }

}
