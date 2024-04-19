package alessia.LastProjectGestioneEventi.services;

import alessia.LastProjectGestioneEventi.entities.User;
import alessia.LastProjectGestioneEventi.exceptions.BadRequestException;
import alessia.LastProjectGestioneEventi.payload.NewUserPayload;
import alessia.LastProjectGestioneEventi.repositories.EventDAO;
import alessia.LastProjectGestioneEventi.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO usersDAO;

    @Autowired
    private EventDAO eventDAO;


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
        User newUser = new User(body.name(), body.surname(), body.email(), body.password());
        return usersDAO.save(newUser);
    }
}
