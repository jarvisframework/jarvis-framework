package com.jarvis.framework.security.authentication.idcard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

public class IdcardAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final String SPRING_SECURITY_FORM_ID_CARD_NUMBER_KEY = "idCardNumber";
    public static final String SPRING_SECURITY_FORM_NAME_KEY = "name";
    public static final String SPRING_SECURITY_FORM_NATIONALITY_KEY = "nationality";
    public static final String SPRING_SECURITY_FORM_SEX_KEY = "sex";
    public static final String SPRING_SECURITY_FORM_BIRTH_DATE_KEY = "birthDate";
    public static final String SPRING_SECURITY_FORM_ADDRESS_KEY = "address";
    public static final String SPRING_SECURITY_FORM_EXTENSION_KEY = "extension";
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login/idcard", "POST");
    private final String idCardNumberParameter = "idCardNumber";
    private final String nameParameter = "name";
    private final String sexParameter = "sex";
    private final String nationalityParameter = "nationality";
    private final String birthDateParameter = "birthDate";
    private final String addressParameter = "address";
    private final String extensionParameter = "extension";
    private boolean postOnly = true;

    public IdcardAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public IdcardAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String idCardNumber = this.obtainIdCardNumber(request);
            if (null != idCardNumber && StringUtils.hasText(idCardNumber)) {
                String name = this.obtainName(request);
                String sex = this.obtainSex(request);
                String nationality = this.obtainNationality(request);
                String birthDate = this.obtainBirthDate(request);
                String address = this.obtainAddress(request);
                String extension = this.obtainExtension(request);
                IdcardModel idcardModel = new IdcardModel(idCardNumber, name, sex, address, birthDate, nationality, extension);
                IdcardAuthenticationToken authRequest = new IdcardAuthenticationToken(idCardNumber, idcardModel);
                this.setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            } else {
                throw new UsernameNotFoundException("身份证号不能为空");
            }
        }
    }

    private String obtainAddress(HttpServletRequest request) {
        this.getClass();
        return request.getParameter("address");
    }

    private String obtainBirthDate(HttpServletRequest request) {
        this.getClass();
        return request.getParameter("birthDate");
    }

    private String obtainNationality(HttpServletRequest request) {
        this.getClass();
        return request.getParameter("nationality");
    }

    private String obtainSex(HttpServletRequest request) {
        this.getClass();
        return request.getParameter("sex");
    }

    private String obtainIdCardNumber(HttpServletRequest request) {
        this.getClass();
        return request.getParameter("idCardNumber");
    }

    private String obtainExtension(HttpServletRequest request) {
        this.getClass();
        return request.getParameter("extension");
    }

    @Nullable
    protected String obtainName(HttpServletRequest request) {
        this.getClass();
        return request.getParameter("name");
    }

    protected void setDetails(HttpServletRequest request, IdcardAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public String getIdCardNumberParameter() {
        return "idCardNumber";
    }

    public String getNameParameter() {
        return "name";
    }

    public String getSexParameter() {
        return "sex";
    }

    public String getNationalityParameter() {
        return "nationality";
    }

    public String getBirthDateParameter() {
        return "birthDate";
    }

    public String getAddressParameter() {
        return "address";
    }

    public String getExtensionParameter() {
        return "extension";
    }

    public boolean isPostOnly() {
        return this.postOnly;
    }
}
