package kr.co.hankk20.study.security;

import kr.co.hankk20.study.domain.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

@EnableWebSecurity(debug = true)
@EnableMethodSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String SECURITY_HEADER = "Authentication";
    public static final String ROLE_ANONYMOUS = "ANONYMOUS";

    private CustomPreAuthenticationUserDetailService customPreAuthenticationUserDetailService;

    /**
     * 그냥 UserDetailService 사용자 정보로 DB에서 찿음
     * @param customPreAuthenticationUserDetailService
     */
    @Autowired
    public void setCustomPreAuthenticationUserDetailService(CustomPreAuthenticationUserDetailService customPreAuthenticationUserDetailService) {
        this.customPreAuthenticationUserDetailService = customPreAuthenticationUserDetailService;
    }

    /**
     * 인증제공자에 UserDetailService 설정
     * PreAuthenticatedAuthenticationProvider는
     * 필터에서 {@link PreAuthenticatedAuthenticationToken}을 인증요청하면 동작한다.
     * @return
     */
    public PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider(){
        PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(customPreAuthenticationUserDetailService);
        return preAuthenticatedAuthenticationProvider;
    }

    /**
     * WebSecurityConfigurerAdapter에서 제공하는 인증관리자 설정에 인증제공자를 등록한다.
     * 여기서 설정된 인증관리자는 authenticationManager() 메소드를 통해 가져 올 수 있다.
     * requestHeaderAuthenticationFilter()에서 authenticationManager()메소드를 호출하여 여기서 설정된 인증관리자를 등록 한다.
     *
     * 필터에서 인증관리자(AuthenticationManager)를 호출 하고 인증관리자는 Token유형 맞는 인증 제공자(AuthenticationProvider)를 찿고
     * 인증제공자는 자신에게 셋팅되어있는 UserDetailService를 사용하여 User 정보를 찿는다
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(preAuthenticatedAuthenticationProvider());
    }

    /**
     * 요청헤더에서 인증정보를 가져오기 위한 필터를 생성하여 설정한다.
     * @return
     * @throws Exception
     */
    public RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter() throws Exception {
        RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter = new RequestHeaderAuthenticationFilter();
        requestHeaderAuthenticationFilter.setPrincipalRequestHeader(SECURITY_HEADER); //인증정보가 담김 헤더 키 지정
        requestHeaderAuthenticationFilter.setAuthenticationManager(authenticationManager()); //위에서 설정한 인증관리자 등록
        requestHeaderAuthenticationFilter.setContinueFilterChainOnUnsuccessfulAuthentication(false); //실패시 계속 진행 여부
        requestHeaderAuthenticationFilter.setExceptionIfHeaderMissing(false);
        return requestHeaderAuthenticationFilter;
    }

    /**
     * 위에서 설정된 필터를 시큐리티에 등록
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/pre-db/**")
                    .permitAll()
                .mvcMatchers(HttpMethod.POST, "/board")
                    .not().anonymous()
                .anyRequest()
                    .permitAll()
                .and()
                .csrf().disable()
                .headers().frameOptions().sameOrigin().and()
                .anonymous()
                    .principal(anonymousPrincipal())
                    .authorities("ROLE_"+ROLE_ANONYMOUS) //Token에 들어가는 Authority는 ROLE과 Authority 구분없이 들어간다. prefix로 ROLE 있냐 없냐로 구분됨
                .and()
                .addFilter(requestHeaderAuthenticationFilter());


    }

    private Object anonymousPrincipal(){
        return CustomUser.builder()
                .account(new Account(ROLE_ANONYMOUS, null, ROLE_ANONYMOUS))
                .username(ROLE_ANONYMOUS)
                .roles(ROLE_ANONYMOUS)
                .build();
    }



}
