package com.pacoprojects.auth;

import com.pacoprojects.model.Usuario;
import com.pacoprojects.repository.UsuarioRepository;
import com.pacoprojects.security.jwt.JwtUtilService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository repositoryUsuario;
    private final AuthenticationManager authenticationManager;
    private final JwtUtilService jwt;

    public AuthenticateResponse authenticate(AuthenticateRequest authRequest, HttpServletResponse response) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
            return auth((Usuario) authentication.getPrincipal(), response);
        } catch (AuthenticationException exception) {
            if (exception instanceof BadCredentialsException) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário ou senha incorretos");
            } else if (exception instanceof LockedException) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sua conta está suspensa");
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro inesperado no servidor");
        }
    }

    private AuthenticateResponse auth(Usuario usuario, HttpServletResponse response) {

        String basicToken = jwt.generateToken(usuario);
        usuario.setJwt(basicToken);
        repositoryUsuario.save(usuario);

        String fullToken = jwt.configuration().getPrefix().concat(basicToken);
        response.addHeader(HttpHeaders.AUTHORIZATION, fullToken);

        return AuthenticateResponse
                .builder()
                .token(fullToken)
                .build();
    }
}
