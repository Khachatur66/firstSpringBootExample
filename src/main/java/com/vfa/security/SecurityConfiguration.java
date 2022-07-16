package com.vfa.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(PasswordEncoder passwordEncoder,@Qualifier("myUserDetailsService") UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }


    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .and()
                .ignoring()
                .antMatchers(HttpMethod.GET, "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/v3/api-docs/**")
                .and()
                .ignoring()
                .antMatchers(HttpMethod.POST, "/auth/login")
                .and()
                .ignoring()
                .antMatchers(HttpMethod.PATCH, "/employee/password");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .and()
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }
}
