package com.example.demo.security;

import com.example.demo.security.auth.RestAuthenticationEntryPoint;
import com.example.demo.security.auth.TokenAuthenticationFilter;
import com.example.demo.security.auth.TokenAuthenticationProvider;
import com.example.demo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Collections;

@Configuration
//Ukljucivanje podrske za anotacije "@Pre*" i "@Post*" koje ce aktivirati autorizacione provere za svaki pristup metodi
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    // Implementacija PasswordEncoder-a koriscenjem BCrypt hashing funkcije.
    // BCrypt po defalt-u radi 10 rundi hesiranja prosledjene vrednosti.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Servis koji se koristi za citanje podataka o korisnicima aplikacije
    @Autowired
    private CustomUserDetailsService jwtUserDetailsService;

    // Handler za vracanje 401 kada klijent sa neodogovarajucim korisnickim imenom i
    // lozinkom pokusa da pristupi resursu
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private TokenAuthenticationProvider authenticationProvider;

    // Registrujemo authentication manager koji ce da uradi autentifikaciju
    // korisnika za nas
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }

    // Definisemo uputstvo za authentication managera koji servis da koristi da
    // izvuce podatke o korisniku koji zeli da se autentifikuje,
    // kao i kroz koji enkoder da provuce lozinku koju je dobio od klijenta u
    // zahtevu da bi adekvatan hash koji dobije kao rezultat bcrypt algoritma
    // uporedio sa onim koji se nalazi u bazi (posto se u bazi ne cuva plain
    // lozinka)
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    // Injektujemo implementaciju iz TokenUtils klase kako bismo mogli da koristimo
    // njene metode za rad sa JWT u TokenAuthenticationFilteru
    @Autowired
    private TokenUtils tokenUtils;

    // Definisemo prava pristupa odredjenim URL-ovima
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // komunikacija izmedju klijenta i servera je stateless posto je u pitanju REST
                // aplikacija
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // sve neautentifikovane zahteve obradi uniformno i posalji 401 gresku
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

                // svim korisnicima dopusti da pristupe putanji /auth/**
                .authorizeRequests().antMatchers("/auth/**", "/ws/**").permitAll()

                // umesto anotacija iynad svake metode, moze i ovde da se proveravaju prava
                // pristupa ya odredjeni URL
                // .antMatchers(HttpMethod.GET,
                // "/api/cultural-content-category").hasRole("ROLE_ADMIN")

                // za svaki drugi zahtev korisnik mora biti autentifikovan
                .antMatchers("/korisnik/prijava").permitAll()
                .antMatchers("/korisnik/registracija").permitAll()
                .antMatchers("/korisnik/logout").permitAll()
                .anyRequest().authenticated().and()
                // za development svrhe ukljuci konfiguraciju za CORS iz WebConfig klase
                .cors().and()

                // umetni custom filter TokenAuthenticationFilter kako bi se vrsila provera JWT
                // tokena umesto cistih korisnickog imena i lozinke (koje radi
                // BasicAuthenticationFilter)
                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService),
                        BasicAuthenticationFilter.class);
        // zbog jednostavnosti primera
        http.csrf().disable();
    }

    // Generalna bezbednost aplikacije
    @Override
    public void configure(WebSecurity web) throws Exception {
        // TokenAuthenticationFilter ce ignorisati sve ispod navedene putanje
        web.ignoring().antMatchers(HttpMethod.POST, "/korisnik/prijava","/korisnik/registracija", "/saglasnost/naprednaPretraga", "/ws/**");

        web.ignoring().antMatchers(HttpMethod.GET, "/", "/korisnik",
        		"/interesovanje/updatePending", "/saglasnost/getAll",
        		"/zahtev/findByStatus", "/zahtev/odbijZahtev/**", "/zahtev/odobriZahtev/**",
                "/saglasnost/xml/**", "/saglasnost/allXmlByEmail/**",
                "/potvrda/xml/**", "/potvrda/allXmlByEmail/**",
                "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
                "/**/*.css", "/**/*.js","/v2/api-docs"); 
    }
}
