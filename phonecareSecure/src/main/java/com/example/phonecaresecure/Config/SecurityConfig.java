package com.example.phonecaresecure.Config;

import com.example.phonecaresecure.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final MyUserDetailsService myUserDetailsService;
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(myUserDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/review/**").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/user/**").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/provider/get").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/provider/delete").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/offer/get").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/offer/add").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/offer/update").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/offer/delete").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/customer/get").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/customer/delete").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/order/get").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/order/add/{customerId}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/order/update/{id}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/order/delete/{id}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/provider/get-id").hasAnyAuthority("ADMIN","PROVIDER")
                .requestMatchers("/api/v1/provider/add").permitAll()
                .requestMatchers("/api/v1/provider/update").hasAnyAuthority("ADMIN","PROVIDER")
                .requestMatchers("/api/v1/provider/make-over/{order_id}").hasAnyAuthority("ADMIN","PROVIDER")
                .requestMatchers("/api/v1/provider/update-provider-info/{info}").hasAnyAuthority("ADMIN","PROVIDER")
                .requestMatchers("/api/v1/provider/get-orders").hasAnyAuthority("ADMIN","PROVIDER")
                .requestMatchers("/api/v1/provider/get-offers").hasAnyAuthority("ADMIN","PROVIDER")
                .requestMatchers("/api/v1/provider/send-final/{offerId}").hasAnyAuthority("ADMIN","PROVIDER")
                .requestMatchers("/api/v1/offer/cancel-offer").hasAnyAuthority("ADMIN","PROVIDER")
                .requestMatchers("/api/v1/offer/offer-completed").hasAnyAuthority("ADMIN","PROVIDER")
                .requestMatchers("/api/v1/customer/update").hasAnyAuthority("ADMIN","CUSTOMER")
                .requestMatchers("/api/v1/customer/add").permitAll()
                .requestMatchers("/api/v1/customer/makeorder").hasAnyAuthority("ADMIN","CUSTOMER")
                .requestMatchers("/api/v1/customer/get-all-offer/{orderId}").hasAnyAuthority("ADMIN","CUSTOMER")
                .requestMatchers("/api/v1/customer/get-id").hasAnyAuthority("ADMIN","CUSTOMER")
                .requestMatchers("/api/v1/customer/get-info/{orderId}").hasAnyAuthority("ADMIN","CUSTOMER")
                .requestMatchers("/api/v1/order/update-desc/{order_id}/{desc}").hasAnyAuthority("ADMIN","CUSTOMER")
                .requestMatchers("/api/v1/order/complete-order/{orderId}").hasAnyAuthority("ADMIN","CUSTOMER")
                .requestMatchers("/api/v1/order/reject-offer/{orderId}").hasAnyAuthority("ADMIN","CUSTOMER")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/user/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }
}
