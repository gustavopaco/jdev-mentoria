package com.pacoprojects.security.jwt;

import com.pacoprojects.model.Usuario;
import com.pacoprojects.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtAuthorizationService {

    private final JwtUtilService jwtUtilService;
    private final UsuarioRepository usuarioRepository;

    public void getAuthorization(HttpServletRequest request) {

        Map<String, Object> map = jwtUtilService.breakToken(request);

        if (!map.isEmpty()) {

            String username = map.get("username").toString();
            String basicToken = map.get("basicToken").toString();

            Optional<Usuario> optionalUsuario = usuarioRepository.findUsuarioByUsername(username);

            if (optionalUsuario.isPresent() && basicToken.equals(optionalUsuario.get().getJwt())) {
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(username, null, optionalUsuario.get().getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new AuthorizationServiceException("Usuário sem permissão, por favor tente logar novamente.");
            }
        }
    }
}
