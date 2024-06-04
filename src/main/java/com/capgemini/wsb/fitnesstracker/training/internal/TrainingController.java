package com.capgemini.wsb.fitnesstracker.training.internal;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import java.util.Date;



@RestController
@RequestMapping("/api/trainings")
public class TrainingController {

    private final TrainingServiceImpl trainingService;

    public TrainingController(TrainingServiceImpl trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.getAllTrainingsDto();
    }

    @GetMapping("/{id}")
    public TrainingDto getTrainingById(@PathVariable Long id) {
        return trainingService.getTraining(id)
                .map(trainingService::convertToDto)
                .orElseThrow(() -> new TrainingNotFoundException(id));
    }

    @PostMapping
    public TrainingDto createTraining(@RequestBody TrainingDto trainingDto) {
        Training training = trainingService.saveTraining(trainingService.convertToEntity(trainingDto));
        return trainingService.convertToDto(training);
    }

    @PutMapping("/{id}")
    public TrainingDto updateTraining(@PathVariable Long id, @RequestBody TrainingDto trainingDto) {
        Training updatedTraining = trainingService.updateTraining(id, trainingService.convertToEntity(trainingDto));
        return trainingService.convertToDto(updatedTraining);
    }

    @GetMapping("/user/{userId}")
    public List<TrainingDto> getTrainingsByUser(@PathVariable Long userId) {
        User user = new User(userId, null, null, null, null); // Konstruktor z parametrami
        return trainingService.getTrainingsByUser(user).stream()
                .map(trainingService::convertToDto)
                .collect(Collectors.toList());
    }


    @GetMapping("/enddate/{endDate}")
    public List<TrainingDto> getTrainingsByEndDate(@PathVariable Date endDate) {
        return trainingService.getTrainingsByEndDate(endDate).stream()
                .map(trainingService::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/activity/{activityType}")
    public List<TrainingDto> getTrainingsByActivityType(@PathVariable ActivityType activityType) {
        return trainingService.getTrainingsByActivityType(activityType).stream()
                .map(trainingService::convertToDto)
                .collect(Collectors.toList());
    }
}
