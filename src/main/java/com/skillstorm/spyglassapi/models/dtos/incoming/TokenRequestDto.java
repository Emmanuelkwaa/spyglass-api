package com.skillstorm.spyglassapi.models.dtos.incoming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequestDto {
    @NotBlank
    private String JwtToken;

    @NotBlank
    private String RefreshToken;
}
