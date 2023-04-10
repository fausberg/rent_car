package rentcar.rentcar.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableAspectJAutoProxy
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
                .antMatchers("/registration/user", "/swagger-ui/index.html").permitAll()
                .antMatchers("/**").hasRole("ADMIN")
                .antMatchers("/car/free", "/car/start/{id}", "/car/end").hasRole("USER")
                .antMatchers("/credit-card/update", "/credit-card/delete/{id}").hasRole("USER")
                .antMatchers("/user/update", "/user/delete", "/user/fines", "/user/credit-cards", "/user/rent-histories").hasRole("USER")
                .antMatchers("/registration/credit-card").hasRole("USER")
                .antMatchers("/driver-licence/update/", "/driver-licence/create", "/driver-licence/delete").hasRole("USER")
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
