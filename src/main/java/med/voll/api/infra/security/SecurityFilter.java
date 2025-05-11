package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJwt = recuperarTokenETrataErro(request);
        if (tokenJwt != null) {
            var subject = tokenService.getSubject(tokenJwt);
            var usuario = repository.findByLogin(subject);
            var autenytication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(autenytication);
        }
        filterChain.doFilter(request, response);
    }

    private String recuperarTokenETrataErro(HttpServletRequest request) {
        var tokenJwt = request.getHeader("Authorization");
        if (tokenJwt != null && tokenJwt.startsWith("Bearer ")) {
            return tokenJwt.replace("Bearer ", "");
        }
        return null;
    }
}
