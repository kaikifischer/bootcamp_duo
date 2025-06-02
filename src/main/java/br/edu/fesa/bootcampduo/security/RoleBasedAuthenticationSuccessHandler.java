package br.edu.fesa.bootcampduo.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class RoleBasedAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            System.out.println("Resposta já enviada (committed). Não é possível redirecionar para " + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(final Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (authorityName.equals("ROLE_ADMIN")) { // Comparar com "ROLE_ADMIN"
                return "/";
            } else if (authorityName.equals("ROLE_DEVOPS")) { // Comparar com "ROLE_DEVOPS"
                return "/devops"; // Redirecionar para /devops
            } else if (authorityName.equals("ROLE_INTEL")) { // Comparar com "ROLE_INTEL"
                return "/intel";
            }
        }

        // Se nenhuma role específica das acima for encontrada, ou se o usuário não tiver roles mapeadas
        System.err.println("Usuário com role não mapeada para redirecionamento específico: " + authorities + ". Redirecionando para a página de login com erro.");
        return "/login?errorRole=true"; // CORRIGIDO: Fallback mais informativo
    }

    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
}
