package br.ufpb.dcx.dsc.repositorios.controller;

import br.ufpb.dcx.dsc.repositorios.dto.LoginRequesDTO;
import br.ufpb.dcx.dsc.repositorios.dto.LoginResponseDTO;
import br.ufpb.dcx.dsc.repositorios.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class authController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public authController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequesDTO loginRequesDTO){
        System.out.println();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequesDTO.getUserName(), loginRequesDTO.getSenha())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequesDTO.getUserName());

        final String token = jwtService.genererToken(userDetails);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
