package org.dows.sdk.weixin.auth.certificate;

import org.dows.sdk.client.certificate.CertificateHandler;
import org.dows.sdk.client.cipher.AeadCipher;
import org.dows.sdk.client.http.AbstractHttpClientBuilder;
import org.dows.sdk.client.http.DefaultHttpClientBuilder;
import org.dows.sdk.client.http.HttpClient;
import org.dows.sdk.client.http.HttpHeaders;
import org.dows.sdk.client.security.Credential;
import org.dows.sdk.client.security.Validator;
import org.dows.sdk.weixin.auth.base.WechatPay2Credential;
import org.dows.sdk.weixin.auth.cipher.AeadAesCipher;
import org.dows.sdk.weixin.auth.cipher.RSASigner;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

/**
 * RSA自动更新平台证书提供器
 */
public class RSAAutoCertificateProvider extends AbstractAutoCertificateProvider {
    // 证书Map
    private static final Map<String, Map<String, X509Certificate>> wechatPayCertificateMap = new ConcurrentHashMap<>();
    // 证书处理器
    private static final CertificateHandler rsaCertificateHandler = new RSACertificateHandler();
    // 下载证书url
    private static final String REQUEST_URL = "https://api.mch.weixin.qq.com/v3/certificates?algorithm_type=RSA";

    private RSAAutoCertificateProvider(String merchantId, AeadCipher aeadCipher, HttpClient httpClient) {
        super(REQUEST_URL, rsaCertificateHandler, aeadCipher, httpClient, merchantId, wechatPayCertificateMap);
    }

    public static class Builder {
        private final Validator emptyValidator =
                new Validator() {
                    @Override
                    public boolean validate(HttpHeaders responseHeaders, String body) {
                        return true;
                    }
                };
        private String merchantId;
        private byte[] apiV3Key;
        private Credential credential;
        private PrivateKey privateKey;
        private String merchantSerialNumber;
        private HttpClient httpClient;
        private AbstractHttpClientBuilder<?> httpClientBuilder;

        public Builder merchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        public Builder apiV3Key(byte[] apiV3Key) {
            this.apiV3Key = apiV3Key;
            return this;
        }

        public Builder httpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Builder credential(Credential credential) {
            this.credential = credential;
            return this;
        }

        public Builder privateKey(PrivateKey privateKey) {
            this.privateKey = privateKey;
            return this;
        }

        public Builder merchantSerialNumber(String merchantSerialNumber) {
            this.merchantSerialNumber = merchantSerialNumber;
            return this;
        }

        public Builder httpClientBuilder(AbstractHttpClientBuilder<?> builder) {
            // httpClientBuilder 不是不可变的，所以为了避免过程中修改入参或者值发生变化，这里制作了一个副本
            this.httpClientBuilder = builder.newInstance();
            return this;
        }

        public RSAAutoCertificateProvider build() {
            if (httpClient == null) {
                if (httpClientBuilder == null) {
                    httpClientBuilder = new DefaultHttpClientBuilder();
                }

                if (credential == null && privateKey != null) {
                    credential = new WechatPay2Credential(requireNonNull(merchantId),
                            new RSASigner(requireNonNull(merchantSerialNumber), privateKey));
                }
                httpClient = httpClientBuilder.credential(credential).validator(emptyValidator).build();
            }
            return new RSAAutoCertificateProvider(merchantId, new AeadAesCipher(requireNonNull(apiV3Key)), httpClient);
        }
    }
}
