package com.yog.electronicstore.Dtos;

import com.yog.electronicstore.Entity.BaseEntity;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomDto extends BaseEntity {
    private String isActive;

    private String createdBy;

    private LocalDateTime createdOn;

    private String modifiedBy;

    private LocalDateTime modifiedOn;

}