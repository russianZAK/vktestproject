package by.russianzak.vktestproject.sercurity.config;

import by.russianzak.vktestproject.filters.RequestLoggingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  private final UserDetailsService userDetailsService;
  private final RequestLoggingFilter loggingFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.addFilterBefore(loggingFilter, UsernamePasswordAuthenticationFilter.class);

    return http
        .httpBasic().and().csrf().disable()
        .cors().and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .and()
        .authorizeRequests()
        .antMatchers("/auth/register").permitAll()
        .antMatchers("/ws/**").hasRole("ADMIN")
        .antMatchers("/roles/**").hasAnyRole("ADMIN")
        .antMatchers("/users/**").hasAnyRole("ADMIN")
        .antMatchers(HttpMethod.GET, "/api/albums/**").hasAnyRole("ADMIN", "ALBUMS_VIEWER", "ALBUMS_EDITOR")
        .antMatchers(HttpMethod.POST, "/api/albums/**").hasAnyRole("ADMIN", "ALBUMS_EDITOR")
        .antMatchers(HttpMethod.PUT, "/api/albums/**").hasAnyRole("ADMIN", "ALBUMS_EDITOR")
        .antMatchers(HttpMethod.DELETE, "/api/albums/**").hasAnyRole("ADMIN", "ALBUMS_EDITOR")
        .antMatchers(HttpMethod.GET, "/api/posts/**").hasAnyRole("ADMIN", "POSTS_VIEWER", "POSTS_EDITOR")
        .antMatchers(HttpMethod.POST, "/api/posts/**").hasAnyRole("ADMIN", "POSTS_EDITOR")
        .antMatchers(HttpMethod.PUT, "/api/posts/**").hasAnyRole("ADMIN", "POSTS_EDITOR")
        .antMatchers(HttpMethod.DELETE, "/api/posts/**").hasAnyRole("ADMIN", "POSTS_EDITOR")
        .antMatchers(HttpMethod.GET, "/api/userdata/**").hasAnyRole("ADMIN", "USERS_VIEWER", "USERS_EDITOR")
        .antMatchers(HttpMethod.POST, "/api/userdata/**").hasAnyRole("ADMIN", "USERS_EDITOR")
        .antMatchers(HttpMethod.PUT, "/api/userdata/**").hasAnyRole("ADMIN", "USERS_EDITOR")
        .antMatchers(HttpMethod.DELETE, "/api/userdata/**").hasAnyRole("ADMIN", "USERS_EDITOR")
        .anyRequest().authenticated().and().build();
  }

  @Bean(name = "pwdEncoder")
  public PasswordEncoder getPasswordEncoder() {
    DelegatingPasswordEncoder delPasswordEncoder = (DelegatingPasswordEncoder) PasswordEncoderFactories
        .createDelegatingPasswordEncoder();
    BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
    delPasswordEncoder.setDefaultPasswordEncoderForMatches(bcryptPasswordEncoder);
    return delPasswordEncoder;
  }

}


