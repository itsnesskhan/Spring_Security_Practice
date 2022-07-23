package com.spring.security;

import static com.spring.models.UserRole.ADMIN;
import static com.spring.models.UserRole.STUDENT;
import static com.spring.models.UserRole.TEACHER;
import static com.spring.permissions.UserPermissions.STUDENT_WRITE;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {
	
		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			http.
			csrf().disable()
			.authorizeRequests()
			.antMatchers("index","/css/**","/js/**").permitAll()
			.antMatchers(HttpMethod.DELETE,"/api/students/**").hasAuthority(STUDENT_WRITE.getPersimsion())
			.antMatchers(HttpMethod.POST,"/api/students/**").hasAuthority(STUDENT_WRITE.getPersimsion())
			.antMatchers(HttpMethod.PUT,"/api/students/**").hasAuthority(STUDENT_WRITE.getPersimsion())
			.antMatchers(HttpMethod.GET,"/api/students/**").hasAnyRole(ADMIN.name(),TEACHER.name())
			.antMatchers(HttpMethod.GET,"/user/**").hasAnyRole(ADMIN.name(),TEACHER.name(),STUDENT.name())
			.antMatchers("/**").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.loginPage("/signin").permitAll()
			.loginProcessingUrl("/login")
			.defaultSuccessUrl("/user/profile");
//			.and()
//			.logout()
//			.logoutUrl("/logout")
//			.logoutSuccessUrl("/signin");
		
		return http.build();		}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails nasserDetails = User.builder()
										.username("Ness")
											.password("ness")
//												.roles(STUDENT.name())
											.authorities(STUDENT.getGrantedAuthorities())
													.build();
		UserDetails hamdaDetails = User.builder()
										.username("Admin")
											.password("ness")
//												.roles(ADMIN.name())
											.authorities(ADMIN.getGrantedAuthorities())
													.build();
		
		UserDetails zoyaDetails = User.builder()
									.username("Zoya")
										.password("ness")
//											.roles(TEACHER.name())
											.authorities(TEACHER.getGrantedAuthorities())
											.build();

		return new InMemoryUserDetailsManager(nasserDetails, hamdaDetails, zoyaDetails);
	}

//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.      //role based mapping 
//			csrf()
//			.disable()
//			.authorizeRequests()
//			.antMatchers("/","index","/css/**","/js/**").permitAll()
//			.antMatchers(HttpMethod.POST, "/api/students/**").hasRole(ADMIN.name())
//			.antMatchers(HttpMethod.PUT, "/api/students/**").hasRole(ADMIN.name())
//			.antMatchers(HttpMethod.DELETE, "/api/students/**").hasRole(ADMIN.name())
//			.antMatchers(HttpMethod.GET, "/api/students/**").hasAnyRole(ADMIN.name(),TEACHER.name())
//			.antMatchers("api/admin/**").hasRole(ADMIN.name())
//			.anyRequest()
//			.authenticated()
//			.and()
//			.formLogin()
//			.loginPage("/login").permitAll()
//			.defaultSuccessUrl("/api/students")
//			.and()
//			.rememberMe()
//			.tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))//will expire in 21 days
//			.key("rem-me-key")// store hashvalue of username,session id with this key in db
//			.and()
//			.logout()
//			.logoutUrl("/logout")
//			.logoutSuccessUrl("/login");
//		
//		return http.build();
//	}
	
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}
