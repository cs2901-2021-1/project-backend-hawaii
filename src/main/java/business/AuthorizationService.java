package business;


import data.dtos.ViewerDTO;
import data.entities.Viewer;
import data.repositories.ViewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Optional;

@Service
@Transactional
public class AuthorizationService{

    @Autowired
    private ViewerRepository viewerRepository;


    public Viewer addViewer(ViewerDTO viewerDTO) {
        var viewer = new Viewer(viewerDTO.getEmail(), viewerDTO.getFullName());
        return viewerRepository.save(viewer);
    }
    /*
    private boolean executeIfViewer(Principal principal) {
        Optional<Viewer> optionalViewer = viewerRepository.findById();
        return optionalViewer.isPresent();
    }*/


}
