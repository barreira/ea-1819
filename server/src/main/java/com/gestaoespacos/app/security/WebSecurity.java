package com.gestaoespacos.app.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import static com.gestaoespacos.app.security.SecurityConstants.ACESSABLE;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	private UserDetailsService userDetailsService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;

	public WebSecurity(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.POST, ACESSABLE).permitAll()
				.antMatchers(HttpMethod.GET, ACESSABLE).permitAll()
				.anyRequest().authenticated().and().addFilter(new JWTAuthenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager()))
				// this disables session creation on Spring Security
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select t.username, t.password, 1 from " +
										"(select username, password from utilizador " +
						"union all " +
						"select username, password from utilizadorcpdr " +
						"union all " +
						"select username, password from administrador " +
						"union all " +
						"select username, password from gestor_espacos) t where t.username=?")

				//"select username, password, 1 from utilizador where username=?")
				.authoritiesByUsernameQuery(
						//"select u.username, r.name from users_roles ur, users u, role r where u.username = ? and ur.users_id = u.id and ur.role_id = r.id")
						"select t.username, CONCAT(t.tipo,',',t.id) from (select username, 'Utilizador' as tipo, id from utilizador " +
								"union all " +
								"select username, 'UtilizadorCPDR' as tipo, id from utilizadorcpdr " +
								"union all " +
								"select username, 'Administrador' as tipo, id from administrador " +
								"union all " +
								"select username, 'GestorEspacos' as tipo, id from gestor_espacos) t where t.username=?")
						//"select username, 'Utilizador' from utilizador where username=?")
				.passwordEncoder(bCryptPasswordEncoder);
	}

	// @Bean
	// CorsConfigurationSource corsConfigurationSource() {
	//
	// CorsConfiguration configuration = new CorsConfiguration();
	// configuration.setAllowedOrigins(Arrays.asList("https://localhost:3000",
	// "http://localhost:3000"));
	// configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE",
	// "OPTIONS"));
	// UrlBasedCorsConfigurationSource source = new
	// UrlBasedCorsConfigurationSource();
	// source.registerCorsConfiguration("/**", configuration);
	// return source;
	// }

	// @Bean
	// public FilterRegistrationBean corsFilter() {
	// UrlBasedCorsConfigurationSource source = new
	// UrlBasedCorsConfigurationSource();
	// CorsConfiguration config = new CorsConfiguration();
	// config.setAllowCredentials(true);
	// config.addAllowedOrigin("*");
	//
	// config.addAllowedHeader("*");
	// config.addAllowedMethod("*");
	// source.registerCorsConfiguration("/**", config);
	//
	// FilterRegistrationBean bean = new FilterRegistrationBean(new
	// CorsFilter(source));
	// bean.setOrder(0);
	// return bean;
	//
	// }

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		System.out.println("setting cors configuration");
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.applyPermitDefaultValues();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

}