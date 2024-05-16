package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

// TODO: Provide Impl
@Service
class TrainingServiceImpl implements TrainingProvider {


    @Override
    public Optional<User> getTraining(final Long trainingId) {
        throw new UnsupportedOperationException("Not finished yet");
    }
    @Override
    public List<Training> getAllTrainings() {
        throw new UnsupportedOperationException("Not finished yet");
        return
    }

}
