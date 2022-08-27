package com.skillstorm.spyglassapi.models.generic;

import com.skillstorm.spyglassapi.models.dtos.errors.Error;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {
    public T content;
    public Error error;
    public boolean isSuccess = true ? error == null : false;
}
