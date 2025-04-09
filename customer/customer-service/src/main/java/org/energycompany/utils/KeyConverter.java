package org.energycompany.utils;

import lombok.experimental.UtilityClass;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.IOException;
import java.io.StringReader;
import java.security.PrivateKey;
import java.security.PublicKey;

@UtilityClass
public class KeyConverter {

    public static PublicKey convertPublicKey(String publicPemKey) {
        try (
                StringReader keyReader = new StringReader(publicPemKey);
                PEMParser pemParser = new PEMParser(keyReader)
        ) {
            SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo
                    .getInstance(pemParser.readObject());

            return new JcaPEMKeyConverter()
                    .getPublicKey(subjectPublicKeyInfo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey convertPrivateKey(final String privatePemKey) {

        try (
                StringReader keyReader = new StringReader(privatePemKey);
                PEMParser pemParser = new PEMParser(keyReader)
        ) {
            PrivateKeyInfo privateKeyInfo = PrivateKeyInfo
                    .getInstance(pemParser.readObject());

            return new JcaPEMKeyConverter()
                    .getPrivateKey(privateKeyInfo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
