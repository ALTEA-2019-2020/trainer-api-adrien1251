package com.miage.altea.tp.trainer_api.repository;


import com.miage.altea.tp.trainer_api.bo.Pokemon;
import com.miage.altea.tp.trainer_api.bo.Trainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureDataJpa
class TrainerRepositoryTest {

    @Autowired
    private TrainerRepository repository;

    @Test
    void trainerRepository_shouldExtendsCrudRepository() throws NoSuchMethodException {
        assertTrue(CrudRepository.class.isAssignableFrom(TrainerRepository.class));
    }

    @Test
    void trainerRepositoryShouldBeInstanciedBySpring(){
        assertNotNull(repository);
    }

    @Test
    void testSave(){
        var ash = Trainer.builder().name("Ash").build();

        repository.save(ash);

        var saved = repository.findById(ash.getName()).orElse(null);

        assertEquals("Ash", saved.getName());
    }

    @Test
    void testSaveWithPokemons(){
        var misty = Trainer.builder().name("Misty").build();
        var staryu = new Pokemon(120, 18);
        var starmie = new Pokemon(121, 21);
        misty.setTeam(List.of(staryu, starmie));

        repository.save(misty);

        var saved = repository.findById(misty.getName()).orElse(null);

        assertEquals("Misty", saved.getName());
        assertEquals(2, saved.getTeam().size());
    }
}
