package com.skillstorm.spyglassapi.models.dtos.incoming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequestDto {
    private Long id;

    @NotBlank
    private String name;
}
