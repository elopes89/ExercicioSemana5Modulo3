package br.com.futurodev.primeiraapi.security;

import br.com.futurodev.primeiraapi.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //Quais requests serão autorizadas e como será a autorização
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/pedidos").permitAll()
                .antMatchers(HttpMethod.PUT, "/pedidos").permitAll()
                .antMatchers(HttpMethod.DELETE, "/pedidos").permitAll()
                .antMatchers(HttpMethod.GET, "/pedidos").permitAll()
                .antMatchers(HttpMethod.GET, "/usuarios").permitAll()
                .antMatchers(HttpMethod.POST, "/usuarios").permitAll()
                .antMatchers("/usuarios/**").hasRole("ADMIN")
                .antMatchers("/produtos/**").hasRole("ADMIN")
                .antMatchers("/pedidos/**").hasRole("ADMIN")
                .antMatchers("/pedidos/**").hasRole("PEDIDO")
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtLoginFilter("/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(cadastroUsuarioService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**.html",
                "/v2/api-docs",
                "/webjars/**",
                "/configuration/**",
                "/swagger-resources/**");
    }
}
