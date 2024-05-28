package com.adeies.adeies.enterprise.auth;

import com.adeies.adeies.enterprise.entities.User;
import com.adeies.adeies.enterprise.repository.UserRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
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
//                      DefaultOAuth2User newUser =  new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(user.getRole().name())),
//                              attributes,"email");
//                      Authentication securityAuth = new OAuth2AuthenticationToken(newUser,List.of(new SimpleGrantedAuthority(user.getRole().name())),
//                              oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
//                      SecurityContextHolder.getContext().setAuthentication(securityAuth);
                      this.setAlwaysUseDefaultTargetUrl(true);
                      this.setDefaultTargetUrl("http://localhost:4200/");


                  },() -> {
                      System.out.println("ton mpoul ");
                  });
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}
