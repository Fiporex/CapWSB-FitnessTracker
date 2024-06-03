package com.capgemini.wsb.fitnesstracker.training.internal;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/trainings")
/**
 * Controller for handling training-related requests.
 */
public class TrainingController {

    private final TrainingServiceImpl trainingService;
    /**
     * Constructs a new TrainingController with the specified TrainingServiceImpl.
     *
     * @param trainingService the training service implementation
     */
    public TrainingController(TrainingServiceImpl trainingService) {
        this.trainingService = trainingService;
    }
    /**
     * Retrieves all trainings as TrainingDto objects.
     *
     * @return A list of TrainingDto objects
     */
    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.getAllTrainingsDto();
    }
}
