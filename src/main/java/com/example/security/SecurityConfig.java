package com.example.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.jdbcAuthentication().dataSource(dataSource)
	    .usersByUsernameQuery("select username, password, 'true' as enabled from users where username=?")
	    .authoritiesByUsernameQuery("select username, 'USER' as authority from users where username=?")
	    .passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception { 
		http.headers().frameOptions().disable();
	    http
	    .csrf().disable()
	    .exceptionHandling()
	    .and()
	    .authorizeRequests()
	    .antMatchers(HttpMethod.POST,"/users").permitAll()
	    .antMatchers("/users/authenticate").permitAll()
	    .antMatchers("/h2-console/**").permitAll()
	    .antMatchers("/**").authenticated()
	    .and()
	    /*.formLogin().defaultSuccessUrl("/jokes", true)
	    .and()*/
	    .logout();
	}
	 
	@Bean
	public PasswordEncoder  encoder() {
	    return new BCryptPasswordEncoder();
	}
}
