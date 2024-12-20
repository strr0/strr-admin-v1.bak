package com.strr.system.config;

import com.strr.system.constant.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Swagger配置
 */
@Configuration
@EnableSwagger2
@Profile({"dev"})
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.strr"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(scheme()))
                .securityContexts(Collections.singletonList(context()));
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("strr-admin")
                .description("strr-admin接口文档")
                .version("1.0")
                .build();
    }

    private List<GrantType> grantTypes() {
        List<GrantType> grantTypes = new ArrayList<>();
//        TokenRequestEndpoint tokenRequestEndpoint = new TokenRequestEndpoint("/oauth/authorize", client, secret);
//        TokenEndpoint tokenEndpoint = new TokenEndpoint("/oauth/token", "token");
//        grantTypes.add(new AuthorizationCodeGrant(tokenRequestEndpoint, tokenEndpoint));
        grantTypes.add(new ResourceOwnerPasswordCredentialsGrant("/oauth/token"));
        return grantTypes;
    }

    private List<AuthorizationScope> scopes() {
        List<AuthorizationScope> list = new ArrayList<>();
        for (String scope : Constant.SCOPES) {
            list.add(new AuthorizationScope(scope,String.format("Grants %s access", scope)));
        }
        return list;
    }

    private SecurityScheme scheme() {
        return new OAuthBuilder()
                .name("OAuth2")
                .scopes(scopes())
                .grantTypes(grantTypes())
                .build();
    }

    private SecurityContext context() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference("OAuth2", scopes().toArray(new AuthorizationScope[0]))))
                .forPaths(PathSelectors.any())
                .build();
    }

    @Bean
    protected SecurityConfiguration securityInfo() {
        return new SecurityConfiguration(Constant.CLIENT_ID, Constant.CLIENT_SECRET, "realm", Constant.CLIENT_ID, "", null, null);
    }
}
