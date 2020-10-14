package guru.sfg.brewery.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// DRY principle Do Not Repeat Yourself
@Slf4j
public class RestUrlAuthFilter extends
        RestHeaderAuthFilter {
    /**
     * Creates a new instance
     *
     * @param requiresAuthenticationRequestMatcher the {@link RequestMatcher} used to
     *                                             determine if authentication is
     *                                             required. Cannot be null.
     */
    public RestUrlAuthFilter(
            RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
            AuthenticationException, IOException, ServletException {
        String userName = getUsername(request);
        String password = getPassword(request);

        if (userName == null){
            userName = "";
        }

        if (password == null){
            password = "";
        }

        log.debug("Authenticating User: " + userName);

        UsernamePasswordAuthenticationToken
                token = new UsernamePasswordAuthenticationToken(userName, password);

        if (!StringUtils.isEmpty(userName)) {
            return this.getAuthenticationManager().authenticate(token);
        } else {
            return null;
        }
    }


    protected String getPassword(HttpServletRequest request) {
        return request.getParameter("apiSecret");
    }

    protected String getUsername(HttpServletRequest request) {
        return request.getParameter("apiKey");
    }
}
