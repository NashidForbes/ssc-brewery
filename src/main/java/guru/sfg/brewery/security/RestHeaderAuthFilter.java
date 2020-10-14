package guru.sfg.brewery.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RestHeaderAuthFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * Creates a new instance
     *
     * @param requiresAuthenticationRequestMatcher the {@link RequestMatcher} used to
     *                                             determine if authentication is
     *                                             required. Cannot be null.
     */
    public RestHeaderAuthFilter(
            RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    /**
     * Performs actual authentication.
     * <p>
     * The implementation should do one of the following:
     * <ol>
     * <li>Return a populated authentication token for the authenticated user, indicating
     * successful authentication</li>
     * <li>Return null, indicating that the authentication process is still in progress.
     * Before returning, the implementation should perform any additional work required to
     * complete the process.</li>
     * <li>Throw an <tt>AuthenticationException</tt> if the authentication process
     * fails</li>
     * </ol>
     *
     * @param request  from which to extract parameters and perform the authentication
     * @param response the response, which may be needed if the implementation has to do a
     *                 redirect as part of a multi-stage authentication process (such
     *                 as OpenID).
     * @return the authenticated user token, or null if authentication is incomplete.
     * @throws AuthenticationException if authentication fails.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) {
        String userName = getUsername(request);
        String password = getPassword(request);
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userName, password);
        if (userName == null)
            userName = "";
        if (password == null)
            password = "";
        logger.debug("Authenticating user : ");
        return this.getAuthenticationManager().authenticate(token);
    }

    private String getPassword(HttpServletRequest request) {
        return request.getHeader("Api-Secret");

    }

    private String getUsername(HttpServletRequest request) {
        return request.getHeader("Api-Key");
    }


}
