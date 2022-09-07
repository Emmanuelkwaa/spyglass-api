package com.skillstorm.spyglassapi.models.dtos.outgoing;

import com.skillstorm.spyglassapi.models.dtos.errors.Error;
import com.skillstorm.spyglassapi.models.generic.TokenData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResult {
    private UserResponseDto user;
    private TokenData tokenData;
    private Error error;
    private boolean isSuccess = true;
}
