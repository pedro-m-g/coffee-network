package com.coffee.network.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.coffee.network.filters.JwtFilter;
import com.coffee.network.services.CoffeeUserDetailsService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private CoffeeUserDetailsService userDetailsService;
  private JwtFilter jwtFilter;

  public WebSecurityConfig(
    @Autowired CoffeeUserDetailsService userDetailsService,
    @Autowired JwtFilter jwtFilter
  ) {
    this.userDetailsService = userDetailsService;
    this.jwtFilter = jwtFilter;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService)
      .passwordEncoder(passwordEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
      .antMatchers(HttpMethod.OPTIONS, "/**")
        .permitAll()
      .antMatchers(HttpMethod.POST, "/login", "/signup")
        .permitAll()
      .anyRequest()
        .authenticated()
    .and()
      .csrf().disable()
    .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    .and()
      .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
  }

}
