package com.teamtreehouse.hackvote.config;

import com.teamtreehouse.hackvote.auth.JwtAuthenticationFilter;
import com.teamtreehouse.hackvote.auth.RestAuthenticationEntryPoint;
import com.teamtreehouse.hackvote.user.User;
import com.teamtreehouse.hackvote.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private String basePath;

  private RestAuthenticationEntryPoint authEntryPoint;
  private JwtAuthenticationFilter jwtAuthFilter;
  private UserService userService;

  @Autowired
  public SecurityConfig(
      RestAuthenticationEntryPoint authEntryPoint,
      JwtAuthenticationFilter jwtAuthFilter,
      UserService userService,
      @Value("${spring.data.rest.basePath}") String basePath) {
    this.authEntryPoint = authEntryPoint;
    this.jwtAuthFilter = jwtAuthFilter;
    this.userService = userService;
    this.basePath = basePath;
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers(basePath + "/login", basePath + "/signup");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .exceptionHandling()
        .authenticationEntryPoint(authEntryPoint)
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(basePath).authenticated();

    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Autowired
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(User.PASSWORD_ENCODER);
  }
}