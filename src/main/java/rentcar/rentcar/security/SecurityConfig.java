package rentcar.rentcar.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/registration/user").permitAll()
                .antMatchers("/car/free", "/car/start/{id}", "/car/end").hasAnyRole("USER", "ADMIN")
                .antMatchers("/credit-card/update", "/credit-card/delete/{id}").hasAnyRole("USER", "ADMIN")
                .antMatchers("/user/update", "/user/delete", "/user/fines", "/user/credit-cards", "/user/rent-histories").hasAnyRole("USER", "ADMIN")
                .antMatchers("/registration/credit-card").hasAnyRole("USER", "ADMIN")
                .antMatchers("/driver-licence/update/", "/driver-licence/create", "/driver-licence/delete").hasAnyRole("USER", "ADMIN")
                .antMatchers("/swagger-ui/index.html").hasAnyRole("USER", "ADMIN")
                .antMatchers("/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
