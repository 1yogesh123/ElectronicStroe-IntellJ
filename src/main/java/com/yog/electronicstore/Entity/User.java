package com.yog.electronicstore.Entity;

import lombok.*;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.springframework.boot.autoconfigure.web.WebProperties;

import javax.persistence.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="Users")

public class User {
    @Id
    private String userId;
    @Column(name="User_Name")
    private String name;
    @Column(name="User_Email",unique = true)
    private String email;
    @Column(name="User_Password",length = 10)
    private String password;
    private String gender;
    @Column(name="About",length = 1000)
    private String about;
    private String imageName;


}
