package com.yog.electronicstore.Service;

import java.util.List;

import com.yog.electronicstore.Dtos.UserDto;

public interface UserService {
UserDto createUser(UserDto userDto);
UserDto updateUser(UserDto userDto,String userId);
void deleteUser(String userId);
UserDto getUserById(String userId);
UserDto getUserByEmail(String email);
List<UserDto> getAllUser();
List<UserDto> searchUser(String keywords);
}
