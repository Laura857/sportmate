package com.example.sportmate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserActivityId implements Serializable {
    private Integer userId;
    private Integer activityId;
}