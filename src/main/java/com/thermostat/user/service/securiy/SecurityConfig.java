package com.thermostat.user.service.securiy;

import com.thermostat.user.service.filter.CustomAuthFilter;
import com.thermostat.user.service.filter.CustomAuthorizeFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${secret_key}")
    private  String secretKey;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthFilter customAuthFilter = new CustomAuthFilter(authenticationManager(),secretKey);
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/login/**").permitAll();
        http.authorizeRequests().antMatchers("/api/add/**").permitAll();
        http.authorizeRequests().antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();
        http.authorizeRequests().antMatchers(GET,"/api/users").hasAnyAuthority("USER");
        http.authorizeRequests().antMatchers(POST,"/api/thermostat/**").hasAnyAuthority("USER");
        http.authorizeRequests().antMatchers(GET,"/api/thermostat/**").hasAnyAuthority("USER");
        http.authorizeRequests().antMatchers(GET,"/api/thermostats/**").hasAnyAuthority("USER");
        http.authorizeRequests().antMatchers(GET,"/api/user/**").hasAnyAuthority("USER");
        http.authorizeRequests().antMatchers(POST,"/api/thermostat/delete/**").hasAnyAuthority("USER");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthFilter);
        http.addFilterBefore(new CustomAuthorizeFilter(secretKey), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {

        return super.authenticationManager();
    }
}