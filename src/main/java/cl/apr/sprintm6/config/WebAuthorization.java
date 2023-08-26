package cl.apr.sprintm6.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {
	

	@Autowired
	UserAuthenticationSuccessHandler successHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
        http.headers().frameOptions().sameOrigin();
        http.authorizeRequests()
                .antMatchers("/login.html", "/login","/","/api/**").permitAll()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/admin/**","/usuario/**").hasAuthority("ADMIN")
                .antMatchers("/cliente/**").hasAnyAuthority("CLIENTE","ADMIN")
                .antMatchers("/administrativo/**").hasAnyAuthority("ADMINISTRATIVO","ADMIN")
                .anyRequest().authenticated();
        
        
 
        http.formLogin()
        .successHandler(successHandler)
                .loginPage("/api/login")
                .usernameParameter("email")
                .passwordParameter("password");              


        http.logout()
        .logoutUrl("/api/logout")
        .logoutSuccessHandler(logoutSuccessHandler()) // Set the custom LogoutSuccessHandler
        .permitAll();

        http.csrf().disable();

        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler(successHandler);

        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response
        //http.logout().logoutSuccessHandler(logoutSuccessHandler);
    }
    
    
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            response.sendRedirect("/login"); // Redirect to the login page after successful logout
        };
    }

 
}
