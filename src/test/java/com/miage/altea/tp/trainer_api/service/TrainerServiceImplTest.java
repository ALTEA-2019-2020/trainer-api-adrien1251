package com.miage.altea.tp.trainer_api.service;

import com.miage.altea.tp.trainer_api.bo.Trainer;
import com.miage.altea.tp.trainer_api.repository.TrainerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TrainerServiceImplTest {

    @Test
    void getAllTrainers_shouldCallTheRepository() {
        var trainerRepo = mock(TrainerRepository.class);
        var trainerService = new TrainerServiceImpl(trainerRepo);

        trainerService.getAllTrainers();

        verify(trainerRepo).findAll();
    }

    @Test
    void getTrainer_shouldCallTheRepository() {
        var trainerRepo = mock(TrainerRepository.class);
        var trainerService = new TrainerServiceImpl(trainerRepo);
        Mockito.when(trainerRepo.findById("Ash")).thenReturn(Optional.of(new Trainer()));

        trainerService.getTrainer("Ash");

        verify(trainerRepo).findById("Ash");
    }

    @Test
    void getTrainer_shouldThrowRuntimeExceptionWhenUserNotPresent() {
        var trainerRepo = mock(TrainerRepository.class);
        var trainerService = new TrainerServiceImpl(trainerRepo);
        Mockito.when(trainerRepo.findById("Ash")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> trainerService.getTrainer("Ash"));
    }

    @Test
    void createTrainer_shouldCallTheRepository() {
        var trainerRepo = mock(TrainerRepository.class);
        var trainerService = new TrainerServiceImpl(trainerRepo);
        Mockito.when(trainerRepo.findById("Ash")).thenReturn(Optional.empty());

        var ash = new Trainer();
        trainerService.createTrainer(ash);

        verify(trainerRepo).save(ash);
    }

    @Test
    void createTrainer_shouldThrowRuntimeExceptionIfUserAlreadyPresent() {
        var trainerRepo = mock(TrainerRepository.class);
        var trainerService = new TrainerServiceImpl(trainerRepo);
        Mockito.when(trainerRepo.findById("Ash")).thenReturn(Optional.of(new Trainer()));

        var ash = Trainer.builder().name("Ash").build();
        assertThrows(RuntimeException.class, () -> trainerService.createTrainer(ash));
    }

    @Test
    void updateTrainer_shouldCallTheRepository() {
        var trainerRepo = mock(TrainerRepository.class);
        var trainerService = new TrainerServiceImpl(trainerRepo);
        var ash = Trainer.builder().name("Ash").build();
        Mockito.when(trainerRepo.findById("Ash")).thenReturn(Optional.of(ash));

        trainerService.update(ash);

        verify(trainerRepo).save(ash);
    }


    @Test
    void updateTrainer_shouldThrowRuntimeExceptionWhenTrainerNotFound() {
        var trainerRepo = mock(TrainerRepository.class);
        var trainerService = new TrainerServiceImpl(trainerRepo);
        var ash = Trainer.builder().name("Ash").build();
        Mockito.when(trainerRepo.findById("Ash")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> trainerService.update(ash));
    }

    @Test
    void deleteTrainer_shouldCallTheRepository() {
        var trainerRepo = mock(TrainerRepository.class);
        var trainerService = new TrainerServiceImpl(trainerRepo);
        var ash = new Trainer();
        Mockito.when(trainerRepo.findById("Ash")).thenReturn(Optional.of(ash));

        trainerService.delete("Ash");

        verify(trainerRepo).findById("Ash");
        verify(trainerRepo).delete(ash);
    }

    @Test
    void deleteTrainer_shouldThrowExceptionWhenTrainerNotFound() {
        var trainerRepo = mock(TrainerRepository.class);
        var trainerService = new TrainerServiceImpl(trainerRepo);
        Mockito.when(trainerRepo.findById("Ash")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> trainerService.delete("Ash"));
    }

}
