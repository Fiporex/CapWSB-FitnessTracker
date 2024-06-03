package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service implementation for managing trainings.
 */
@Service
class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;
    /**
     * Constructs a new TrainingServiceImpl with the specified TrainingRepository.
     *
     * @param trainingRepository the training repository
     */
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
    /**
     * Retrieves all trainings as TrainingDto objects.
     *
     * @return A list of TrainingDto objects
     */
    public List<TrainingDto> getAllTrainingsDto() {
        List<Training> trainings = getAllTrainings();
        return trainings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    /**
     * Converts a Training object to a TrainingDto object.
     *
     * @param training the training object to be converted
     * @return the converted TrainingDto object
     */
    private TrainingDto convertToDto(Training training) {
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
    /**
     * Converts a User object to a UserDto object.
     *
     * @param user the user object to be converted
     * @return the converted UserDto object
     */
    private UserDto convertUserToDto(User user) {
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getBirthdate(), user.getEmail());
    }
}

