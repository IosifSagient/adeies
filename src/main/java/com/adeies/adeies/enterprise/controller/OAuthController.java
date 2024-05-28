package com.adeies.adeies.enterprise.controller;

import com.adeies.adeies.enterprise.repository.UserRepo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class OAuthController {

@Autowired
private  UserRepo userRepo;
@Autowired
private JwtDecoder jwtDecoder;
    @GetMapping("/oauth2/code/google")
    public ResponseEntity<HttpServletResponse> googleLogin(@RequestParam("code") String code , HttpServletResponse httpServletResponse) throws Exception {


        String url = "https://oauth2.googleapis.com/token";

        // Replace with your Google Cloud project client ID and client secret
        String clientId = "";
        String clientSecret = "";
        //String redirectUrl = "http://8080/login/oauth2/code/google";

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("redirect_uri", "http://localhost:4200/");  // Replace with your redirect URI
        map.add("code", code);
        map.add("scope","https://www.googleapis.com/auth/calendar https://www.googleapis.com/auth/drive openid profile email");


        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> resp = restTemplate.postForObject(url, map, Map.class);
        HttpHeaders headers = new HttpHeaders();


        //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(resp.get("access_token"));

        HttpEntity<?> request =new HttpEntity<>(headers);
        String userInfo = "https://openidconnect.googleapis.com/v1/userinfo";
        ResponseEntity<Map> response = restTemplate.exchange(userInfo,HttpMethod.GET,request,Map.class);

        System.out.println("sthh " + jwtDecoder.decode(resp.get("id_token")).getExpiresAt() + " " + jwtDecoder.decode(resp.get("id_token")).getIssuedAt());

        String email = (String) response.getBody().get("email");
        //if (email != null && userRepo.findByEmail(email).isPresent())\
        Cookie cookie =new Cookie("jwt",jwtDecoder.decode(resp.get("id_token")).getTokenValue());

        httpServletResponse.addCookie(new Cookie("jwt",jwtDecoder.decode(resp.get("id_token")).getTokenValue()));


        return ResponseEntity.ok(httpServletResponse);
    }
}

