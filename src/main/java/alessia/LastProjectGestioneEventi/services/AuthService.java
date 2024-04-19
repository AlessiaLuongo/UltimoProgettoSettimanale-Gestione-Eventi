package alessia.LastProjectGestioneEventi.services;

import alessia.LastProjectGestioneEventi.entities.User;
import alessia.LastProjectGestioneEventi.exceptions.UnauthorizedException;
import alessia.LastProjectGestioneEventi.payload.UserLoginPayload;
import alessia.LastProjectGestioneEventi.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;

@Service

public class AuthService {

    @Autowired
    private UserService userService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUserAndGenerateToken(UserLoginPayload body){
        User user = this.userService.findByeMail(body.email());
        if (bcrypt.matches(body.password(), user.getPassword())){
            return jwtTools.createToken(user);
        }else{
            throw new UnauthorizedException("Invalid credentials! Please log in again!");
        }
    }
}
