package org.energycompany.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.IOException;
import java.io.StringReader;
import java.security.PrivateKey;
import java.security.PublicKey;

@UtilityClass
@Slf4j
public class KeyConverter {

    public PublicKey convertPublicKey(final String publicPemKey) {

        final StringReader keyReader = new StringReader(publicPemKey);
        try {
            SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo
                    .getInstance(new PEMParser(keyReader).readObject());
            return new JcaPEMKeyConverter().getPublicKey(publicKeyInfo);
        } catch (IOException exception) {
            log.error("Error converting public key to PEM", exception);
            throw new RuntimeException(exception);
        }

    }

    public PrivateKey convertPrivateKey(final String privatePemKey) {

        StringReader keyReader = new StringReader(privatePemKey);
        try {
            PrivateKeyInfo privateKeyInfo = PrivateKeyInfo
                    .getInstance(new PEMParser(keyReader).readObject());
            return new JcaPEMKeyConverter().getPrivateKey(privateKeyInfo);
        } catch (IOException exception) {
            log.error("Error converting private key to PEM", exception);
            throw new RuntimeException(exception);
        }

    }

}
