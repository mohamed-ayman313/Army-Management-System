package com.example.Army.ArmySystem.ncofficer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NcofficerRepository extends JpaRepository<Ncofficer,Integer> {
    Optional<Ncofficer> findNcofficerByNcoArmyId(String ncoArmyId);
//    Optional<Ncofficer> findNcofficerByPositions(List<Position> positions);
}