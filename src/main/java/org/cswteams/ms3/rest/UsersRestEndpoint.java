package org.cswteams.ms3.rest;

import org.cswteams.ms3.control.user.IUserController;
import org.cswteams.ms3.dto.user.UserCreationDTO;
import org.cswteams.ms3.dto.user.UserDTO;
import org.cswteams.ms3.dto.user.UserDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/users/")
public class UsersRestEndpoint {

    @Autowired
    private IUserController userController;

    @RequestMapping(method = RequestMethod.POST, path = "")
    public ResponseEntity<?> createUser(@RequestBody() UserCreationDTO doctor) {
        if (doctor != null) {
            userController.createUser(doctor);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers() {
        Set<UserDTO> utenti = userController.getAllUsers();
        return new ResponseEntity<>(utenti, HttpStatus.FOUND);
    }


    @RequestMapping(method = RequestMethod.GET, path = "/user_id={userId}")
    public ResponseEntity<?> getSingleUser(@PathVariable Long userId) {
        UserDetailsDTO u = userController.getSingleUser(userId);
        return new ResponseEntity<>(u, HttpStatus.FOUND);
    }
}
