package com.strr.system.config;

import com.strr.system.constant.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JwtConfig {
    // jwt 令牌转换
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(Constant.JWT_SECRET);
        return converter;
    }

    // jwt 令牌增强
    @Bean
    public TokenEnhancer jwtTokenEnhancer() {
        return (token, authentication) -> {
            Map<String, Object> additionalInfo = new HashMap<>();
//            additionalInfo.put("username", authentication.getPrincipal());
            ((DefaultOAuth2AccessToken) token).setAdditionalInformation(additionalInfo);
            return token;
        };
    }

    @Bean
    public TokenStore getTokenStore(JwtAccessTokenConverter converter) {
        return new JwtTokenStore(converter);
    }

    @Bean
    public DefaultTokenServices getTokenService(TokenStore tokenStore) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setSupportRefreshToken(true);
        return tokenServices;
    }
}
