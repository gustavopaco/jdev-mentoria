//package com.pacoprojects.ssl;
//
//import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.config.DefaultClientConfig;
//import com.sun.jersey.client.urlconnection.HTTPSProperties;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.glassfish.jersey.media.multipart.internal.MultiPartWriter;
//
//import javax.net.ssl.*;
//import java.net.Socket;
//import java.security.KeyManagementException;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//import java.util.LinkedHashSet;
//import java.util.Map;
//import java.util.Set;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//// Classe nao utilizada pois a classe RestTemplate consegue enviar requisicao Https
//public class HostIgnoreClient {
//
//    private String hostname;
//
//    public Client hosIgnoringCLient() throws NoSuchAlgorithmException, KeyManagementException {
//
//        TrustManager[] trustManagers = new TrustManager[] {
//                new X509ExtendedTrustManager() {
//                    @Override
//                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {
//
//                    }
//
//                    @Override
//                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {
//
//                    }
//
//                    @Override
//                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {
//
//                    }
//
//                    @Override
//                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {
//
//                    }
//
//                    @Override
//                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//
//                    }
//
//                    @Override
//                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//
//                    }
//
//                    @Override
//                    public X509Certificate[] getAcceptedIssuers() {
//                        return new X509Certificate[0];
//                    }
//                }
//        };
//
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//
//        sslContext.init(null, trustManagers, new SecureRandom());
//
//        Set<String> hostNameList = new LinkedHashSet<>();
//
//        hostNameList.add(this.hostname);
//
//        HttpsURLConnection.setDefaultHostnameVerifier(new IgnoreHostNameSSL(hostNameList));
//
//        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
//
//        DefaultClientConfig config = new DefaultClientConfig();
//
//        Map<String,Object> map = config.getProperties();
//
//        HTTPSProperties httpsProperties = new HTTPSProperties(new HostnameVerifier() {
//            @Override
//            public boolean verify(String s, SSLSession sslSession) {
//                return true;
//            }
//        }, sslContext);
//
//        map.put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, httpsProperties);
//
//        config.getClasses().add(JacksonJsonProvider.class);
//        config.getClasses().add(MultiPartWriter.class);
//
//        return Client.create(config);
//    }
//
//}
