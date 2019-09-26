package com.backend.helpdesk.service;

import com.backend.helpdesk.common.CommonMethods;
import com.backend.helpdesk.configurations.TokenProvider;
import com.backend.helpdesk.entity.RoleEntity;
import com.backend.helpdesk.entity.UserEntity;
import com.backend.helpdesk.exception.UserException.EmailUserIsNotMatch;
import com.backend.helpdesk.repository.RoleRepository;
import com.backend.helpdesk.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {

    private static final HttpTransport transport = new NetHttpTransport();
    private static final JacksonFactory jsonFactory = new JacksonFactory();

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    public String getEmailFromTokenUser(String tokenGoogle) throws IOException, GeneralSecurityException {

        GoogleIdToken idToken = GoogleIdToken.parse(jsonFactory, tokenGoogle);
        System.out.println(idToken);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            String email = payload.getEmail();

            checkForUserRegister(email,
                    (String) payload.get("family_name"),
                    (String) payload.get("given_name"));
            return email;
        }
        return null;
    }

    public ResponseEntity<String> generateToken(String email) {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        email
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(token);
    }

    public void checkForUserRegister(String email, String firstName, String lastName) {

        if (CommonMethods.isEmailNovaHub(email)) {
            if (userRepository.findByEmail(email) == null) {
                saveNewAccount(email, firstName, lastName);
            }
        } else {
            throw new EmailUserIsNotMatch();
        }

    }

    public void saveNewAccount(String email, String firstName, String lastName) {

        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(email);
        userEntity.setPassword(new BCryptPasswordEncoder().encode(email));

        if (firstName.equals("") || firstName == null) {
            userEntity.setFirstName("default");
        }
        userEntity.setFirstName(firstName);

        if (lastName.equals("") || lastName == null) {
            userEntity.setLastName("default");
        }
        userEntity.setLastName(lastName);

        // set role default
        RoleEntity roleEntity = roleRepository.findByName("ROLE_EMPLOYEES");
        Set<RoleEntity> roleEntities = new HashSet<RoleEntity>() {
            {
                add(roleEntity);
            }
        };
        userEntity.setRoleEntities(roleEntities);
        userRepository.save(userEntity);
    }
}
