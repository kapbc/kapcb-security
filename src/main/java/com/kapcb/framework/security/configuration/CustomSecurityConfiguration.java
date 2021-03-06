package com.kapcb.framework.security.configuration;

import com.kapcb.framework.common.constants.enums.StringPool;
import com.kapcb.framework.security.annotation.AnonymousAccess;
import com.kapcb.framework.security.filter.CustomAuthenticationFilter;
import com.kapcb.framework.security.filter.JwtAuthenticationFilter;
import com.kapcb.framework.security.handler.CustomAuthenticationFailureHandler;
import com.kapcb.framework.security.handler.CustomAuthenticationSuccessHandler;
import com.kapcb.framework.security.handler.CustomLogoutSuccessHandler;
import com.kapcb.framework.security.handler.RestAuthenticationEntryPoint;
import com.kapcb.framework.security.handler.RestfulAccessDeniedHandler;
import com.kapcb.framework.security.properties.SecurityIgnoreProperties;
import com.kapcb.framework.security.properties.ValidateCodeProperties;
import com.kapcb.framework.web.context.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
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
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
        if (CollectionUtils.isNotEmpty(securityIgnoreProperties().getUrl())) {
            for (String ignoreUrl : securityIgnoreProperties().getUrl()) {
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
//                    response.setContentType(ContentType.JSON.getValue());
//                    Map<String, String> resultMap = HashMap.of("msg", "logout success!", "data", "kapcb logout success!", "code", "200");
//                    response.getWriter().write(JSON.toJSONString(resultMap));
//                    response.getWriter().flush();
//                    response.getWriter().close();
                }, new AntPathRequestMatcher("/logout", "GET"))
                .defaultLogoutSuccessHandlerFor((request, response, auth) -> {
//                    response.setContentType(ContentType.JSON.getValue());
//                    Map<String, String> resultMap = HashMap.of("msg", "logout1 success!", "data", "kapcb logout1 success!", "code", "200");
//                    response.getWriter().write(JSON.toJSONString(resultMap));
//                    response.getWriter().flush();
//                    response.getWriter().close();
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

    private Set<String> getPermitAll() {
        Set<String> anonymousAccessSet = new HashSet<>();
        Set<String> configPermit = getConfigPermit();
        if (CollectionUtils.isNotEmpty(configPermit)) {
            anonymousAccessSet.addAll(configPermit);
        }
        Set<String> anonymousAccess = getAnonymousAccess();
        if (CollectionUtils.isNotEmpty(anonymousAccess)) {
            anonymousAccessSet.addAll(anonymousAccess);
        }
        return anonymousAccessSet;
    }

    private Set<String> getAnonymousAccess() {
        RequestMappingHandlerMapping requestMappingHandlerMapping = ApplicationContextHolder.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        Set<String> anonymousAccessSet = null;
        if (MapUtils.isNotEmpty(handlerMethods)) {
            anonymousAccessSet = new HashSet<>();
            for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethods.entrySet()) {
                RequestMappingInfo requestMappingInfo = infoEntry.getKey();
                HandlerMethod handlerMethod = infoEntry.getValue();
                AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
                if (Objects.nonNull(anonymousAccess)) {
                    anonymousAccessSet.addAll(requestMappingInfo.getPatternsCondition().getPatterns());
                }
            }
        }
        return anonymousAccessSet;
    }

    private Set<String> getConfigPermit() {
        List<String> url = securityIgnoreProperties().getUrl();
        Set<String> configPermit = null;
        if (CollectionUtils.isNotEmpty(url)) {
            configPermit = new HashSet<>(url);
        }
        return configPermit;
    }

}
