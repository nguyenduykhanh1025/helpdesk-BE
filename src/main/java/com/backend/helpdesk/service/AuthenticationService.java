package com.backend.helpdesk.service;

import com.backend.helpdesk.common.CommonMethods;
import com.backend.helpdesk.configurations.TokenProvider;
import com.backend.helpdesk.entity.RoleEntity;
import com.backend.helpdesk.entity.UserEntity;
import com.backend.helpdesk.exception.UserException.EmailUserIsNotMatch;
import com.backend.helpdesk.repository.RoleRepository;
import com.backend.helpdesk.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GooglePublicKeysManager;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
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

    public String getEmailFromTokenUser(String tokenGoogle) throws IOException {
        final GoogleIdToken idToken = GoogleIdToken.parse(jsonFactory, tokenGoogle);
        final GooglePublicKeysManager manager = new GooglePublicKeysManager.Builder(transport, jsonFactory).build();
        final GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(manager).setAudience(Arrays.asList("65915084404-pmafgo4444jbsbfn2mp53oigm0qm9lue.apps.googleusercontent.com")).build();

        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            return payload.getEmail();
        }
        return null;
    }

    public ResponseEntity<String> generateToken(String email) {

        checkForUserResister(email);

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

    public void checkForUserResister(String email) {

        if (CommonMethods.isEmailNovaHub(email)) {
            if (userRepository.findByEmail(email) == null) {
                saveNewAccount(email);
            }
        } else {
            throw new EmailUserIsNotMatch();
        }

    }

    public void saveNewAccount(String email) {

        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(email);
        userEntity.setPassword(new BCryptPasswordEncoder().encode(email));
        userEntity.setFirstName("default");
        userEntity.setLastName("default");

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
