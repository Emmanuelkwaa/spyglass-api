package com.skillstorm.spyglassapi.authenticationAndSecurityConfigurations.configuration;

import com.skillstorm.spyglassapi.STATIC_DETAILS.SD;
import com.skillstorm.spyglassapi.authenticationAndSecurityConfigurations.filter.JwtAuthenticationFilter;
import com.skillstorm.spyglassapi.authenticationAndSecurityConfigurations.filter.JwtAuthorizationFilter;
import com.skillstorm.spyglassapi.authenticationAndSecurityConfigurations.util.JwtUtil;
import com.skillstorm.spyglassapi.data.repositories.AuthRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailsService jwtService;
    private final JwtUtil jwtUtil;
    private final AuthRepository authRepository;

    public SecurityConfiguration(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, UserDetailsService jwtService, JwtUtil jwtUtil, AuthRepository authRepository) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtService = jwtService;
        this.jwtUtil = jwtUtil;
        this.authRepository = authRepository;
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

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(jwtService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil, authRepository, authenticationManagerBean());
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/auth/login");

        http.cors().and().csrf().disable()
            .authorizeRequests().antMatchers("/api/v1/**", "/api/v1/auth/login/**", "/api/v1/auth/register/**").permitAll()
            .antMatchers(HttpHeaders.ALLOW).permitAll()
            .anyRequest().authenticated()
            .and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .sessionManagement().sessionCreationPolicy(STATELESS)
        ;
//        http.authorizeRequests().antMatchers(GET, "/api/v1/**").hasAnyAuthority(SD.Role_User);
//        http.authorizeRequests().antMatchers(POST, "/api/v1/**").hasAnyAuthority(SD.Role_User);

//        http.authorizeRequests().antMatchers("/api/v1/auth/login/**", "/api/v1/auth/register/**").permitAll();
//        http.authorizeRequests().antMatchers(GET, "/api/user/**").hasAnyAuthority(SD.Role_User);
//        http.authorizeRequests().antMatchers(POST, "/api/user/save/**").hasAnyAuthority(SD.Role_Admin);
//        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(jwtAuthenticationFilter);

        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}
