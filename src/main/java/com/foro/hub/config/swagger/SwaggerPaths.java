package com.foro.hub.config.swagger;

import java.util.List;

public class SwaggerPaths {
    public static List<String> PATHS = List.of(
            "/swagger-ui",
            "/swagger-ui/",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**"
    );

    public static String[] PATHS_ARRAY() {
        return PATHS.toArray(new String[0]);
    }
}
