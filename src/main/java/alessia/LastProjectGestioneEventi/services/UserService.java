package alessia.LastProjectGestioneEventi.services;

import alessia.LastProjectGestioneEventi.entities.Event;
import alessia.LastProjectGestioneEventi.entities.User;
import alessia.LastProjectGestioneEventi.exceptions.BadRequestException;
import alessia.LastProjectGestioneEventi.exceptions.NotFoundException;
import alessia.LastProjectGestioneEventi.payload.NewUserPayload;
import alessia.LastProjectGestioneEventi.repositories.EventDAO;
import alessia.LastProjectGestioneEventi.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDAO usersDAO;

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private PasswordEncoder bcrypt;


    public Page<User> getAllUsers(int page, int size, String sortBy){
        if(size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.usersDAO.findAll(pageable);
    }

    public User save(NewUserPayload body)throws BadRequestException {
        this.usersDAO.findByeMail(body.email()).ifPresent(
                user -> {
                    throw new BadRequestException("The email " + user.getEMail() + "is already used");

                }

        );
        User newUser = new User(body.name(), body.surname(), body.email(), bcrypt.encode(body.password()));
        return usersDAO.save(newUser);
    }
    public User findById(UUID userId){
        return this.usersDAO.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public User findUserByIdAndUpdate(UUID id, User updatedUser){
        Optional<User> optionalUser = usersDAO.findById(id);
        if (optionalUser.isPresent()){
            User found = optionalUser.get();
            found.setName(updatedUser.getName());
            found.setSurname(updatedUser.getSurname());
            found.setEMail(updatedUser.getEMail());

            return this.usersDAO.save(found);
        }else {
            throw new NotFoundException(id);
        }


    }



    public void findUserByIdAndDelete(UUID id){
        Optional<User> optionalUser = usersDAO.findById(id);
        if (optionalUser.isPresent()){
            User found = optionalUser.get();
            this.usersDAO.delete(found);
        }else{
            throw new NotFoundException(id);
        }
    }

    public User findByeMail(String eMail){
        return usersDAO.findByeMail(eMail).orElseThrow(() -> new NotFoundException("No User with this email " + eMail + "found"));
    }
}
