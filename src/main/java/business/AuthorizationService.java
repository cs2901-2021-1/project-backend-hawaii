package business;


import business.custom_exceptions.ConflictException;
import business.custom_exceptions.NotFoundException;
import business.custom_exceptions.UnauthorizedException;
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

    public User addViewer(String email) throws ConflictException {
        if(userRepository.findById(email).isPresent()){
            throw new ConflictException();
        }
        var viewer = new User(email,TYPE_VIEWER);
        return userRepository.save(viewer);
    }


    public void deleteViewer(String email) throws NotFoundException {
        Optional<User> user = userRepository.findById(email);
        if(user.isEmpty()){
            throw new NotFoundException();
        }
        userRepository.delete(user.get());
    }

    public void authorize(OAuth2User user, char type) throws UnauthorizedException{
        if(user == null){
            throw new UnauthorizedException();
        }
        Optional<User> userOptional = userRepository.findById((String)user.getAttributes().get("email"));
        if(userOptional.isEmpty()){
            throw new UnauthorizedException();
        }
        char typeOptional = userOptional.get().getType();
        if(typeOptional != type &&TYPE_ADMIN!=typeOptional){
            throw new UnauthorizedException();
        }
    }

    public List<User> getViewers(){
        List<User> listViewer = new ArrayList<>();
        for(var item :  userRepository.findByType(TYPE_VIEWER)) {
            listViewer.add(new User(item.getEmail(),item.getDateInsert()));
        }
        return listViewer;
    }
}
