package com.example.Army.SecuritySystem.Security;

import com.example.Army.SecuritySystem.Member.Member;
import com.example.Army.SecuritySystem.Member.MemberDegree;
import com.example.Army.SecuritySystem.Member.MemberService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig{
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberService memberService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable) //to be able to send request like post
                .authorizeHttpRequests(x -> x.requestMatchers("/officer/**").hasRole("OFFICER"))
                .authorizeHttpRequests(x -> x.requestMatchers("/ncofficer/**").hasAnyRole("OFFICER","NCOFFICER"))
                .authorizeHttpRequests(x -> x.requestMatchers("/soldier/**").hasAnyRole("OFFICER","NCOFFICER","SOLDIER"))
                .authorizeHttpRequests(x -> x.requestMatchers("/member/**").hasRole("OFFICER"))
                .authorizeHttpRequests(a -> a.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .invalidSessionUrl("/login?invalid-session=true") // Redirect for invalid sessions
                                .maximumSessions(1) // Limit to one session per user
                                .expiredUrl("/login?session-expired=true") // Redirect when session expires
//                                .sessionFixation().migrateSession() // Migrate session on login
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout") // Custom logout URL
                                .logoutSuccessUrl("/login?logout=true") // Redirect after logout success
                                .invalidateHttpSession(true) // Invalidate the session
                                .deleteCookies("JSESSIONID") // Delete session cookie
                );

        return http.build();
    }
    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(memberService);
        return provider;
    }



    @PostConstruct
//    @Bean
    public String addTestUser(){
        Member yassin = new Member("ali","ahmed","654845316331","yassin","good", MemberDegree.ROLE_SOLDIER);
        yassin.setEnabled(true);
        yassin.setLocked(false);
        Member capo = new Member("mustafa","ahmed","657745316331","capo","good", MemberDegree.ROLE_NCOFFICER);
//        capo.setEnabled(true);
//        capo.setLocked(false);
        Member mohamed = new Member("mohamed","ahmed","656645316331","mohamed","good", MemberDegree.ROLE_OFFICER);
        mohamed.setEnabled(true);
        mohamed.setLocked(false);
        memberService.signUp(yassin);
        memberService.signUp(capo);
        memberService.signUp(mohamed);
//        memberService.enableAppUser(mohamed.getEmail());
        return "test user saved successfully";
    }

}
