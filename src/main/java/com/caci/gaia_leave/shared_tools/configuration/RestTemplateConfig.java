package com.caci.gaia_leave.shared_tools.configuration;

import org.apache.hc.client5.http.config.TlsConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Configuration
public class RestTemplateConfig {

    private final TLS[] protocols = new TLS[]{
            TLS.V_1_0,
            TLS.V_1_1,
            TLS.V_1_2,
            TLS.V_1_3
    };

    /**
     * Rest template SSL configuration.
     *
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

        TlsConfig tlsConfig = TlsConfig.custom()
                .setHandshakeTimeout(Timeout.ofSeconds(30))
                .setSupportedProtocols(protocols)
                .build();
        HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
                .setDefaultTlsConfig(tlsConfig)
                .build();

        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpclient);

        return new RestTemplate(requestFactory);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
