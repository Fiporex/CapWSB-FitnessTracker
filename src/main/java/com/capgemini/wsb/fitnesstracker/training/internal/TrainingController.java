package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/training")
class TrainingController {
    private TrainingController() {

}
    @GetMapping
    public List<TrainingDTO> getAllTrainings() {

        return null;
    }
