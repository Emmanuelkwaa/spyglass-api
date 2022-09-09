package com.skillstorm.spyglassapi.models.dbSet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goal{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private Long id;

    @Column
    @NotBlank
    private String name;

    @Column(name = "discription")
    @NotBlank
    private String description;

    @Column
    private String picture;

    @Column(name = "currently_saved")
    @NotNull
    private double currentlySaved;

    @Column(name = "target_date")
    @NotNull
    private Date targetDate;

    @Column(name = "target_amount")
    @NotNull
    private double targetAmount;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
