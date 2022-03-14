package com.acoes.solinfbreaker;

import java.security.Principal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@SpringBootApplication
@RestController
public class SolinfbreakerApplication {

	@GetMapping("/")
	public String welcome2user(Principal principal){
		return "Hi " + principal.getName() + " Welcome to javatech" ;
	}

	public static void main(String[] args) {
		SpringApplication.run(SolinfbreakerApplication.class, args);
	}

	@Configuration
    @EnableWebSecurity
    class SecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors().and()
                    .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                    .oauth2ResourceServer().jwt();
        }
    }

}
