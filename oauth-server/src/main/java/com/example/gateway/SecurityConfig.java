package com.example.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class SecurityConfig extends AuthorizationServerConfigurerAdapter 
{
	@Bean
	PasswordEncoder getEncoder() {
	    return new BCryptPasswordEncoder();
	}

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
            .tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()")
            .allowFormAuthenticationForClients();
    }
 
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
            .inMemory()
            .withClient("clientapp").secret(getEncoder().encode("123456"))
            .authorizedGrantTypes("password", "authorization_code", "refresh_token","client_credentials")
            .authorities("READ_ONLY_CLIENT")
            .scopes("read_profile_info")
            .accessTokenValiditySeconds(120000)
            .refreshTokenValiditySeconds(240000);
    }
}