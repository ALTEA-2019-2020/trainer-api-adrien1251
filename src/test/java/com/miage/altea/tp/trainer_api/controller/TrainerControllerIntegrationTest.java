package com.miage.altea.tp.trainer_api.controller;

import com.miage.altea.tp.trainer_api.bo.Pokemon;
import com.miage.altea.tp.trainer_api.bo.Trainer;
import com.miage.altea.tp.trainer_api.repository.TrainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TrainerControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TrainerController controller;

    @Autowired
    private TrainerRepository repository;



    @BeforeEach
    void init() {
        repository.deleteAll();

        var ash = Trainer.builder().name("Ash").build();
        var pikachu = new Pokemon(25, 18);
        ash.setTeam(List.of(pikachu));

        var misty = Trainer.builder().name("Misty").build();
        var staryu = new Pokemon(120, 18);
        var starmie = new Pokemon(121, 21);
        misty.setTeam(List.of(staryu, starmie));

        // save a couple of trainers
        repository.save(ash);
        repository.save(misty);
    }

    @Test
    void trainerController_shouldBeInstanciated(){
        assertNotNull(controller);
    }

    @Test
    void getTrainer_withNameAsh_shouldReturnAsh() {
        var ash = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/Ash", Trainer.class);
        assertNotNull(ash);
        assertEquals("Ash", ash.getName());
        assertEquals(1, ash.getTeam().size());

        assertEquals(25, ash.getTeam().get(0).getPokemonTypeId());
        assertEquals(18, ash.getTeam().get(0).getLevel());
    }

    @Test
    void getAllTrainers_shouldReturnAshAndMisty() {
        var trainers = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/", Trainer[].class);
        assertNotNull(trainers);
        assertEquals(2, trainers.length);

        assertEquals("Ash", trainers[0].getName());
        assertEquals("Misty", trainers[1].getName());
    }

    @Test
    void createTrainers_shouldCreateNewEntryInDatabase() {
        var trainersBefore = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/", Trainer[].class);
        assertNotNull(trainersBefore);
        assertEquals(2, trainersBefore.length);


        Trainer newTrainerWithMewLvl100 = Trainer.builder()
                .name("I'm mew")
                .team(Arrays.asList(Pokemon.builder().level(100).pokemonTypeId(151).build()))
                .build();
        var trainers = this.restTemplate.postForObject("http://localhost:" + port + "/trainers/", newTrainerWithMewLvl100, Trainer.class);
        assertNotNull(trainers);
        assertEquals("I'm mew", trainers.getName());

        var trainersAfter = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/", Trainer[].class);
        assertNotNull(trainersAfter);
        assertEquals(3, trainersAfter.length);
    }

    @Test
    void updateTrainers_shouldUpdateEntryInDatabase() {
        var trainersBefore = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/", Trainer[].class);
        assertNotNull(trainersBefore);
        assertEquals(2, trainersBefore.length);
        assertEquals("Ash", trainersBefore[0].getName());
        assertEquals(18, trainersBefore[0].getTeam().get(0).getLevel());

        assertEquals("Misty", trainersBefore[1].getName());

        // UPDATE
        Trainer trainer = trainersBefore[0];
        trainer.getTeam().get(0).setLevel(19);

        this.restTemplate.put("http://localhost:" + port + "/trainers/", trainer, Trainer.class);


        var trainersAfter = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/", Trainer[].class);
        assertNotNull(trainersBefore);
        assertEquals(2, trainersAfter.length);
        assertEquals("Ash", trainersAfter[0].getName());
        assertEquals(19, trainersAfter[0].getTeam().get(0).getLevel());
        assertEquals("Misty", trainersAfter[1].getName());

    }

    @Test
    void deleteTrainer_shouldDeleteEntryInDatabase() {
        var trainersBefore = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/", Trainer[].class);
        assertNotNull(trainersBefore);
        assertEquals(2, trainersBefore.length);
        assertEquals("Ash", trainersBefore[0].getName());
        assertEquals("Misty", trainersBefore[1].getName());

        // DELETE

        this.restTemplate.delete("http://localhost:" + port + "/trainers/Ash", Trainer.class);


        var trainersAfter = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/", Trainer[].class);
        assertNotNull(trainersBefore);
        assertEquals(1, trainersAfter.length);
        assertEquals("Misty", trainersAfter[0].getName());

    }
}
