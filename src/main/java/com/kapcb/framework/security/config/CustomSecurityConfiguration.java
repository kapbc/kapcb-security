package com.kapcb.framework.security.config;

import cn.hutool.http.ContentType;
import com.alibaba.fastjson.JSON;
import com.kapcb.framework.common.constants.enums.StringPool;
import com.kapcb.framework.security.filter.CustomAuthenticationFilter;
import com.kapcb.framework.security.filter.JwtAuthenticationFilter;
import com.kapcb.framework.security.handler.CustomAuthenticationFailureHandler;
import com.kapcb.framework.security.handler.CustomAuthenticationSuccessHandler;
import com.kapcb.framework.security.handler.CustomLogoutSuccessHandler;
import com.kapcb.framework.security.handler.RestAuthenticationEntryPoint;
import com.kapcb.framework.security.handler.RestfulAccessDeniedHandler;
import com.kapcb.framework.security.properties.SecurityIgnoreProperties;
import com.kapcb.framework.security.properties.ValidateCodeProperties;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
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
public class CustomSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        if (CollectionUtils.isNotEmpty(securityIgnoreProperties().getIgnoreUrlList())) {
            for (String ignoreUrl : securityIgnoreProperties().getIgnoreUrlList()) {
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
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler())
                .clearAuthentication(true)
                .logoutRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher("/logout", "GET"),
                        new AntPathRequestMatcher("/logout1", "POST")
                ))
                .defaultLogoutSuccessHandlerFor((request, response, auth) -> {
                    response.setContentType(ContentType.JSON.getValue());
                    Map<String, String> resultMap = HashMap.of("msg", "logout success!", "data", "kapcb logout success!", "code", "200");
                    response.getWriter().write(JSON.toJSONString(resultMap));
                    response.getWriter().flush();
                    response.getWriter().close();
                }, new AntPathRequestMatcher("/logout", "GET"))
                .defaultLogoutSuccessHandlerFor((request, response, auth) -> {
                    response.setContentType(ContentType.JSON.getValue());
                    Map<String, String> resultMap = HashMap.of("msg", "logout1 success!", "data", "kapcb logout1 success!", "code", "200");
                    response.getWriter().write(JSON.toJSONString(resultMap));
                    response.getWriter().flush();
                    response.getWriter().close();
                }, new AntPathRequestMatcher("/logout1", "POST"))
                .and()
                .exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler())
                .authenticationEntryPoint(restAuthenticationEntryPoint())
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter();
        authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        authenticationFilter.setAuthenticationManager(authenticationManager());
        return authenticationFilter;
    }

    @Bean
    public SecurityIgnoreProperties securityIgnoreProperties() {
        return new SecurityIgnoreProperties();
    }

    @Bean
    public ValidateCodeProperties validateCodeProperties() {
        return new ValidateCodeProperties();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public RestfulAccessDeniedHandler accessDeniedHandler() {
        return new RestfulAccessDeniedHandler();
    }

    @Bean
    public CustomAuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public CustomAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
        return new RestfulAccessDeniedHandler();
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomLogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
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
