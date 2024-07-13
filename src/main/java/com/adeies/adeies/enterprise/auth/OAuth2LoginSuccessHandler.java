package com.adeies.adeies.enterprise.auth;

import com.adeies.adeies.enterprise.repository.UserRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class OAuth2LoginSuccessHandler  extends SavedRequestAwareAuthenticationSuccessHandler {


 @Autowired
 private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        if("google".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())){
           DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
          Map<String, Object> attributes = principal.getAttributes();
          String email = attributes.getOrDefault("email", "").toString();

          userRepo.findByEmail(email)
                  .ifPresentOrElse(user ->{

                      this.setAlwaysUseDefaultTargetUrl(true);
                      this.setDefaultTargetUrl("http://localhost:4200/");

                      Cookie idCookie = new Cookie("id-cookie" , Long.toString(user.getId()));
                      idCookie.setPath("/");
                      idCookie.setHttpOnly(false);
                      idCookie.setMaxAge(60*60);
                      response.addCookie(idCookie);

                  },() -> {
                      System.out.println("error ");
                  });
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}
