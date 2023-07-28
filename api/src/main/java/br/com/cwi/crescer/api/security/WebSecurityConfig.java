package br.com.cwi.crescer.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().cors()
                .and()
                .authorizeRequests()
                .antMatchers(POST, "/users").permitAll()
                .antMatchers(GET, "/activate-account").permitAll()
                .antMatchers(POST, "/activate-account").permitAll()
                .antMatchers(POST, "/forgot-password").permitAll()
                .antMatchers(POST, "/reset-password").permitAll()
                .antMatchers(POST, "/login").permitAll()
                .antMatchers(GET, "/static/**").permitAll()
                .antMatchers(POST, "/items").hasRole("ADMIN")
                .antMatchers(GET, "/ws/**").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic().authenticationEntryPoint((request, response, authException) -> {
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json");
                    response.setStatus(UNAUTHORIZED.value());
                    response.getOutputStream()
                            .println("{ \"erro\": \"" + "Credenciais invalidas." + "\" }");
                })
                .and()
                .logout().logoutSuccessHandler((request, response, authentication) -> response.setStatus(OK.value()));
        return http.build();
    }

}
