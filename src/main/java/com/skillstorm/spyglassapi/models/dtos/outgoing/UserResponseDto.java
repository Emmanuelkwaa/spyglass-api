package com.skillstorm.spyglassapi.models.dtos.outgoing;

import com.skillstorm.spyglassapi.models.dbSet.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Role> roles;
}
