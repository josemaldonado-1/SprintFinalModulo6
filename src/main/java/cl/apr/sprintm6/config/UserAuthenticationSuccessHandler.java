package cl.apr.sprintm6.config;

import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String redirectUrl = null;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ADMIN")) {
                redirectUrl = "/admin";
                break;
            } else if (grantedAuthority.getAuthority().equals("CLIENTE")) {
                redirectUrl = "/cliente";
                break;
            } else if (grantedAuthority.getAuthority().equals("ADMINISTRATIVO")) {
                redirectUrl = "/administrativo";
                break;
            }
        }

        if (redirectUrl == null) {
            throw new IllegalStateException();
        }

        RedirectView view = new RedirectView(redirectUrl, true);
        view.setExposeModelAttributes(false);

        try {
            view.render(null, request, response);
        } catch (Exception e) {
            throw new IOException("Error creando vista", e);
        }
    }
}
