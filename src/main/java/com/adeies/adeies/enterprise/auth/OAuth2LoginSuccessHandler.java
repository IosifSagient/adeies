package com.adeies.adeies.enterprise.auth;

import com.adeies.adeies.enterprise.entities.DaysOff;
import com.adeies.adeies.enterprise.entities.DaysOffDefinition;
import com.adeies.adeies.enterprise.entities.EmployeeCard;
import com.adeies.adeies.enterprise.entities.User;
import com.adeies.adeies.enterprise.enums.Role;
import com.adeies.adeies.enterprise.repository.DaysOffDefinitionRepo;
import com.adeies.adeies.enterprise.repository.DaysOffRepo;
import com.adeies.adeies.enterprise.repository.EmployeeRepo;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    @Autowired
    DaysOffDefinitionRepo daysOffDefinitionRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private DaysOffRepo daysOffRepo;

    private static User getUser(String firstName, String lastName, String email) {
        User newUser = new User();

        EmployeeCard newEmployeeCard = new EmployeeCard();
        newEmployeeCard.setFirstName(firstName);
        newEmployeeCard.setLastName(lastName);

        newUser.setEmail(email);
        newUser.setLanguage("en_US");
        newUser.setPassword("12");

        newUser.setRole(Role.USER);
        newUser.setEmployeeCard(newEmployeeCard);
        return newUser;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        if ("google".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {
            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
            Map<String, Object> attributes = principal.getAttributes();
            String email = attributes.getOrDefault("email", "").toString();
            String firstName = attributes.getOrDefault("given_name", "sth").toString();
            String lastName = attributes.getOrDefault("family_name", "sth").toString();
            List<DaysOffDefinition> definitions = daysOffDefinitionRepo.findAll();
            System.out.println("kati kati kati ");

                    userRepo.findByEmail(email)
                            .ifPresentOrElse(user -> {

                                this.setAlwaysUseDefaultTargetUrl(true);
                                this.setDefaultTargetUrl("http://localhost:4200/");

                                Cookie idCookie = new Cookie("id-cookie", Long.toString(user.getId()));
                                idCookie.setPath("/");
                                idCookie.setHttpOnly(false);
                                idCookie.setMaxAge(60 * 60);
                                response.addCookie(idCookie);

                            }, () -> {
                                User newUser = getUser(firstName, lastName, email);

                                userRepo.save(newUser);
                                DaysOff daysOff = new DaysOff();
                                daysOff.setUser(newUser);
                                definitions.forEach(daysOffDefinition -> {
                                    daysOffRepo.save(new DaysOff(newUser,daysOffDefinition,20,20)); //TODO : NA FTIAKSW ENA MAPPER GIA KATHE EIDOS ADEIAS NA VAZEI TIS KATALLILES MERES
                                });

                                this.setAlwaysUseDefaultTargetUrl(true);
                                this.setDefaultTargetUrl("http://localhost:4200/");

                                Cookie idCookie = new Cookie("id-cookie", Long.toString(newUser.getId()));
                                idCookie.setPath("/");
                                idCookie.setHttpOnly(false);
                                idCookie.setMaxAge(60 * 60);
                                response.addCookie(idCookie);
                            });
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}
