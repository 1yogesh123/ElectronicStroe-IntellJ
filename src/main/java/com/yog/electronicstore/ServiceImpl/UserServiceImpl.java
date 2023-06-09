package com.yog.electronicstore.ServiceImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yog.electronicstore.Dtos.UserDto;
import com.yog.electronicstore.Entity.User;
import com.yog.electronicstore.Exception.ResourceNotFoundException;
import com.yog.electronicstore.Repository.UserRepo;
import com.yog.electronicstore.Service.UserService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper mapper;

    public UserDto createUser(UserDto userDto) {
        logger.info(" Initiated Request for creating user");

        String UserID = UUID.randomUUID().toString();
        userDto.setUserId(UserID);
        //dto->entity
        User user = dtoToEntity(userDto);
        User saveUser = userRepo.save(user);
        //entity-> dto
        UserDto newDto = entityToDto(saveUser);
        logger.info(" Completed Request for creating user");

        return newDto;
    }

    public UserDto updateUser(UserDto userDto, String userId) {
        logger.info(" Initiated Request  for updating user with userId :{}", userId);

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setImageName(userDto.getImageName());
        User updatedUser = userRepo.save(user);
        UserDto updatedDto = entityToDto(updatedUser);
        logger.info(" Completed Request  for updating user with userId :{}", userId);

        return updatedDto;
    }

    //Delete User
    public void deleteUser(String userId) {
        logger.info(" Initiated Request  for deleting user with userId :{}", userId);

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not found"));
        logger.info(" Completed Request  for deleting user with userId :{}", userId);

        this.userRepo.delete(user);
    }

    //GetUserByID
    public UserDto getUserById(String userId) {
        logger.info(" Initiated Request  for getting user with userId :{}", userId);

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));
        logger.info(" Completed Request  for getting user with userId :{}", userId);

        return entityToDto(user);

    }

    //GetAll USer
    public List<UserDto> getAllUser() {
        logger.info(" Initiated Request for getting Users");

        List<User> users = userRepo.findAll();
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        logger.info(" completed Request  for getting users ");

        return dtoList;
    }

    public UserDto getUserByEmail(String email) {
        User user = userRepo.findByEmail(email).
                orElseThrow(() -> new ResourceNotFoundException("User","email",email));
        return entityToDto(user);
    }

    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepo.findByNameContaining(keyword);

        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }

    private UserDto entityToDto(User savedUser) {
//        UserDto userDto=UserDto.builder()
//                .id(savedUser.getId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender())
//                .imageName(savedUser.getImageName()).build();
//                return userDto;

        return mapper.map(savedUser, UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
        return mapper.map(userDto, User.class);

    }


}
