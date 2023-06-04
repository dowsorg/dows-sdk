package org.dows.sdk.weixin.auth.cipher;

import org.dows.sdk.client.util.SignConstant;

import java.security.PrivateKey;

/**
 * RSA签名器
 */
public final class RSASigner extends AbstractSigner {

    public RSASigner(String certificateSerialNumber, PrivateKey privateKey) {
        super("SHA256-RSA2048", SignConstant.SHA256WITHRSA, certificateSerialNumber, privateKey);
    }
}
