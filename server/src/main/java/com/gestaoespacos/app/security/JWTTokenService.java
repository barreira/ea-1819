package com.gestaoespacos.app.security;

import com.gestaoespacos.app.security.DateService;
import com.gestaoespacos.app.security.TokenService;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.compression.GzipCompressionCodec;
import io.jsonwebtoken.impl.crypto.MacProvider;
import lombok.experimental.FieldDefaults;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.Map;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static io.jsonwebtoken.impl.TextCodec.BASE64;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.substringBeforeLast;

@Service
final class JWTTokenService implements Clock, TokenService {
    private static final String DOT = ".";
    private static final GzipCompressionCodec COMPRESSION_CODEC = new GzipCompressionCodec();

    private DateService dates;
    private String issuer;
    private int expirationSec;
    private int clockSkewSec;
    private String secretKey;

    JWTTokenService(final DateService dates,
                    @Value("${jwt.issuer:octoperf}") final String issuer,
                    @Value("${jwt.expiration-sec:86400}") final int expirationSec,
                    @Value("${jwt.clock-skew-sec:300}") final int clockSkewSec,
                    @Value("${jwt.secret:secret}") final String secret) {
        super();
        this.dates = requireNonNull(dates);
        this.issuer = requireNonNull(issuer);
        this.expirationSec = requireNonNull(expirationSec);
        this.clockSkewSec = requireNonNull(clockSkewSec);
        //this.secretKey = BASE64.encode(requireNonNull(secret));

        SecretKey key = MacProvider.generateKey(HS256);
        this.secretKey = BASE64.encode(key.getEncoded());
    }

    @Override
    public String permanent(final Map<String, String> attributes) {
        return newToken(attributes, 0);
    }

    @Override
    public String expiring(final Map<String, String> attributes) {
        return newToken(attributes, expirationSec);
    }

    private String newToken(final Map<String, String> attributes, final int expiresInSec) {
        final DateTime now = dates.now();
        final Claims claims = Jwts
                .claims()
                .setIssuer(issuer)
                .setIssuedAt(now.toDate());

        if (expiresInSec > 0) {
            final DateTime expiresAt = now.plusSeconds(expiresInSec);
            claims.setExpiration(expiresAt.toDate());
        }
        claims.putAll(attributes);

        //String base64Key = DatatypeConverter.printBase64Binary("9iEUhbo6qvX8dyFRVjXMN9QsP1I5qun4SCLuNPFJtZcQ1FBnL5MA2f9NsewgwDN".getBytes());
        //byte[] secretBytes = DatatypeConverter.parseBase64Binary(base64Key);
        //SecretKey key = MacProvider.generateKey(HS256);
        //String base64Encoded = TextCodec.BASE64.encode(key.getEncoded());
        //System.out.println(base64Encoded);

        return Jwts
                .builder()
                .setClaims(claims)
                .signWith(HS256, secretKey)//base64Encoded)//secretBytes)//secretKey)
                .compressWith(COMPRESSION_CODEC)
                .compact();
    }

    @Override
    public Map<String, String> verify(final String token) {
        final JwtParser parser = Jwts
                .parser()
                .requireIssuer(issuer)
                .setClock(this)
                .setAllowedClockSkewSeconds(clockSkewSec)
                .setSigningKey(secretKey);
        return parseClaims(() -> parser.parseClaimsJws(token).getBody());
    }

    @Override
    public Map<String, String> untrusted(final String token) {
        final JwtParser parser = Jwts
                .parser()
                .requireIssuer(issuer)
                .setClock(this)
                .setAllowedClockSkewSeconds(clockSkewSec);

        // See: https://github.com/jwtk/jjwt/issues/135
        final String withoutSignature = substringBeforeLast(token, DOT) + DOT;
        return parseClaims(() -> parser.parseClaimsJwt(withoutSignature).getBody());
    }

    private static Map<String, String> parseClaims(final Supplier<Claims> toClaims) {
        try {
            final Claims claims = toClaims.get();
            final ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
            for (final Map.Entry<String, Object> e: claims.entrySet()) {
                builder.put(e.getKey(), String.valueOf(e.getValue()));
            }
            return builder.build();
        } catch (final IllegalArgumentException | JwtException e) {
            return ImmutableMap.of();
        }
    }

    @Override
    public Date now() {
        final DateTime now = dates.now();
        return now.toDate();
    }
}
