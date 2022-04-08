package com.example.sproject.configuration;

import com.example.sproject.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean

    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.authorizeRequests().antMatchers("/").hasAnyAuthority("SuperAdmin","Admin","User")
    	.antMatchers("/new").hasAnyAuthority("Admin","SuperAdmin").antMatchers("/edit/**")
    	.hasAnyAuthority("Admin").antMatchers("/delete/**").hasAuthority("Admin")
    	.antMatchers("/h2-console/**").permitAll().anyRequest().authenticated().and().formLogin().permitAll()
    	.defaultSuccessUrl("/users")
    	.and().logout().permitAll().and().exceptionHandling().accessDeniedPage("/403");



//    	http.csrf().disable();
//    	http.headers().frameOptions().disable();
    	}
    	}
       
        
        
//    	.antMatchers("/edit/**", "/delete/**").hasRole("Admin")
//		.anyRequest().authenticated()
//		.and()
//		.formLogin().permitAll()
//		.defaultSuccessUrl("/users")
//		.and()
//		.logout().permitAll()
//		.and()
//		.exceptionHandling().accessDeniedPage("/403")
//		;
//}
//                .antMatchers("/").authenticated()
//                .anyRequest().permitAll()
////                .anyRequest()
////                .hasRole("USER")
//               .and()
//                .formLogin()
//                .usernameParameter("email")
//                .defaultSuccessUrl("/users")
//                .loginProcessingUrl("/authenticateTheUser")
////                .loginPage("/authenticateTheUser")
//                .permitAll()
//                .and()
//                .logout().logoutSuccessUrl("/").permitAll();

   
