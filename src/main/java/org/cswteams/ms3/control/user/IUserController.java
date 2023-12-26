package org.cswteams.ms3.control.user;

import org.cswteams.ms3.dto.user.UserCreationDTO;
import org.cswteams.ms3.dto.user.UserDTO;
import org.cswteams.ms3.dto.user.UserDetailsDTO;

import java.util.Set;


public interface IUserController {

    Set<UserDTO> getAllUsers();

    void createUser(UserCreationDTO c);

    UserDetailsDTO getSingleUser(long userId);

}