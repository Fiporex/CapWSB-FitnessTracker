package com.capgemini.wsb.fitnesstracker.training.internal;
import lombok.Data;
import java.util.Date;
@Data
public class TrainingDTO {
    private Long id;
    private UserDTO user;
    private Date startTime;
    private Date endTime;
    private ActivityType activityType;
    private double distance;
    private double averageSpeed;

    }
}
