package com.adeies.adeies.enterprise.config;

import com.adeies.adeies.enterprise.auth.CookieAuthenticationFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

import static com.adeies.adeies.enterprise.enums.Permission.*;
import static com.adeies.adeies.enterprise.enums.Role.ADMIN;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**", "/v2/api-docs",
            "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**",
            "/configuration/ui", "/configuration/security", "/swagger-ui/**", "/webjars/**",
            "/swagger-ui.html", "/api/v1/**"};
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(req -> req.requestMatchers(WHITE_LIST_URL).permitAll()
                                             .requestMatchers("/api/v1/management/**")
                                             .hasAnyRole(ADMIN.name())
                                             .requestMatchers(GET, "/api/v1/management/**")
                                             .hasAnyAuthority(ADMIN_READ.name(),
                                                              MANAGER_READ.name())
                                             .requestMatchers(POST, "/api/v1/management/**")
                                             .hasAnyAuthority(ADMIN_CREATE.name(),
                                                              MANAGER_CREATE.name())
                                             .requestMatchers(PUT, "/api/v1/management/**")
                                             .hasAnyAuthority(ADMIN_UPDATE.name(),
                                                              MANAGER_UPDATE.name())
                                             .requestMatchers(DELETE, "/api/v1/management/**")
                                             .hasAnyAuthority(ADMIN_DELETE.name(),
                                                              MANAGER_DELETE.name()).anyRequest()
                                             .authenticated())
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(corsFilter(),UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new CookieAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class)
            .logout(logout -> logout.logoutUrl("/api/v1/auth/logout")
                                    .addLogoutHandler(logoutHandler).logoutSuccessHandler(
                            (request, response, authentication) ->
                            {
                                response.addCookie(createExpiredCookie("Access-Token"));
                                response.addCookie(createExpiredCookie("Refresh-Token"));
                                SecurityContextHolder.clearContext();
                            }
                            ))
            .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    private Cookie createExpiredCookie(String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0); // Set the cookie expiration to 0 seconds
        cookie.setPath("/");
        cookie.setSecure(true); // Enable for HTTPS
        cookie.setHttpOnly(true);
        return cookie;
    }
    @Bean
    org.springframework.web.filter.CorsFilter corsFilter (){
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.addAllowedOrigin("http://localhost:4200");
            configuration.setAllowedMethods(Arrays.asList("PUT","POST","GET","DELETE","OPTIONS"));
            configuration.setAllowedHeaders(Arrays.asList("*")); // change headers to autho 
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**",configuration);
            return  new CorsFilter(source);
        }


}
