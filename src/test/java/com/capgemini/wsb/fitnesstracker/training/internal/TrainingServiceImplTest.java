import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

class TrainingServiceImplTest {

    private TrainingServiceImpl trainingService;

    @Mock
    private TrainingRepository trainingRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainingService = new TrainingServiceImpl(trainingRepository);
    }

    @Test
    void testGetTraining() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        Training training = new Training(user, new Date(), new Date(), ActivityType.RUNNING, 5.0, 10.0);
        when(trainingRepository.findById(1L)).thenReturn(Optional.of(training));

        Optional<Training> result = trainingService.getTraining(1L);
        assertTrue(result.isPresent());
        assertEquals(ActivityType.RUNNING, result.get().getActivityType());
    }

    @Test
    void testGetAllTrainings() {
        User user1 = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        User user2 = new User("Jane", "Doe", LocalDate.of(1992, 2, 2), "jane.doe@example.com");
        Training training1 = new Training(user1, new Date(), new Date(), ActivityType.RUNNING, 5.0, 10.0);
        Training training2 = new Training(user2, new Date(), new Date(), ActivityType.CYCLING, 10.0, 20.0);
        when(trainingRepository.findAll()).thenReturn(Arrays.asList(training1, training2));

        List<Training> result = trainingService.getAllTrainings();
        assertEquals(2, result.size());
    }
}
