package com.skillstorm.spyglassapi.models.generic;

import com.skillstorm.spyglassapi.models.dtos.errors.Error;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    public List<T> content;
    public Error error;
    public boolean isSuccess = true ? error == null : false;
}
