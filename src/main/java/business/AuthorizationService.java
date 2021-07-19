package business;


import business.custom_exceptions.ConflictException;
import business.custom_exceptions.NotFoundException;
import business.custom_exceptions.UnauthorizedException;
import data.dtos.ViewerDTO;
import data.entities.Viewer;
import data.repositories.TIRepository;
import data.repositories.ViewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AuthorizationService{

    @Autowired
    private ViewerRepository viewerRepository;

    @Autowired
    private TIRepository tiRepository;


    public Viewer addViewer(ViewerDTO viewerDTO) throws ConflictException {
        if(viewerRepository.findById(viewerDTO.getEmail()).isPresent()){
            throw new ConflictException();
        }
        var viewer = new Viewer(viewerDTO.getEmail());
        return viewerRepository.save(viewer);
    }


    public void deleteViewer(ViewerDTO viewerDTO) throws NotFoundException {
        Optional<Viewer> viewer = viewerRepository.findById(viewerDTO.getEmail());
        if(viewer.isEmpty()){
            throw new NotFoundException();
        }
        viewerRepository.delete(viewer.get());
    }

    public void authorizeViewer(OAuth2User user) throws UnauthorizedException{
        if(user == null || viewerRepository.findById((String)user.getAttributes().get("email")).isEmpty()){
            throw new UnauthorizedException();
        }
    }

    public void authorizeTI(OAuth2User user) throws UnauthorizedException{
        if(user == null || tiRepository.findById((String)user.getAttributes().get("email")).isEmpty()){
            throw new UnauthorizedException();
        }
    }
}
