package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserDto;
import org.springframework.stereotype.Service;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service implementation for managing trainings.
 */
import java.util.Date;

@Service
class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public Optional<Training> getTraining(final Long trainingId) {
        return trainingRepository.findById(trainingId);
    }

    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    public List<Training> getTrainingsByUser(User user) {
        return trainingRepository.findAll().stream()
                .filter(training -> training.getUser().equals(user))
                .collect(Collectors.toList());
    }

    public List<Training> getTrainingsByEndDate(Date endDate) {
        return trainingRepository.findAll().stream()
                .filter(training -> training.getEndTime().after(endDate))
                .collect(Collectors.toList());
    }

    public List<Training> getTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findAll().stream()
                .filter(training -> training.getActivityType().equals(activityType))
                .collect(Collectors.toList());
    }

    public Training saveTraining(Training training) {
        return trainingRepository.save(training);
    }

    public Training updateTraining(Long id, Training updatedTraining) {
        Optional<Training> trainingOptional = trainingRepository.findById(id);
        if (trainingOptional.isPresent()) {
            Training training = trainingOptional.get();
            training.setDistance(updatedTraining.getDistance());
            training.setAverageSpeed(updatedTraining.getAverageSpeed());
            training.setEndTime(updatedTraining.getEndTime());
            return trainingRepository.save(training);
        } else {
            throw new TrainingNotFoundException(id);
        }
    }


    public List<TrainingDto> getAllTrainingsDto() {
        List<Training> trainings = getAllTrainings();
        return trainings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TrainingDto convertToDto(Training training) {
        TrainingDto dto = new TrainingDto();
        dto.setId(training.getId());
        dto.setUser(convertUserToDto(training.getUser()));
        dto.setStartTime(training.getStartTime());
        dto.setEndTime(training.getEndTime());
        dto.setActivityType(training.getActivityType());
        dto.setDistance(training.getDistance());
        dto.setAverageSpeed(training.getAverageSpeed());
        return dto;
    }

    public Training convertToEntity(TrainingDto trainingDto) {
        return new Training(
                convertDtoToUser(trainingDto.getUser()),
                trainingDto.getStartTime(),
                trainingDto.getEndTime(),
                trainingDto.getActivityType(),
                trainingDto.getDistance(),
                trainingDto.getAverageSpeed()
        );
    }

    private UserDto convertUserToDto(User user) {
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getBirthdate(), user.getEmail());
    }

    private User convertDtoToUser(UserDto userDto) {
        return new User(userDto.id(), userDto.firstName(), userDto.lastName(), userDto.birthdate(), userDto.email());
    }
}
