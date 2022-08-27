package com.skillstorm.spyglassapi.models.dtos.incoming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoalRequestDto {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String picture;

    @NotNull
    private double currentlySaved;

    @NotNull
    private Date targetDate;

    @NotNull
    private double targetAmount;
}
