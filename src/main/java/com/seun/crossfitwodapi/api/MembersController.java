package com.seun.crossfitwodapi.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seun.crossfitwodapi.domain.Members;
import com.seun.crossfitwodapi.domain.MembersRoles;
import com.seun.crossfitwodapi.domain.Roles;
import com.seun.crossfitwodapi.domain.dto.MembersDTO;
import com.seun.crossfitwodapi.service.MembersService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/member")
public class MembersController {
    private final MembersService membersService;

    @GetMapping
    public ResponseEntity<List<Members>> getAllMembers(@RequestParam(defaultValue = "0") Integer pageNo,
                                       @RequestParam(defaultValue = "4") Integer elementPerPage) {
        return ResponseEntity.ok().body(membersService.getAllMembers(pageNo, elementPerPage));
    }

    @PostMapping
    public ResponseEntity<Members> saveOneMember(@Valid @RequestBody MembersDTO membersDTO) {
        URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/member")));
        return ResponseEntity.created(uri).body(membersService.saveOneMember(membersDTO));
    }

    @GetMapping (path = "{memberId}")
    public ResponseEntity<Optional<Members>> getMemberById(@PathVariable Long memberId) {
        return ResponseEntity.ok().body(membersService.getMemberById(memberId));
    }

    @PostMapping(path = "/role")
    public ResponseEntity<Roles> saveRole(@RequestBody Roles role) {
        URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/member/role")));
        return ResponseEntity.created(uri).body(membersService.saveRole(role));
    }

    @PostMapping(path = "/role")
    public ResponseEntity<?> saveRole(@RequestBody RoleToMemberForm form) {
        membersService.addRoleToMembers(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        System.out.println(authorizationHeader);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                Members member = membersService.getMemberByUsername(username);
                System.out.println(member);
                String accessToken = JWT.create().withSubject(member.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", member.getMembersRoles().stream().map(MembersRoles::getRoles).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                log.error("Error logging in: {}", exception.getMessage());
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("errorMessage", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    @PatchMapping(path = "{memberId}")
    public ResponseEntity<Members> updateOneMember(@PathVariable Long memberId,
                                          @RequestParam(required = false) String name,
                                          @RequestParam(required = false) String email,
                                          @RequestParam(required = false) String username,
                                          @RequestParam(required = false) String password) {
        return ResponseEntity.ok().body(membersService.updateOneMember(memberId, name, email, username, password));
    }

    @DeleteMapping(path = "{memberId}")
    public ResponseEntity<String> deleteOneMember(@PathVariable Long memberId) {
        membersService.deleteOneMember(memberId);
        return ResponseEntity.noContent().build();
    }
}

@Data
class RoleToMemberForm {
    private String username;
    private String roleName;
}
