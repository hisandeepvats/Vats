package com.sandeep.accounts;

import com.sandeep.accounts.dto.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * if you are not following the standard like you are creating this controller package somewhere outside  this package.
 * So in such scenarios it is mandatory to mention details to spring boot where your controllers are,  where your repositories are,
 * where your entity classes. So all such information we are supposed to pass using certain annotations. So these annotations are commented below,
 * like you can see using ComponentScan. you have to enable all these, otherwise code will not work.
 */
@SpringBootApplication
/*@ComponentScans({ @ComponentScan("com.eazybytes.accounts.controller") })
@EnableJpaRepositories("com.eazybytes.accounts.repository")
@EntityScan("com.eazybytes.accounts.model")*/
@EnableFeignClients
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {AccountsContactInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts microservice REST API Documentation",
				description = "EazyBank Accounts microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Sandeep Tyagi",
						email = "sandip.tyagi@gmail.com",
						url = "https://www.youtube.com/results?search_query=hindi+movie"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.youtube.com/results?search_query=hindi+movie"
				)
		),
		externalDocs = @ExternalDocumentation(
				description ="Accounts microservices REST API Documentation",
				url = "https://www.youtube.com/results?search_query=hindi+movie"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
