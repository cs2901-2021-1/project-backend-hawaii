package business;


import business.custom_exceptions.ConflictException;
import business.custom_exceptions.NotFoundException;
import business.custom_exceptions.UnauthorizedException;
import data.dtos.UserDTO;
import data.entities.User;
import data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static config.GlobalConstants.TYPE_ADMIN;
import static config.GlobalConstants.TYPE_VIEWER;

@Service
@Transactional
public class AuthorizationService{

    @Autowired
    private UserRepository userRepository;

    public User addViewer(UserDTO userDTO) throws ConflictException {
        if(userRepository.findById(userDTO.getEmail()).isPresent()){
            throw new ConflictException();
        }
        var viewer = new User(userDTO.getEmail(),TYPE_VIEWER);
        return userRepository.save(viewer);
    }


    public void deleteViewer(UserDTO userDTO) throws NotFoundException {
        Optional<User> user = userRepository.findById(userDTO.getEmail());
        if(user.isEmpty()){
            throw new NotFoundException();
        }
        userRepository.delete(user.get());
    }

    public void authorize(OAuth2User user, char type) throws UnauthorizedException{
        if(user == null || userRepository.findByEmailAndType((String)user.getAttributes().get("email"),type).isEmpty()){
            throw new UnauthorizedException();
        }
    }

    public List<UserDTO> getViewers(){
        List<UserDTO> listViewer = new ArrayList<>();
        for(var item :  userRepository.findByType(TYPE_VIEWER)) {
            listViewer.add(new UserDTO(item.getEmail(), item.getDateInsert()));
        }
        return listViewer;
    }
}
