package com.postify.main.configuration;

import com.postify.main.services.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    @Autowired
    private CustomUserDetailService userDetailsService;

//    @Autowired
//    public SecurityConfiguration(CustomUserDetailService userDetailService){
//        this.userDetailsService=userDetailService;
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails user1= User.withUsername("bhushan")
//                .password(passwordEncoder().encode("bhushan"))
//                .roles("ADMIN")
//                .build();
//        UserDetails user2= User.withUsername("sushant")
//                .password(passwordEncoder().encode("sushant"))
//                .roles("USER")
//                .build();
//        UserDetails user3= User.withUsername("rutik")
//                .password(passwordEncoder().encode("rutik"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1,user2,user3);
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.
                csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequest->
                        authorizeRequest
                                .requestMatchers(HttpMethod.POST,"/users").permitAll()
                                .requestMatchers("/posts/**").hasRole("USER")
                                .anyRequest().authenticated()
                )
//                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sessionManagement->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        return http.build();
    }

}
