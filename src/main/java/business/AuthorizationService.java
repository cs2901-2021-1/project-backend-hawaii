package business;


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

    public ResponseEntity<?> executeIfViewer(OAuth2User user, Object object, Method method) throws InvocationTargetException, IllegalAccessException {
        if(user != null && viewerRepository.findById((String)user.getAttributes().get("email")).isPresent()){
            return (ResponseEntity<?>) method.invoke(object);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "unauthorized"));
    }

    /*
    public ResponseEntity<?> executeIfViewer(OAuth2User user, Function<Object, Object> function) {
        if(user != null && viewerRepository.findById((String)user.getAttributes().get("email")).isPresent()){
            return function.apply()
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "unauthorized"));
    }

     */

}
