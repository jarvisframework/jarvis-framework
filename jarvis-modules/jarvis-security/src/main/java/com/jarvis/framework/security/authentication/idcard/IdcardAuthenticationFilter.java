package com.jarvis.framework.security.authentication.idcard;

import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年1月28日
 */
public class IdcardAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_FORM_ID_CARD_NUMBER_KEY = "idCardNumber";

    public static final String SPRING_SECURITY_FORM_NAME_KEY = "name";

    public static final String SPRING_SECURITY_FORM_NATIONALITY_KEY = "nationality";

    public static final String SPRING_SECURITY_FORM_SEX_KEY = "sex";

    public static final String SPRING_SECURITY_FORM_BIRTH_DATE_KEY = "birthDate";

    public static final String SPRING_SECURITY_FORM_ADDRESS_KEY = "address";

    public static final String SPRING_SECURITY_FORM_EXTENSION_KEY = "extension";

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(
            "/login/idcard",
            "POST");

    private final String idCardNumberParameter = SPRING_SECURITY_FORM_ID_CARD_NUMBER_KEY;

    private final String nameParameter = SPRING_SECURITY_FORM_NAME_KEY;

    private final String sexParameter = SPRING_SECURITY_FORM_SEX_KEY;

    private final String nationalityParameter = SPRING_SECURITY_FORM_NATIONALITY_KEY;

    private final String birthDateParameter = SPRING_SECURITY_FORM_BIRTH_DATE_KEY;

    private final String addressParameter = SPRING_SECURITY_FORM_ADDRESS_KEY;

    private final String extensionParameter = SPRING_SECURITY_FORM_EXTENSION_KEY;

    private boolean postOnly = true;

    public IdcardAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public IdcardAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        final String idCardNumber = obtainIdCardNumber(request);
        if (null == idCardNumber || !StringUtils.hasText(idCardNumber)) {
            throw new UsernameNotFoundException("身份证号不能为空");
        }
        final String name = obtainName(request);
        final String sex = obtainSex(request);
        final String nationality = obtainNationality(request);
        final String birthDate = obtainBirthDate(request);
        final String address = obtainAddress(request);
        final String extension = obtainExtension(request);
        final IdcardModel idcardModel = new IdcardModel(idCardNumber, name, sex, address, birthDate, nationality,
                extension);
        final IdcardAuthenticationToken authRequest = new IdcardAuthenticationToken(idCardNumber, idcardModel);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * @param request
     * @return
     */
    private String obtainAddress(HttpServletRequest request) {
        return request.getParameter(this.addressParameter);
    }

    /**
     * @param request
     * @return
     */
    private String obtainBirthDate(HttpServletRequest request) {
        return request.getParameter(this.birthDateParameter);
    }

    /**
     * @param request
     * @return
     */
    private String obtainNationality(HttpServletRequest request) {
        return request.getParameter(this.nationalityParameter);
    }

    /**
     * @param request
     * @return
     */
    private String obtainSex(HttpServletRequest request) {
        return request.getParameter(this.sexParameter);
    }

    /**
     * @param request
     * @return
     */
    private String obtainIdCardNumber(HttpServletRequest request) {
        return request.getParameter(this.idCardNumberParameter);
    }

    /**
     * @param request
     * @return
     */
    private String obtainExtension(HttpServletRequest request) {
        return request.getParameter(this.extensionParameter);
    }

    /**
     * Enables subclasses to override the composition of the username, such as by
     * including additional values and a separator.
     *
     * @param request so that request attributes can be retrieved
     * @return the username that will be presented in the <code>Authentication</code>
     *         request token to the <code>AuthenticationManager</code>
     */
    @Nullable
    protected String obtainName(HttpServletRequest request) {
        return request.getParameter(this.nameParameter);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     *
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     *            set
     */
    protected void setDetails(HttpServletRequest request, IdcardAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    /**
     * Sets the parameter name which will be used to obtain the username from the login
     * request.
     *
     * @param usernameParameter the parameter name. Defaults to "username".
     */
    /*public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.usernameParameter = usernameParameter;
    }*/

    /**
     * Defines whether only HTTP POST requests will be allowed by this filter. If set to
     * true, and an authentication request is received which is not a POST request, an
     * exception will be raised immediately and authentication will not be attempted. The
     * <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed
     * authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    /**
     * @return the idCardNumberParameter
     */
    public String getIdCardNumberParameter() {
        return idCardNumberParameter;
    }

    /**
     * @return the nameParameter
     */
    public String getNameParameter() {
        return nameParameter;
    }

    /**
     * @return the sexParameter
     */
    public String getSexParameter() {
        return sexParameter;
    }

    /**
     * @return the nationalityParameter
     */
    public String getNationalityParameter() {
        return nationalityParameter;
    }

    /**
     * @return the birthDateParameter
     */
    public String getBirthDateParameter() {
        return birthDateParameter;
    }

    /**
     * @return the addressParameter
     */
    public String getAddressParameter() {
        return addressParameter;
    }

    /**
     * @return the extensionParameter
     */
    public String getExtensionParameter() {
        return extensionParameter;
    }

    /**
     * @return the postOnly
     */
    public boolean isPostOnly() {
        return postOnly;
    }

}
