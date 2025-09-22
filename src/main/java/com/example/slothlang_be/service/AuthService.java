package com.example.slothlang_be.service;


import com.example.slothlang_be.dto.request.auth.AuthGoogleRequest;
import com.example.slothlang_be.dto.request.auth.TokenRequest;
import com.example.slothlang_be.dto.response.auth.AuthResponse;
import com.example.slothlang_be.entity.RefreshToken;
import com.example.slothlang_be.entity.User;
import com.example.slothlang_be.enums.Role;
import com.example.slothlang_be.enums.UserStatus;
import com.example.slothlang_be.exception.AppException;
import com.example.slothlang_be.exception.AuthErrorCode;
import com.example.slothlang_be.exception.InValidErrorCode;
import com.example.slothlang_be.exception.UserErrorCode;
import com.example.slothlang_be.repository.RefreshTokenRepository;
import com.example.slothlang_be.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    UserService userService;
    RefreshTokenService refreshTokenService;
    RefreshTokenRepository refreshTokenRepository;


    @NonFinal
    @Value("${jwt.refreshTokenSecret}")
    private String REFRESH_TOKEN_SECRET;

    //    key access token
    @NonFinal
    @Value("${jwt.accessTokenSecret}")
    private String ACCESS_TOKEN_SECRET;

    @NonFinal
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @NonFinal
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;

    @NonFinal
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String GOOGLE_REDIRECT_URI;

    public AuthResponse authGoogle(AuthGoogleRequest authGoogleRequest) {

        try {
            String tokenUrl = "https://oauth2.googleapis.com/token";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", authGoogleRequest.getCode());
            params.add("client_id", GOOGLE_CLIENT_ID);
            params.add("client_secret", GOOGLE_CLIENT_SECRET);
            params.add("redirect_uri", GOOGLE_REDIRECT_URI);
            params.add("grant_type", "authorization_code");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);
            Map<String, Object> tokenData = response.getBody();

            String accessTokenGG = (String) tokenData.get("access_token");
            String idToken = (String) tokenData.get("id_token");

            String userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + accessTokenGG;
            ResponseEntity<Map> userResponse = restTemplate.getForEntity(userInfoUrl, Map.class);
            Map<String, Object> userInfo = userResponse.getBody();

            User user = userService.findByGoogleId(idToken);

            if (user == null) {
                user = userService.saveNewUser(userInfo);
            } else if (user.getStatus() == UserStatus.LOCKED) {
                throw new AppException(UserErrorCode.USER_LOCKED);
            }

            return AuthResponse.builder()
                    .accessToken(generateAccessToken(user))
                    .refreshToken(generateRefreshToken(user))
                    .build();
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
            throw new AppException(AuthErrorCode.AUTH_GOOGLE_ERROR);
        }


    }




    public AuthResponse refreshToken (TokenRequest tokenRequest) {

      try {
          if (!verifyRefreshToken(tokenRequest)) {
              throw new AppException(AuthErrorCode.UNAUTHENTICATED);
          }

          SignedJWT signedJWT = SignedJWT.parse(tokenRequest.getToken());
          String userId = signedJWT.getJWTClaimsSet().getSubject(); // Đây là username
          return AuthResponse.builder()
                  .accessToken(generateAccessToken(userService.findById(userId)))
                  .build();
      } catch (Exception e) {
          throw new AppException(AuthErrorCode.UNAUTHENTICATED);
      }
    }


    public boolean verifyRefreshToken(TokenRequest tokenRequest)
    {
        try {

            JWSVerifier verifier = new MACVerifier(REFRESH_TOKEN_SECRET.getBytes());
//        token cua  ng dung
            SignedJWT signedJWT = SignedJWT.parse(tokenRequest.getToken());
            var jit = signedJWT.getJWTClaimsSet().getJWTID();

//        lay ra han cua token
            Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();

//        tra ve true hoac flase
            var verified = signedJWT.verify(verifier);

//            neu loi secret
            if (!verified) {
                throw new AppException(AuthErrorCode.UNAUTHENTICATED);
            }
            //            neu het han
            else if ( !expityTime.after(new Date()))  {
                refreshTokenService.deleteRefreshToken(jit);
                throw new AppException(AuthErrorCode.UNAUTHENTICATED);
            }
//            neu khong co trong db
            else if (!refreshTokenService.existsByJit(jit)) {
                throw new AppException(AuthErrorCode.UNAUTHENTICATED);
            }

            else {
                return  true;
            }

        } catch (Exception e) {

            throw new AppException(AuthErrorCode.UNAUTHENTICATED);
        }
    }


    public String generateAccessToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);// tao header
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder() // tao body
                .subject(user.getId())
                .issuer("slothlang.vn") // token nay dc issuer tu ai
                .issueTime(new Date()) // thoi diem hien tai
                .expirationTime(new Date(
                        Instant.now().plus(15, ChronoUnit.MINUTES) // 15 phút là chuẩn an toàn
                                .toEpochMilli()))
                .claim("scope", user.getRole())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject()); // tao pay load
        JWSObject jwsObject = new JWSObject(header, payload);// built thong tin token
        try {
            jwsObject.sign(new MACSigner(ACCESS_TOKEN_SECRET.getBytes())); // ky token
            return jwsObject.serialize(); // tra ve kieu string
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateRefreshToken(User user) {
        String jit = UUID.randomUUID().toString();

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);// tao header
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder() // tao body
                .subject(user.getId())
                .issuer("slothlang.vn") // token nay dc issuer tu ai
                .issueTime(new Date()) // thoi diem hien tai
                .jwtID(jit)
                .expirationTime(new Date(
                        Instant.now().plus(30, ChronoUnit.DAYS)  // 30 ngày
                                .toEpochMilli()))

                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject()); // tao pay load
        JWSObject jwsObject = new JWSObject(header, payload);// built thong tin token
        try {
            jwsObject.sign(new MACSigner(REFRESH_TOKEN_SECRET.getBytes())); // ky token
            refreshTokenRepository.save(RefreshToken.builder()
                    .jit(jit)
                    .expireDate(LocalDateTime.now())
                    .build());

            return jwsObject.serialize(); // tra ve kieu string
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

}
