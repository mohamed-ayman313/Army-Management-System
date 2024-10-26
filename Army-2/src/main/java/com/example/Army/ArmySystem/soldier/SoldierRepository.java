package com.example.Army.ArmySystem.soldier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SoldierRepository extends JpaRepository<Soldier,Integer> {
    Optional<Soldier> findSoldierBySoldierArmyId(String soldierArmyId);
}
