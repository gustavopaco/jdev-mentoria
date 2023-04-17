package com.pacoprojects.ssl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.net.ssl.*;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
// Classe nao utilizada pois a classe RestTemplate consegue enviar requisicao Https
public class IgnoreHostNameSSL implements HostnameVerifier {

    private static final HostnameVerifier DEFAULT_HOST_NAME_VERIFIER = HttpsURLConnection.getDefaultHostnameVerifier();
    private Set<String> trustedHosts;

    @Override
    public boolean verify(String hostName, SSLSession sslSession) {

        if (trustedHosts.contains(hostName)) {
            return  true;
        } else {
            return DEFAULT_HOST_NAME_VERIFIER.verify(hostName, sslSession);
        }
    }

    public static HostnameVerifier getDefaultHostNameVerifier() throws NoSuchAlgorithmException, KeyManagementException {

        TrustManager[] trustManagers = new TrustManager[] {
                new X509ExtendedTrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {

                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {

                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };

        SSLContext sslContext = SSLContext.getInstance("SSL");

        sslContext.init(null, trustManagers, new SecureRandom());

        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        return new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
    }
}
