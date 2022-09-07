package com.skillstorm.spyglassapi.authenticationAndSecurityConfigurations.configuration;

import com.skillstorm.spyglassapi.STATIC_DETAILS.SD;
import com.skillstorm.spyglassapi.authenticationAndSecurityConfigurations.filter.JwtAuthenticationFilter;
import com.skillstorm.spyglassapi.authenticationAndSecurityConfigurations.filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/api/v1/login/**", "/api/v1/refreshToken/**").permitAll();
        http.authorizeRequests().antMatchers(GET, "/api/user/**").hasAnyAuthority(SD.Role_User);
        http.authorizeRequests().antMatchers(POST, "/api/user/save/**").hasAnyAuthority(SD.Role_Admin);
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(jwtAuthenticationFilter);

        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
