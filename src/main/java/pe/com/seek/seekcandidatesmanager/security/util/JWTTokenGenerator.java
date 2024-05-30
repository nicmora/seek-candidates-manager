package pe.com.seek.seekcandidatesmanager.security.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static pe.com.seek.seekcandidatesmanager.security.util.SecurityUtil.getSigningKey;

@Component
public class JWTTokenGenerator {

    @Value("${pe.com.seek.seek-candidates-manager.secret}")
    private String secret;

    public String getJWTToken(String username) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

        return Jwts.builder()
                .setId("seekid")
                .setSubject(username)
                .claim("authorities", grantedAuthorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3_600_000))
                .signWith(getSigningKey(secret), SignatureAlgorithm.HS512).compact();
    }

}
