package alessia.LastProjectGestioneEventi.controllers;


import alessia.LastProjectGestioneEventi.entities.User;
import alessia.LastProjectGestioneEventi.exceptions.BadRequestException;
import alessia.LastProjectGestioneEventi.payload.NewUserPayload;
import alessia.LastProjectGestioneEventi.payload.NewUserResponse;
import alessia.LastProjectGestioneEventi.payload.UserLoginPayload;
import alessia.LastProjectGestioneEventi.payload.UserLoginResponsePayload;
import alessia.LastProjectGestioneEventi.services.AuthService;
import alessia.LastProjectGestioneEventi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public UserLoginResponsePayload login(@RequestBody UserLoginPayload body){
        return new UserLoginResponsePayload(this.authService.authenticateUserAndGenerateToken(body));

    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponse saveUser(@RequestBody @Validated NewUserPayload body, BindingResult validation){
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        return new NewUserResponse(this.userService.save(body).getId());
    }


}
