package org.energycompany.utils;

import lombok.experimental.UtilityClass;
import org.energycompany.model.JwtRecord;
import org.springframework.security.oauth2.jwt.Jwt;

@UtilityClass
public class JwtRecordConverter {

    public Jwt convertJwtRecordToJwt(JwtRecord jwtRecord) {
        return new Jwt(
                jwtRecord.tokenValue(),
                jwtRecord.issuedAt(),
                jwtRecord.expiresAt(),
                jwtRecord.headers(),
                jwtRecord.claims()
        );
    }

}
