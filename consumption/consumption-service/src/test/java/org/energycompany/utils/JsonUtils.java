package org.energycompany.utils;

import java.nio.charset.Charset;

import static org.springframework.util.StreamUtils.copyToString;

public final class JsonUtils {

    public static String getJsonString(String resourceFilePath) throws Exception {
        return copyToString(JsonUtils.class.getClassLoader().getResourceAsStream(resourceFilePath),
                Charset.defaultCharset());
    }
}
