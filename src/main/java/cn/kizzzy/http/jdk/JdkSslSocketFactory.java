package cn.kizzzy.http.jdk;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class JdkSslSocketFactory {
    
    private SSLContext context;
    
    public SSLSocketFactory getSocketFactory() throws NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        if (context == null) {
            context = SSLContext.getInstance("TLS");
            context.init(null, new TrustManager[]{
                new X509TrustManager() {
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
            }, new SecureRandom());
        }
        
        return context.getSocketFactory();
    }
}
