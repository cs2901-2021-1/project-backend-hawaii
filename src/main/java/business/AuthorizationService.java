package business;


import data.dtos.ViewerDTO;
import data.entities.Prediction;
import data.entities.Viewer;
import data.repositories.PredictionRepository;
import data.repositories.TIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import javax.transaction.Transactional;
import java.sql.*;
import java.util.Optional;

@Service
@Transactional
public class AuthorizationService extends DefaultOAuth2UserService {
    @Autowired
    private PredictionRepository predictionRepository;

    @Autowired
    private TIRepository tiRepository;

    @Autowired
    private Environment environment;

    public Viewer addViewer(ViewerDTO viewerDTO) {
        var viewer = new Viewer(viewerDTO.getEmail(), viewerDTO.getFullName());
        return tiRepository.save(viewer);
    }

    private boolean isViewer(String email) {
        Optional<Viewer> optionalViewer = tiRepository.findById(email);
        return optionalViewer.isPresent();
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(request);
        String email = (String) user.getAttributes().get("email");
        System.out.println(email);
        if (!isViewer(email)) {
            throw new InternalAuthenticationServiceException("Error");
        }
        return user;
    }
}
