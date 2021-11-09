package com.kapcb.framework.security.config;

import com.kapcb.framework.common.constants.enums.StringPool;
import com.kapcb.framework.security.filter.CustomAuthenticationFilter;
import com.kapcb.framework.security.filter.JwtAuthenticationFilter;
import com.kapcb.framework.security.handler.CustomAuthenticationFailureHandler;
import com.kapcb.framework.security.handler.CustomAuthenticationSuccessHandler;
import com.kapcb.framework.security.handler.CustomLogoutSuccessHandler;
import com.kapcb.framework.security.handler.RestAuthenticationEntryPoint;
import com.kapcb.framework.security.handler.RestfulAccessDeniedHandler;
import com.kapcb.framework.security.properties.SecurityIgnoreProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * <a>Title: CustomSecurityConfiguration </a>
 * <a>Author: Kapcb <a>
 * <a>Description: CustomSecurityConfiguration <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/6 16:09
 */
@Slf4j
@RequiredArgsConstructor
public class CustomSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final SecurityIgnoreProperties securityIgnoreProperties;
    private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    private final CustomAuthenticationFilter authenticationFilter;
    private final CustomLogoutSuccessHandler logoutSuccessHandler;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        if (CollectionUtils.isNotEmpty(securityIgnoreProperties.getIgnoreUrlList())) {
            for (String ignoreUrl : securityIgnoreProperties.getIgnoreUrlList()) {
                registry.antMatchers(ignoreUrl).permitAll();
            }
        }

        registry.antMatchers(HttpMethod.OPTIONS).permitAll();

        registry.and()
                .formLogin()
                .loginProcessingUrl("/login");

        registry.and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        authenticationFilter.setAuthenticationManager(authenticationManager());
        return authenticationFilter;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin(StringPool.STAR.value());
        corsConfiguration.addAllowedHeader(StringPool.STAR.value());
        corsConfiguration.addAllowedMethod(StringPool.STAR.value());
        corsConfiguration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

}
