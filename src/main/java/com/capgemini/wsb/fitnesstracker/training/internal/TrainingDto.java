package com.capgemini.wsb.fitnesstracker.training.internal;

import lombok.Data;
import java.util.Date;
import com.capgemini.wsb.fitnesstracker.user.internal.UserDto;

/**
 * Data Transfer Object for Training.
 */
@Data
public class TrainingDto {
    private Long id;
    private UserDto user;
    private Date startTime;
    private Date endTime;
    private ActivityType activityType;
    private double distance;
    private double averageSpeed;
}
