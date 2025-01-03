package com.adeies.adeies.enterprise.config;

import com.adeies.adeies.enterprise.auth.CookieAuthenticationFilter;
import com.adeies.adeies.enterprise.auth.OAuth2LoginSuccessHandler;

import com.adeies.adeies.enterprise.service.CustomOAuth2UserService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.oauth2.client.userinfo.DelegatingOAuth2UserService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration   {
    private static final String[] WHITE_LIST_URL = {
            "/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**",
            "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
            "/configuration/security", "/swagger-ui/**", "/webjars/**",
            "/swagger-ui.html", "/api/v1/oauth2/code/**"
    };
    private final LogoutHandler logoutHandler;
    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Autowired
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
//                        .requestMatchers("/api/v1/management/**")
//                        .hasAnyRole(ADMIN.name())
//                        .requestMatchers(GET, "/api/v1/management/**")
//                        .hasAnyAuthority(ADMIN_READ.name(),
//                                MANAGER_READ.name())
//                        .requestMatchers(POST, "/api/v1/management/**")
//                        .hasAnyAuthority(ADMIN_CREATE.name(),
//                                MANAGER_CREATE.name())
//                        .requestMatchers(PUT, "/api/v1/management/**")
//                        .hasAnyAuthority(ADMIN_UPDATE.name(),
//                                MANAGER_UPDATE.name())
//                        .requestMatchers(DELETE, "/api/v1/management/**")
//                        .hasAnyAuthority(ADMIN_DELETE.name(),
//                                MANAGER_DELETE.name()).anyRequest()
                        .requestMatchers("/api/v1/oauth2/code/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> {
                            userInfo.userService(customOAuth2UserService);
                            System.out.println("CustomOAuth2UserService has been set as the user service.");

                        })
                        .successHandler(oAuth2LoginSuccessHandler)
                )
                
//                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(new CookieAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.addCookie(createExpiredCookie("Access-Token"));
                            response.addCookie(createExpiredCookie("Refresh-Token"));
                            SecurityContextHolder.clearContext();
                        })
                );
        return http.build();
    }

//    @Bean
//    JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
//        grantedAuthoritiesConverter.setAuthorityPrefix("");
//
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
//        return jwtAuthenticationConverter;
//    }


    private Cookie createExpiredCookie(String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0); // Set the cookie expiration to 0 seconds
        cookie.setPath("/");
        cookie.setSecure(true); // Enable for HTTPS
        cookie.setHttpOnly(true);
        return cookie;
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource (){
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Arrays.asList("https://accounts.google.com/o/oauth2/v2/auth" , "http://localhost:4200" ));
            configuration.setAllowedMethods(Arrays.asList("PUT","POST","GET","DELETE","OPTIONS"));
            configuration.setAllowedHeaders(Arrays.asList("*")); // change headers to autho
            configuration.setAllowCredentials(true);
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**",configuration);
            return  source;
        }


}
