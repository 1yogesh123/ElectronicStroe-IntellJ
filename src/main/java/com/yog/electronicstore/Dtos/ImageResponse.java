package com.yog.electronicstore.Dtos;

import lombok.*;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ImageResponse {
    private String imageName;
    private String message;
    private boolean success;
    private HttpStatus status;
}

