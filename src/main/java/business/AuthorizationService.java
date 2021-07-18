package business;


import business.custom_exceptions.CustomNotFoundException;
import business.custom_exceptions.UnauthorizeException;
import data.dtos.ViewerDTO;
import data.entities.Viewer;
import data.repositories.ViewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional
public class AuthorizationService{

    @Autowired
    private ViewerRepository viewerRepository;


    public Viewer addViewer(ViewerDTO viewerDTO) {
        var viewer = new Viewer(viewerDTO.getEmail(), viewerDTO.getFullName());
        return viewerRepository.save(viewer);
    }

    public void authorizeViewer(OAuth2User user) {
        if(user == null || viewerRepository.findById((String)user.getAttributes().get("email")).isEmpty()){
            throw new UnauthorizeException("Unauthorized");
        }
    }
}
