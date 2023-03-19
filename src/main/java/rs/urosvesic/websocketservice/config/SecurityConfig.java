package rs.urosvesic.websocketservice.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/sba-websocket/**").permitAll()
                .anyRequest()
                .authenticated().and()
                .oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtConverter());
        return http.build();
    }

    private Converter<Jwt,AbstractAuthenticationToken> jwtConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<String> claims = jwt.getClaimAsStringList("cognito:groups");
            if(claims==null){
                return Collections.emptyList();
            }
            List<GrantedAuthority> authorities  = new ArrayList<>();
            claims.forEach(c->authorities.add(new SimpleGrantedAuthority(c)));
            return authorities;
        });
        return converter;

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**");
    }

}
