package com.miage.altea.tp.trainer_api.service;

import com.miage.altea.tp.trainer_api.bo.Trainer;
import com.miage.altea.tp.trainer_api.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerServiceImpl implements TrainerService {

    private TrainerRepository trainerRepository;

    @Autowired
    public TrainerServiceImpl(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    public Iterable<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    @Override
    public Trainer getTrainer(String name) {
        return trainerRepository.findById(name)
                .orElseThrow(() -> new RuntimeException("Trainer " + name + " not found"));
    }

    @Override
    public Trainer createTrainer(Trainer trainer) {
        if(trainerRepository.findById(trainer.getName()).isPresent()) throw new RuntimeException("Trainer " + trainer.getName()+ " already present, want you update it instead?");
        return trainerRepository.save(trainer);
    }

    @Override
    public Trainer update(Trainer trainer) {
        if(!trainerRepository.findById(trainer.getName()).isPresent()) throw new RuntimeException("Trainer " + trainer.getName()+ " no present, want you create it instead?");
        return trainerRepository.save(trainer);
    }

    @Override
    public void delete(String name) {
        Trainer trainer = trainerRepository.findById(name)
                .orElseThrow(() -> new RuntimeException("Trainer : " + name + " not found, we can't delete it"));

        trainerRepository.delete(trainer);
    }
}
