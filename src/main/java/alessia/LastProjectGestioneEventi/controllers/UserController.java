package alessia.LastProjectGestioneEventi.controllers;

import alessia.LastProjectGestioneEventi.entities.User;
import alessia.LastProjectGestioneEventi.exceptions.BadRequestException;
import alessia.LastProjectGestioneEventi.payload.NewUserPayload;
import alessia.LastProjectGestioneEventi.payload.NewUserResponse;
import alessia.LastProjectGestioneEventi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy){
        return this.userService.getAllUsers(page, size, sortBy);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    private NewUserResponse saveNewUser(@RequestBody @Validated NewUserPayload body, BindingResult validation){
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        return new NewUserResponse(userService.save(body).getId());
    }
    @GetMapping("/{userId}")
    private User getSingleUser(@PathVariable UUID userId){
        return this.userService.findById(userId);
    }
    @PutMapping("/{userId}")
    private User findUserByIdAndUpdate(@PathVariable UUID id, @RequestBody User body){
        return this.userService.findUserByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void findUserByIdAndDelete(@PathVariable UUID id){
        this.userService.findUserByIdAndDelete(id);
    }



}
