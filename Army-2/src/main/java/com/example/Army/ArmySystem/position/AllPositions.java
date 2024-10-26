package com.example.Army.ArmySystem.position;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AllPositions {
    @Bean
    CommandLineRunner commandLineRunner(PositionRepository positionRepository) {
        return args ->{
                      positionRepository.saveAll(List.of(
                                new Position("amn"),
                                new Position("archive"),
                                new Position("amlyat"),
                                new Position("daam"),
                                new Position("afrad"),
                                new Position("poffee"),
                                new Position("malyat")
                                        )
                        );
        };
    }
}
