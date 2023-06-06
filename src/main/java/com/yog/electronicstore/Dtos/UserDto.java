package com.yog.electronicstore.Dtos;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String userId;
    @Size(min=3,max=20,message="Name must be in between 3 char to 10 char")
    private String name;
    @NotEmpty
    @Email(message = "Invalid email format")
    private String email;
    private String imageName;
    @NotEmpty(message="Write something about yourself")
    private String about;
    @NotBlank(message="password must be required !!!")
    private String password;
    @Size(min=4,max=6,message = "Invalid Gender !")
    private String gender;

}
