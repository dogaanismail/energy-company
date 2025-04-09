package org.energycompany.config;

import lombok.Getter;
import org.energycompany.model.customer.enums.ConfigurationParameter;
import org.energycompany.utils.KeyConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.PrivateKey;
import java.security.PublicKey;

@Getter
@Configuration
public class TokenConfigurationParameter {

    private final int accessTokenExpireMinute;
    private final int refreshTokenExpireDay;

    public TokenConfigurationParameter() {

        this.accessTokenExpireMinute = Integer.parseInt(
                ConfigurationParameter.AUTH_ACCESS_TOKEN_EXPIRE_MINUTE.getDefaultValue()
        );

        this.refreshTokenExpireDay = Integer.parseInt(
                ConfigurationParameter.AUTH_REFRESH_TOKEN_EXPIRE_DAY.getDefaultValue()
        );

    }

    @Bean
    public PublicKey publicKey() {
        // Convert the PEM string into a PublicKey just once at startup
        return KeyConverter.convertPublicKey(
                ConfigurationParameter.AUTH_PUBLIC_KEY.getDefaultValue()
        );
    }

    @Bean
    public PrivateKey privateKey() {
        // Convert the PEM string into a PrivateKey just once at startup
        return KeyConverter.convertPrivateKey(
                ConfigurationParameter.AUTH_PRIVATE_KEY.getDefaultValue()
        );
    }


}
