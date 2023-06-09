package com.yog.electronicstore.Controller;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.internal.ImmutableEntityEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yog.electronicstore.Dtos.ApiResponseMessage;
import com.yog.electronicstore.Dtos.UserDto;
import com.yog.electronicstore.Entity.User;
import com.yog.electronicstore.Service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     *
     * @author Yogesh Shelar
     * @ApiNote This API is used to create user
     * @param userDto
     * @return
     */
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Request Starting for service layer to create user");
        UserDto userDto1 = userService.createUser(userDto);
        log.info("Request completed for service layer to save the user");
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);

    }

    /**
     * @author Yogesh Shelar
     * @ApiNote This API is used to Update user
     * @param userId
     * @param userDto
     * @return
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid
                                              @PathVariable("userId") String userId, @RequestBody UserDto userDto) {
        log.info("Request Starting for service layer to update user");

        UserDto updatedUserDto = userService.updateUser(userDto, userId);
        log.info("Request Starting for service layer to update user");

        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);

    }

    /**
     * @author Yogesh Shelar
     * @ApiNote This API is used to Delete user
     * @param userId
     * @return
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) {
        log.info("Request Starting for service layer to delete user by userId {}",userId);

        userService.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage
                .builder()
                .message("User is deleted Sucessfully!!  ")
                .success(true).
                status(HttpStatus.OK)
                .build();
        log.info("Request completed for service layer to delete user by userId {}",userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * @author Yogesh Shelar
     * @ApiNote This API is used to GetAllUser Information
     * @return
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.info("Request  for service layer to get All user ");

        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }


    /**
     * @author Yogesh Shelar
     * @ApiNote This API is used to Get Single User by Id
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")

    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        log.info("Request for service layer to get user by userId {}",userId);

        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);

    }

    /**
     * @author Yogesh Shelar
     * @ApiNote This API is used to Get User By Email
     * @param email
     * @return
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUser(@PathVariable String email) {
        log.info("Request  for service layer to get user by email {}",email);

        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);

    }


    /**
     * @author Yogesh Shelar
     * @ApiNote This API is used to Search user
     * @param keywords
     * @return
     */
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords) {
        log.info("Request for service layer to seach user by keywords {}",keywords);

        return new ResponseEntity<>(userService.searchUser(keywords), HttpStatus.OK);

    }


}
