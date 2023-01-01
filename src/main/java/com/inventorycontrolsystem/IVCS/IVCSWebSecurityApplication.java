package com.inventorycontrolsystem.IVCS;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class IVCSWebSecurityApplication  extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/login").permitAll()
			.antMatchers("/role/**").hasAnyRole("System Administration","Master Setup","Inventory Transaction","Inventory Report","ADMIN")
		.and()
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/IVC").failureUrl("/loginError")
		.and()
			.logout()
			.logoutSuccessUrl("/logoutSuccess")
			.deleteCookies("JSESSIONID")
			;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.passwordEncoder(encoder)
			.withUser("admin@gmail.com")
			.password(encoder.encode("admin"))
			.roles("ADMIN");
		auth.jdbcAuthentication()
			.passwordEncoder(encoder)
			.dataSource(dataSource)
			.usersByUsernameQuery("select email,pass,enable from user where email=?")
			.authoritiesByUsernameQuery("select email,role from permission where email=?");
	}
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
}
