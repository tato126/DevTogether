package com.BE_13.DevTogether.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserAuthenticationFailureHandler userAuthenticationFailureHandler() {
        return new UserAuthenticationFailureHandler();
    }

    /*
        ProviderManager의 구현체를 가져오는 역할
        DaoAuthenticationProvider가 내부에서 loadUserByUsername() 호출하여 정보 조회,
        PasswordEncoder로 비교

        스프링 시큐리티 5.7 이상부터는 커스텀, jwt, 다양한 인증 방식 등을 사용하는 게 아니라면 시큐리티가 아래 빈을 자동으로 등록해준다
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    */


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/posts", "/api/posts/**").permitAll()
                        .requestMatchers("/api/user").permitAll()
                        .requestMatchers("/api/test").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
//                 추후 토큰 도입 시 고도화를 위하여 컨트롤러단에서 로그인 조정하겠음
//                .formLogin(login -> login
//                        .loginPage("/api/user/login") // 로그인 페이지
//                        .loginProcessingUrl("/api/user/login-submit") // 로그인 요청 처리 URL
//                        //.failureUrl("login?error=true") 로그인 실패시 url 설정
//                        .failureHandler(userAuthenticationFailureHandler())
//                        .defaultSuccessUrl("/api/posts", true)
//                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/api/posts")
                );

        return http.build();
    }

    @Bean
    @Profile({"test", "dev"})
    public WebSecurityCustomizer h2ConsoleCustomizer() {
        return web -> web.ignoring().requestMatchers("/h2-console/**");
    }

    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",
                "http://localhost:4173",
                "https://devtogether.site"
        ));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
