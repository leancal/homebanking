package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity//@EnableWebSecurity es una anotacion que se usa para habilitar la seguridad en la aplicacion.
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/web/index.html", "/web/js/**", "/web/styles/**", "/web/assets/**", "/api/clients/current",  "/api/clients", "/api/clients/current/accounts", "/api/clients/current/cards", "/api/transactions", "/api/loans","/api/clients/current/loans","/api/clients/current/accounts","/api/clients/current/cards/state","/api/clients/current/accounts/accountState","/api/admin/loans").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients","/api/loans","/api/clients/current/loans","/api/transactions/pdf","/api/clients/current/accounts","/api/clients/current/transactions/payments").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients/current/accounts", "/api/clients/current/cards","/api/loans").hasAnyAuthority("CLIENT", "ADMIN")
                .antMatchers("/rest/**", "/api/**", "/manager.html", "/clients/currents","/api/admin/loans").hasAuthority("ADMIN")
                .antMatchers("/web/**").hasAnyAuthority("CLIENT", "ADMIN");


        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("pwd")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");

        http.csrf().disable();


        //disabling frameOptions so h2-console can be accessed

        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }

    }
}

