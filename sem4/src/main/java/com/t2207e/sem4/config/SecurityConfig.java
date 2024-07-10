package com.t2207e.sem4.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery("CALL GetUserByUsername(?)");
        userDetailsManager.setAuthoritiesByUsernameQuery("CALL GetAuthoritiesByUsername(?)");
        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .sessionManagement(session ->{
                    session
                            .maximumSessions(1)
                            .sessionRegistry(sessionRegistry())
                            .maxSessionsPreventsLogin(true);
                })
                .authorizeHttpRequests(configuration ->{configuration
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webfonts/**", "/home/**","/assets/**").permitAll()
                        .requestMatchers("/register","/", "/forgotPassword/**","/checkExistMail/**","resetPassworUrl/**","resetForgotPassword","checkResetForgotPassword", "search").permitAll()
                        .requestMatchers("/contactus/**","/sendcontactus/**", "/api/cart/addToCart", "/courseType/**", "/api/course/getVideo").permitAll()
                        .requestMatchers("/test","/course/list/**", "/course/detail/**", "/api/course/getVideo", "teacher/list", "teacher/detail/**","confirmAccount/**").permitAll()
                        .requestMatchers("/home/account/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/roleTeacher/**").hasRole("TEACHER")
                        .anyRequest().authenticated();
                })
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/authenticateUser")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe.key("uniqueAndSecret").tokenValiditySeconds(86400))
                .logout(logout ->logout
                        .logoutUrl("/logout")
//                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
//                        .addLogoutHandler(new SecurityContextLogoutHandler())
//                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .permitAll()
                );

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        return factory -> factory.addConnectorCustomizers((Connector connector) -> {
            connector.setMaxPostSize(500 * 1024 * 1024); // 500MB
        });
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
