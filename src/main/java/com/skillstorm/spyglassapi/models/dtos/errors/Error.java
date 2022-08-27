package com.skillstorm.spyglassapi.models.dtos.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private int code;
    private String type;
    private String message;
}
