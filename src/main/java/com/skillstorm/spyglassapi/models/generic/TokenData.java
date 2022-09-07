package com.skillstorm.spyglassapi.models.generic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenData {
    private String JwtToken;
    private String RefreshToken;
}
